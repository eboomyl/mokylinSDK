package com.mokylin.log.http;

import com.mokylin.log.util.ConstantsUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.util.EncodingUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public class Client {

    //TODO 用户验证方法
    public static boolean Auth() {
        return true;
    }

    //需要发送的文件列表
    private static List<File> sendFiles = new ArrayList<>();


    //统一发送文件
    public void sendFile() {
        try {
            if (checkSendFiles()) {
                String targetUrl = ConstantsUtils.UPLOAD_FILE_URL;
                PostMethod filePost = new PostMethod(targetUrl) {//这个用来中文乱码
                    public String getRequestCharSet() {
                        return "UTF-8";//
                    }
                };
                HttpClient client = new HttpClient();
                System.out.println("come in Client");
                for (int i = 0; i < sendFiles.size(); i++) {
                    File sendFile = sendFiles.get(i);
                    System.out.println(sendFiles.size());
                    //设置线程安全，给文件对象加锁
                    synchronized(sendFile){
                        System.out.println(sendFile.length());
                        System.out.println(sendFile.getAbsolutePath() + sendFile.getName());
                        Part[] parts = new Part[]{new CustomFilePart(sendFile.getName(), sendFile)};
//                        filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
//                        if (client.executeMethod(filePost) == HttpStatus.SC_OK) {
//                            sendFiles.remove(sendFile);
//                        }
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //检测发送文件列表是否为空等信息
    private boolean checkSendFiles() {
        if (sendFiles != null && sendFiles.size() > 0) {
            return true;
        }
        return false;
    }

    //设置需要发送的文件列表
    public void setSendFiles(File sendFile){
        if(sendFile.isFile()) {
            sendFiles.add(sendFile);
        }
    }

    //读取目录下需要发送的文件
    public void getFiles(String filePath) {
        File[] files = new File(filePath).listFiles();
        for (File file : files) {
                if (file.isDirectory()) {
                    getFiles(file.getPath());
                } else {
                    //文件大小超过 30K则发送，
//                    if(file.length()>30720&&ConstantsUtils.LOG_CONTANT_FILE_NAME.equals(file.getName())){
//                        //改名为logFileUpload
//                        File renameFile = new File(file.getParent()+File.separator+ConstantsUtils.LOG_CONTANT_UPLOAD);
//                        file.renameTo(renameFile);
                        sendFiles.add(file);
//                    }

                }
        }
    }
}

class CustomFilePart extends FilePart {
    public CustomFilePart(String filename, File file) throws IOException {
        super(filename, file);
    }

    protected void sendDispositionHeader(OutputStream out) throws IOException {
        super.sendDispositionHeader(out);
        String filename = getSource().getFileName();
        if (filename != null) {
            out.write(EncodingUtil.getAsciiBytes(FILE_NAME));
            out.write(QUOTE_BYTES);
            out.write(EncodingUtil.getBytes(filename, "utf-8"));
            out.write(QUOTE_BYTES);
        }
    }
}