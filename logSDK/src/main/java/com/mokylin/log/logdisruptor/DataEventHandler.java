package com.mokylin.log.logdisruptor;


import com.lmax.disruptor.EventHandler;
import com.mokylin.log.processing.LogManager;
import com.mokylin.log.util.ConstantsUtils;
import com.mokylin.log.util.StringMap;
import com.mokylin.log.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/17.
 */
public class DataEventHandler implements EventHandler<DataEvent> {

    @Override
    public void onEvent(DataEvent dataEvent, long l, boolean b) throws Exception {
        //System.out.println(dataEvent.getStringMap().getMapJson());
        //把日志写入文件，根据日志类型，写到不同的文件里面去
        List<StringMap> stringMapList = dataEvent.getStringMapList();


        if(stringMapList!=null){
            Map<String, String> logFileMap = new HashMap<>();
            for (StringMap stringMap : stringMapList) {
                String logType = (String) stringMap.get(ConstantsUtils.LOG_TYPE);
                String logContent = stringMap.getMapJson();
                if(StringUtils.isNullOrEmpty(logFileMap.get(logType))){
                    logContent+="\r\n";
                }else{
                    logContent+="\r\n"+logFileMap.get(logType);
                }
                logFileMap.put(logType, logContent);
            }

            for (String key : logFileMap.keySet()) {
                //目录结构 例：/logfilepath/logtype/logContant  用于把json字符串写入该文件
                //            /logfilepath/logtype/logUpload   该文件读取上传至服务器
                //String logfilePath = ConstantsUtils.BASE_FILE_PATH + File.separator + logType + File.separator + ConstantsUtils.LOG_CONTANT_FILE_NAME;
                String logfilePath = "E://testDATA//logdatatest//logFileContant";
                // TODO: 2015/9/24 测试数据需要更改

                File logfile = new File(logfilePath);
                if (!logfile.exists()) {
                    logfile.createNewFile();
                }

                //true:表示是追加的标志
                try (FileWriter fw = new FileWriter(logfile, true); BufferedWriter bw = new BufferedWriter(fw)) {
                    fw.write(logFileMap.get(key));
                }

                // 变更文件名
                File renameFile = new File(logfile.getParent()+File.separator+ConstantsUtils.LOG_CONTANT_UPLOAD);
                if(!renameFile.exists()){//文件存在则发送
                    //文件大小超过 30720改名 发送
                    if(logfile.length()>150720&&ConstantsUtils.LOG_CONTANT_FILE_NAME.equals(logfile.getName())){
                        //改名为logFileUpload
                        logfile.renameTo(renameFile);
                        //sendFiles.add(renameFile);
                        LogManager.filePublish(renameFile);
                    }
                }

        }
        }
    }
}
