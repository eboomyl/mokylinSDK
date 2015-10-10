package com.mokylin.log.performance;

/**
 * Created by Administrator on 2015/10/10.
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
        int DATA_COUNT =1000000;
        CounterTracer tracer = new SimpleTracer(DATA_COUNT) ;//计数跟踪到达指定的数值；
        TestHandler handler = new TestHandler(tracer);//Consumer 的事件处理；

        EventPublisher publisher = new DisruptorPublisher(1024*1024,handler);//通过工厂对象创建不同的 Producer 的实现；
        publisher.start();
        tracer.start();

//发布事件；
        for (int i = 0; i < DATA_COUNT; i++) {
            publisher.publish(i);
        }

//等待事件处理完成；
        tracer.waitForReached();

        publisher.stop();

//输出结果；
        printResult(tracer);
    }

    private static void printResult(CounterTracer tracer) {
        System.out.println(tracer.getMilliTimeSpan());
        System.out.println(tracer.toString());
    }

}
