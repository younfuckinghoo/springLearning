package com.hy.basejava.thread;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new CountDownRunnable(countDownLatch));
        }
        try {
            countDownLatch.await();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>"+countDownLatch.getCount());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }

    }


    @Data
    @AllArgsConstructor
    static class CountDownRunnable implements Runnable{

        CountDownLatch countDownLatch;

        @Override
        public void run() {
            try{
                System.out.println("------------"+countDownLatch.getCount());
                if (countDownLatch.getCount()==500){
                    int a = 1/0;
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }

        }
    }

}
