package com.alumnisystem;

import java.util.concurrent.atomic.AtomicInteger;

public class TestLocalThread {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    private static ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<Integer>() {

        @Override
        protected Integer initialValue() {
            return atomicInteger.incrementAndGet();
        }

    };

    public static int getNum() {
        return integerThreadLocal.get();
    }

}
