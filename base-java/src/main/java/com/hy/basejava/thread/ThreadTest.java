package com.hy.basejava.thread;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class ThreadTest {


    public static void main(String[] args)  {
        runnableTest();
    }

    /**
     *主线程先suspend
     * 当所有线程执行完了，执行主线程的resume唤起主线程
     */
    public static void runnableTest(){
        Logger myLogger = Logger.getLogger("myLogger");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        Thread thread = Thread.currentThread();

        threadPoolExecutor.getCompletedTaskCount();

        AtomicInteger resource = new AtomicInteger(1000);
        for (int i=0;i<1000;i++){
            threadPoolExecutor.execute(new CountRunnable(myLogger,resource,thread));
        }
        thread.suspend();
        myLogger.info("------------------------------"+resource.get());

    }

    public static void callableTest() throws ExecutionException, InterruptedException {
        Logger myLogger = Logger.getLogger("myLogger");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        Thread thread = Thread.currentThread();
        AtomicInteger resource = new AtomicInteger(1000);
        List<Future<AtomicInteger>> futureList = new ArrayList<>();
        for (int i=0;i<1000;i++){
            Future<AtomicInteger> future = threadPoolExecutor.submit(new CountCallable(myLogger,resource,thread));
            futureList.add(future);
        }

        for (Future<AtomicInteger> atomicIntegerFuture : futureList) {
            atomicIntegerFuture.get();
        }
        myLogger.info("------------------------------"+resource.get());


    }


    @AllArgsConstructor
    @Data
    public static class CountRunnable implements Runnable{
        Logger myLogger;
        AtomicInteger resource;
        Thread thread;


        @Override
        public void run() {
            int current = resource.getAndDecrement();
            if (current<=1){
                myLogger.info("counting down....current<1...."+current);
                thread.resume();
            return;
            }
//            try {
//                Thread.sleep(500L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            myLogger.info("counting down...."+current);
        }
    }

        @AllArgsConstructor
        @Data
        public static class CountCallable implements Callable<AtomicInteger>{
            Logger myLogger;
            AtomicInteger resource;
            Thread thread;

            @Override
            public AtomicInteger call() throws Exception {

                int current = resource.getAndDecrement();
                if (current<1){
                    return null;
                }
                Thread.sleep(500L);
                myLogger.info("counting down...."+current);
                return resource;
            }
        }


}
