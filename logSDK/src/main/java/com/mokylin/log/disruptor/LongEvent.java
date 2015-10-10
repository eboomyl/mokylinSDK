package com.mokylin.log.disruptor;

/**
 * Created by Administrator on 2015/9/16.
 */
public class LongEvent {
    private long value;

    public void setValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }
}
