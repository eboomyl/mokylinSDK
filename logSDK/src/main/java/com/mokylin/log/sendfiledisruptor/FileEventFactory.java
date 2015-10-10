package com.mokylin.log.sendfiledisruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by Administrator on 2015/10/10.
 */
public class FileEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new FileEvent();
    }
}
