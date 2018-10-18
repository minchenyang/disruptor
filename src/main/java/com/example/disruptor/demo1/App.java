package com.example.disruptor.demo1;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: disruptor
 * @description:
 * @author: mcy
 * @create: 2018-09-11 14:08
 **/
public class App {

    public static void main(String[] args) {

        //初始化线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //初始化工厂
        LongEventFactory longEventFactory = new LongEventFactory();
        //初始化ringbuffer的大小必须是2的指数
        int ringsize = 1024;

        //初始化RingBuffer
        Disruptor<LongEvent> longEventDisruptor = new Disruptor<>(longEventFactory, ringsize, executorService);
        //指定事件处理器
        longEventDisruptor.handleEventsWith(new LongEventHandler1());
        //开启Disruptor
        longEventDisruptor.start();

        /*================================向ringBuffer中放数据================================================================*/

        RingBuffer<LongEvent> ringBuffer = longEventDisruptor.getRingBuffer();
        long next = ringBuffer.next();
        try {

            LongEvent longEvent = ringBuffer.get(next);
            longEvent.setValue(1000L);
            System.out.println("Producter"+ longEvent);
        }finally {
            ringBuffer.publish(next);
        }
    }
}
