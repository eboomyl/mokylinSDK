package com.mokylin.log.performance;

/**
 * Created by Administrator on 2015/10/10.
 */
public interface EventPublisher  {
    public void publish(int data) throws Exception;
    public void start();
    public void stop();
}
