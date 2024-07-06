package com.hy.basejava.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用对象锁来控制线程的停止/运行
 */
public class ThreadWaitNoticeTest {
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
                            object.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }


                }
            }
        };
        Thread thread1 = new Thread(runnable, "thread1");
        thread1.start();



        Thread.sleep(5000l);
        // 必须要持有对象锁 否则会报IllegalMonitorStateException
        synchronized (object){
            object.notify();
        }

    }
}
