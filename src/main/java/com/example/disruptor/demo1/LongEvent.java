package com.example.disruptor.demo1;

/**
 * @program: disruptor
 * @description:
 * @author: mcy
 * @create: 2018-09-11 13:56
 **/
public class LongEvent {

    private long value;

    public LongEvent() {
    }

    public LongEvent(final long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(final long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "value=" + value +
                '}';
    }
}
