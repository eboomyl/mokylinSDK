package com.mokylin.log.performance;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.mokylin.log.util.ExecutorsUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/10/10.
 */
public class DisruptorPublisher implements EventPublisher {

    private class TestEventHandler implements EventHandler<TestEvent> {

        private TestHandler handler;

        public TestEventHandler(TestHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onEvent(TestEvent event, long sequence, boolean endOfBatch)
                throws Exception {
            handler.process(event);
        }

    }

    private static final WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();

    private Disruptor<TestEvent> disruptor;
    private TestEventHandler handler;
    private RingBuffer<TestEvent> ringbuffer;
    private ExecutorService executor;

    public DisruptorPublisher(int bufferSize, TestHandler handler) {
        this.handler = new TestEventHandler(handler);
        executor = Executors.newSingleThreadExecutor();
        disruptor = new Disruptor<TestEvent>(new TestEventFactory(), bufferSize,
                executor, ProducerType.SINGLE,
                YIELDING_WAIT);
    }

    @SuppressWarnings("unchecked")
    public void start() {
        disruptor.handleEventsWith(handler);
        disruptor.start();
        ringbuffer = disruptor.getRingBuffer();
    }

    @Override
    public void stop() {
        disruptor.shutdown();
        ExecutorsUtils.shutdownAndAwaitTermination(executor,10, TimeUnit.SECONDS);
    }

    @Override
    public void publish(int data) throws Exception {
        long seq = ringbuffer.next();
        try {
            TestEvent evt = ringbuffer.get(seq);
            evt.setValue(data);
        } finally {
            ringbuffer.publish(seq);
        }
    }

    //省略其它代码；
}
