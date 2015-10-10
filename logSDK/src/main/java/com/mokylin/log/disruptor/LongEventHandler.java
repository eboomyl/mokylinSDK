package com.mokylin.log.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by Administrator on 2015/9/16.
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event: " + event);
    }
}
