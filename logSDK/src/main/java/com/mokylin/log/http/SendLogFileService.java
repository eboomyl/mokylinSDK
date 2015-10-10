package com.mokylin.log.http;

import com.mokylin.log.util.ExecutorsUtils;
import com.mokylin.log.util.ConstantsUtils;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2015/9/24.
 */
public class SendLogFileService {

    private static Executor executor;
    private static Client client = new Client();



    public static void main(String[] args) {
        client.getFiles(ConstantsUtils.BASE_FILE_PATH);
        testScheduledExecutorService();

    }

    private static void testScheduledExecutorService(){
        System.out.println("come in testScheduledExecutorService");
        executor = Executors.newScheduledThreadPool(10);
        ScheduledExecutorService scheduler = (ScheduledExecutorService) executor;
        scheduler.scheduleAtFixedRate(new LogFileThead(client), 1, 3, TimeUnit.SECONDS);
    }


    public static void testExecutorService() {
        executor = Executors.newFixedThreadPool(10);
        ExecutorService executorService = (ExecutorService) executor;
        while (!executorService.isShutdown()) {
            try {

                executorService.execute(new LogFileThead(client));
                //System.out.println("come in testExecutorService");
            } catch (RejectedExecutionException  ignored) {
                ExecutorsUtils.shutdownAndAwaitTermination(executorService, 30, TimeUnit.SECONDS);
            }
        }
        //executorService.shutdown();
    }
}

class LogFileThead implements Runnable {
    private Client client;

    public LogFileThead(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println("come in LogFileThead");
        client.sendFile();
    }
}
