package com.mokylin.log.logdisruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Administrator on 2015/9/17.
 */



public class DataEventFactory implements EventFactory<DataEvent> {

    @Override
    public DataEvent newInstance() {
        return new DataEvent();
    }
}


