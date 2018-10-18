package com.example.disruptor.demo2;

import com.example.disruptor.demo1.LongEvent;
import com.lmax.disruptor.RingBuffer;

/**
 * @program: disruptor
 * @description:
 * @author: mcy
 * @create: 2018-09-12 16:04
 **/
public class LongEventProducter {

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducter(final RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(Long l){
        long next = ringBuffer.next();
        try {
            LongEvent longEvent = ringBuffer.get(next);
            longEvent.setValue(l);
            System.out.println("线程："+Thread.currentThread().getName()+ " LongEvent:"+longEvent);
        }finally {
            ringBuffer.publish(next);
        }
    }
}
