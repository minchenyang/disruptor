package com.example.disruptor.demo1;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @program: disruptor
 * @description:
 * @author: mcy
 * @create: 2018-09-11 14:02
 **/
public class LongEventHandler1 implements EventHandler<LongEvent>, WorkHandler<LongEvent> {

    /**
     *
     * @param longEvent 发布到ringBuffer中的事件
     * @param l 当前处理事件的序号
     * @param b 是否为ringBuffer中的最后一个
     * @throws Exception
     */
    @Override
    public void onEvent(final LongEvent longEvent, final long l, final boolean b) throws Exception {
        this.event(longEvent);
    }

    @Override
    public void onEvent(final LongEvent longEvent) throws Exception {
        this.event(longEvent);
    }

    private void event( LongEvent longEvent){
        System.out.println(" 1 线程名字："+Thread.currentThread().getName()+" 消费Event: "+ longEvent);
    }
}
