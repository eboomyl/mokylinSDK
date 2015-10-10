package com.mokylin.log.disruptor;

import com.mokylin.log.util.ConstantsUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class FileNewIOTest {

    private static List<File> sendFiles = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        getFiles("E:\\testDATA\\testdata");
        for(File file:sendFiles){
            File renameFile = new File(file.getParent()+File.separator+ ConstantsUtils.LOG_CONTANT_UPLOAD+"1111");
            file.renameTo(renameFile);

            System.out.println(renameFile.getAbsolutePath());
            System.out.println(file.getAbsolutePath()+file.getName());
        }
        System.out.println(sendFiles.size());
    }

    private static void getFiles(String filePath){
        File [] files = new File(filePath).listFiles();
        for(File file:files){
            if(file.isDirectory()){
                getFiles(file.getPath());
            }else{
                sendFiles.add(file);
            }

        }
    }

    private static void writeAppendFile()throws IOException {
        String appendData = "this string will be append to last row fileName\r\n";
        try(FileWriter fw = new FileWriter("E://风云gm指令.txt", true); BufferedWriter bw = new BufferedWriter(fw)){
            fw.write(appendData);
        }
    }

    private static void writeNewMethodFile()throws IOException {
        String mapJson = "\r\n{asdfasdfasdfasdfasd}";
        Files.write(Paths.get("E://风云gm指令.txt"), mapJson.getBytes());
    }
}
