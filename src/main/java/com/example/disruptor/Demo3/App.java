package com.example.disruptor.Demo3;

import com.example.disruptor.demo1.LongEvent;
import com.example.disruptor.demo1.LongEventFactory;
import com.example.disruptor.demo1.LongEventHandler1;
import com.example.disruptor.demo1.LongEventHandler2;
import com.example.disruptor.demo2.LongEventProducter;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @program: disruptor
 * @description: 高级设置
 * @author: mcy
 * @create: 2018-09-12 16:19
 **/
public class App {
    public static void main(String[] args) throws Exception {

        //初始化线程池
        //ExecutorService executorService = Executors.newCachedThreadPool();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //初始化工厂
        LongEventFactory longEventFactory = new LongEventFactory();
        //初始化ringbuffer的大小必须是2的指数
        int ringsize = 1024;

        //初始化RingBuffer  //默认并发模式
        /**
         * 等待策略
         * 控制生产者和消费者， 因为数据的生产和消费的速率不同而制定的策略
         *
         */
        Disruptor<LongEvent> longEventDisruptor = new Disruptor<>(longEventFactory, ringsize, threadFactory, ProducerType.MULTI,new BlockingWaitStrategy());

        /**
         * 多线程时   创建处理器数组
         */
        LongEventHandler1[] LongEventHandler1s = new LongEventHandler1[10];
        for (int i = 0; i <LongEventHandler1s.length ; i++) {
            LongEventHandler1s[i] = new LongEventHandler1();
        }
        LongEventHandler2[] LongEventHandler2s = new LongEventHandler2[10];
        for (int i = 0; i <LongEventHandler2s.length ; i++) {
            LongEventHandler2s[i] = new LongEventHandler2();
        }

        //指定事件处理器
        //longEventDisruptor.handleEventsWith(new LongEventHandler1()); //放数组是每个都消费1次遍

        longEventDisruptor.handleEventsWithWorkerPool(LongEventHandler1s);//一个线程消费一遍

        //longEventDisruptor.handleEventsWithWorkerPool(LongEventHandler1s).handleEventsWith(LongEventHandler1s); 后面可以跟串，并行。和多线程消费和单线程消费。

        //开启Disruptor
        longEventDisruptor.start();
        RingBuffer<LongEvent> ringBuffer = longEventDisruptor.getRingBuffer();

        /*================================向ringBuffer中放数据================================================================*/
        LongEventProducter longEventProducter = new LongEventProducter(ringBuffer);

        //10线程并发
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i <10 ; i++) {
            final long vv = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                        longEventProducter.onData(vv);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //longEventDisruptor.shutdown();
    }
    }

