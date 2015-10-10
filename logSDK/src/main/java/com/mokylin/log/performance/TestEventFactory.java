package com.mokylin.log.performance;

import com.lmax.disruptor.EventFactory;
/**
 * Created by Administrator on 2015/10/10.
 */
public class TestEventFactory implements EventFactory<TestEvent> {
    @Override
    public TestEvent newInstance() {
        return new TestEvent();
    }
}
