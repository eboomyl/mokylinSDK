package com.mokylin.log.util;

import java.nio.charset.Charset;

/**
 * Created by Administrator on 2015/9/18.
 */
public class ConstantsUtils {
    //日志文件保存路径
    public static final String BASE_FILE_PATH = "E:\\testDATA\\logdatatest";

    //日志文件名
    public static final String LOG_CONTANT_FILE_NAME = "logFileContant";

    //发送日志文件名
    public static final String LOG_CONTANT_UPLOAD = "logFileUpload";

    //日志类型
    public static final String LOG_TYPE = "logType";

    //时间戳
    public static final String TIMESTAMP = "timestamp";

    //uuid
    public static final String UUID = "uuid";

    //上传文件URL
    public static final String UPLOAD_FILE_URL = "http://localhost:8080/Test";

    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final String LOG_BASE_URL = "";
}
