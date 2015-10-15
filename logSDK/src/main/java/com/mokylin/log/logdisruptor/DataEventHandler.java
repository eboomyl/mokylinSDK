package com.mokylin.log.logdisruptor;


import com.lmax.disruptor.EventHandler;
import com.mokylin.log.processing.LogManager;
import com.mokylin.log.util.ConstantsUtils;
import com.mokylin.log.util.StringMap;
import com.mokylin.log.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

            // TODO: 2015/9/24 测试数据需要更改
            String logfilePath = "E://testDATA//logdatatest//logFileContant";
            File logfile = new File(logfilePath);
            if (!logfile.exists()) {
                logfile.createNewFile();
            }

            //true:表示是追加的标志
            try (FileWriter fw = new FileWriter(logfile, true); BufferedWriter bw = new BufferedWriter(fw)) {
                fw.write(logFileMap.get(key));
            }
            long dasdf = logfile.lastModified();
            long nowdate11 = System.currentTimeMillis();
            // 变更文件名
            File renameFile = new File(logfile.getParent()+File.separator+ConstantsUtils.LOG_CONTANT_UPLOAD);
            if(!renameFile.exists()){//发送文件不存在则创建
                //文件大小超过 30720则发送，
                if(logfile.length()>30720&&ConstantsUtils.LOG_CONTANT_FILE_NAME.equals(logfile.getName())){
                    //改名为logFileUpload
                    logfile.renameTo(renameFile);
                    //sendFiles.add(renameFile);
                    LogManager.filePublish(renameFile);
                }else{
                    //最后一次修改时间超过10分钟则修改文件名发送
                    if(System.currentTimeMillis()-logfile.lastModified()>600000){
                        logfile.renameTo(renameFile);
                        //sendFiles.add(renameFile);
                        LogManager.filePublish(renameFile);
                    }
                }
            }else{
                LogManager.filePublish(renameFile);
            }



        }


    }
}
