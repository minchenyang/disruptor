package com.example.disruptor.demo2;

import com.example.disruptor.demo1.LongEvent;
import com.example.disruptor.demo1.LongEventFactory;
import com.example.disruptor.demo1.LongEventHandler1;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: disruptor
 * @description: 优化  Producter 模板块代码
 * @author: mcy
 * @create: 2018-09-12 16:02
 **/
public class App {

    public static void main(String[] args) throws Exception {
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
        RingBuffer<LongEvent> ringBuffer = longEventDisruptor.getRingBuffer();

        /*================================向ringBuffer中放数据================================================================*/
        LongEventProducter longEventProducter = new LongEventProducter(ringBuffer);

        for (int i = 0; i < 10; i++) {
            longEventProducter.onData(1000L);
            Thread.sleep(1000);
        }

    }
}
