package com.mokylin.log.sendfiledisruptor;

import com.lmax.disruptor.EventHandler;
import com.mokylin.log.http.Client;
import com.mokylin.log.util.ConstantsUtils;
import com.mokylin.log.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2015/10/10.
 */
public class FileEventHandler  implements EventHandler<FileEvent> {

    @Override
    public void onEvent(FileEvent fileEvent, long l, boolean b) throws Exception {
        //发送文件
       // System.out.println("发送文件");
        File sendFile  = fileEvent.getSendFile();
//        Client client = new Client();
//        client.setSendFiles(sendFile);
//        client.sendFile();

        //发送文件后改名
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();

        String nowDate =  StringUtils.getNowDate();
        System.out.println("file-" + nowDate);
        System.out.println("file-count"+l);
        File renameFile = new File(sendFile.getParent()+File.separator+ ConstantsUtils.LOG_CONTANT_UPLOAD+"_send"+nowDate+"_"+uuidStr);
        //发送完毕文件改名为已发送状态 logFileUpload_over_yyyyMMddHHmmss
        sendFile.renameTo(renameFile);
    }
}
