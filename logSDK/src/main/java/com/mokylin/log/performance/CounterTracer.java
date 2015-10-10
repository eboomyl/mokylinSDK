package com.mokylin.log.performance;

/**
 * Created by Administrator on 2015/10/10.
 */
public interface CounterTracer {
    public void start();
    public long getMilliTimeSpan();
    public boolean count();
    public void waitForReached() throws InterruptedException;
}
