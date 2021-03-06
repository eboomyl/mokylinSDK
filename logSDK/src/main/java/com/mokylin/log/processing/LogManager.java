package com.mokylin.log.processing;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.mokylin.log.http.Client;
import com.mokylin.log.logdisruptor.DataEvent;
import com.mokylin.log.logdisruptor.DataEventFactory;
import com.mokylin.log.logdisruptor.DataEventHandler;
import com.mokylin.log.model.BaseModel;
import com.mokylin.log.sendfiledisruptor.FileEvent;
import com.mokylin.log.sendfiledisruptor.FileEventFactory;
import com.mokylin.log.sendfiledisruptor.FileEventHandler;
import com.mokylin.log.util.ExecutorsUtils;
import com.mokylin.log.util.ConstantsUtils;
import com.mokylin.log.util.StringMap;
import com.mokylin.log.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/10/10.
 */
public class LogManager  {
    private static final WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
    private static final  WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
    private static final  WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
    private static String accessKey;
    private static String secretKey;
    private static ExecutorService dataExecutor;
    private static ExecutorService fileExecutor;
    private static RingBuffer<DataEvent> ringbuffer;
    private static Disruptor<DataEvent> disruptor;
    private static DataEventHandler handler;

    private static RingBuffer<FileEvent> fileRingbuffer;
    private static Disruptor<FileEvent> filedisruptor;
    private static FileEventHandler fileHandler;

    private static int bufferSize = 1024*1024;
    private static long startTicks;
    private static long endTicks;
    private static Client client = new Client();

    public  List<StringMap> stringMapList;
    private StringMap stringMap;


    static{
        handler = new DataEventHandler();
        fileHandler = new FileEventHandler();

        //设置线程池策略
        dataExecutor = Executors.newFixedThreadPool(20);
        fileExecutor = Executors.newFixedThreadPool(20);


        disruptor = new Disruptor<DataEvent>(new DataEventFactory(), bufferSize,
                dataExecutor, ProducerType.SINGLE,
                BLOCKING_WAIT);

        filedisruptor =   new Disruptor<FileEvent>(new FileEventFactory(), bufferSize,
                fileExecutor, ProducerType.SINGLE,
                BLOCKING_WAIT);
    }

    private LogManager(){
        stringMapList =  new ArrayList<>();
        stringMap = new StringMap();
    }

    public static void auth(String accessKey, String secretKeySpec) {
        if (!StringUtils.isNullOrEmpty(accessKey) && !StringUtils.isNullOrEmpty(secretKeySpec)) {
            LogManager.accessKey = accessKey;
            LogManager.secretKey = secretKeySpec;
            //验证key是否正确
            if (Client.Auth()) {
                //启动Disruptor
                start();
            }
        } else {
            throw new IllegalArgumentException("empty key");
        }
    }

    //写日志数据到文件
    public static void dataPublish(List<StringMap> data) throws Exception {
        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringbuffer.next();
        try {
            //用上面的索引取出一个空的事件用于填充
            DataEvent event = ringbuffer.get(sequence);// for the sequence
            event.setStringMapList(data);
        } finally {
            //发布事件
            ringbuffer.publish(sequence);

        }
    }

    //读取文件发送
    public static void filePublish(File file) throws Exception {
        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = fileRingbuffer.next();
        try {
            //用上面的索引取出一个空的事件用于填充
            FileEvent event = fileRingbuffer.get(sequence);// for the sequence
            event.setSendFile(file);
        } finally {
            //发布事件
            fileRingbuffer.publish(sequence);
        }
    }


    public static void start() {
        //计算日志处理时间
        startTicks = System.currentTimeMillis();
        //启动disruptor
        disruptor.handleEventsWith(handler);
        disruptor.start();
        ringbuffer = disruptor.getRingBuffer();

        filedisruptor.handleEventsWith(fileHandler);
        filedisruptor.start();
        fileRingbuffer = filedisruptor.getRingBuffer();

    }

    public static void stop() throws Exception {
        //disruptor 关闭
        disruptor.shutdown();
        filedisruptor.shutdown();
        ExecutorsUtils.shutdownAndAwaitTermination(dataExecutor,10, TimeUnit.SECONDS);
        ExecutorsUtils.shutdownAndAwaitTermination(fileExecutor,10, TimeUnit.SECONDS);
        endTicks = System.currentTimeMillis();

        //日志尾单处理
        sendLogFileEnd();
    }

    private static void sendLogFileEnd() throws Exception {
        //发送最后一个文件
         List<File> sendFiles = client.getLogFilesEnd(ConstantsUtils.BASE_FILE_PATH);
         for(File sendFile:sendFiles){
            // client.setSendFiles(sendFile);
            //client.sendFile();
             String nowDate =  StringUtils.getNowDate();
             File renameFile = new File(sendFile.getParent()+File.separator+ ConstantsUtils.LOG_CONTANT_UPLOAD+"_send_"+nowDate+"_over");
             sendFile.renameTo(renameFile);
         }
//        if (sendFile.exists()) {
//            // client.setSendFiles(logfile);
//            //client.sendFile();
//            String nowDate =  StringUtils.getNowDate();
//            System.out.println("file-"+nowDate);
//            File renameFile = new File(sendFile.getParent()+File.separator+ ConstantsUtils.LOG_CONTANT_UPLOAD+"_sendover_"+ nowDate);
//            //发送完毕文件改名为已发送状态 logFileUpload_over_yyyyMMddHHmmss
//             sendFile.renameTo(renameFile);
//        }
    }

    //设置日志类型，日志类型为目录名
    public static LogManager Type(String logType) {
        String nowDate = StringUtils.getNowDate();
        String uuidStr = StringUtils.getUUID();

        LogManager logManager = new LogManager();
        logManager.stringMap.put(ConstantsUtils.LOG_TYPE, logType);
        logManager.stringMap.put(ConstantsUtils.TIMESTAMP, nowDate).put(ConstantsUtils.UUID, uuidStr);
        logManager.stringMapList = new ArrayList<>();
        return logManager;
    }

    public LogManager set(String column, Object value) {
        if (!StringUtils.isNullOrEmpty((String) this.stringMap.get(ConstantsUtils.LOG_TYPE))) {//判断日志类型是否已经被设置
            this.stringMap.put(column, value);
        } else {
            System.out.println("日志类型没有设置。");
        }
        return this;
    }


    public LogManager setLogModel(BaseModel logModel) {
        this.stringMap.putAll(logModel.getDtoToStringMap());
        return this;
    }

    //每条日志提交一次，写入日志文件
    public void commit(){
        try {
            this.stringMapList.add(this.stringMap);
            dataPublish(this.stringMapList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送文件
    public static void setLogFilePath(String logFilePath) {
        //读取目录下的所有日志文件
        client.getFiles(logFilePath);
        client.sendFile();
    }

    //计算日志处理时间单位ms
    public static long getMilliTimeSpan(){
        return endTicks-startTicks;
    }


}
