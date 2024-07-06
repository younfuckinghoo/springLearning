package com.hy.basejava.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程time_out
 * 线程等待一段时间自动唤醒
 */
public class ThreadTimeWaitTest {
    public static void main(String[] args) throws InterruptedException {
        Object object = new Object();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized (object){
                    while (true){
                        atomicInteger.addAndGet(1);
                        System.out.println("add----"+ atomicInteger.get());
                        try {
                            object.wait(1000l);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }


                }
            }
        };
        Thread thread1 = new Thread(runnable, "thread1");
        thread1.start();




    }
}
