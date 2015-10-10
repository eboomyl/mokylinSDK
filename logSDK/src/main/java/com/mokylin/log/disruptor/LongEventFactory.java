package com.mokylin.log.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Administrator on 2015/9/16.
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    public LongEvent newInstance() {
        return new LongEvent();
    }

}
