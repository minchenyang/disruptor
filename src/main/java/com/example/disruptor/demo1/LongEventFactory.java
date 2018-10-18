package com.example.disruptor.demo1;

import com.example.disruptor.demo1.LongEvent;
import com.lmax.disruptor.EventFactory;

/**
 * @program: disruptor
 * @description:
 * @author: mcy
 * @create: 2018-09-11 13:58
 **/
public class LongEventFactory implements EventFactory<LongEvent>{

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
