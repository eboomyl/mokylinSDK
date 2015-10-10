package com.mokylin.log.disruptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ExecutorsFactory {
    private static Map<String, Executor> executors = new HashMap<>();

    public static Executor getLogTypeExecutor(String logType) {

        if (executors.get(logType) == null) {
            Executor executor = Executors.newCachedThreadPool();
            executors.put(logType, executor);
            return executor;
        } else {
            return executors.get(logType);
        }
    }
}
