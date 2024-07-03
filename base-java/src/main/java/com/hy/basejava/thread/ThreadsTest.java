package com.hy.basejava.thread;

import java.util.Set;
import java.util.concurrent.*;

/**
 * 使用CyclicBarrier屏障 在多个线程中wait 等所有线程都完成了 CyclicBarrier中的最终线程才会开始跑 可以作为统计功能使用
 * 使用Semaphore信号量来限制最多同时运行的线程数。
 */
public class ThreadsTest implements Runnable{

    private ExecutorService threadPool= Executors.newFixedThreadPool(10);
    private CyclicBarrier cb=new CyclicBarrier(10,this);
    private ConcurrentHashMap<String, Integer> map=new ConcurrentHashMap<String,Integer>();
    // 只能2个线程同时访问
    final Semaphore semp = new Semaphore(2);
    public void count(){
        ThreadPoolExecutor threadPool1 = (ThreadPoolExecutor) threadPool;

        for(int i=0;i<10;i++){
            threadPool.execute(new Runnable(){
                @Override
                public void run() {

                    // 获取许可
                    try {
                        semp.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for(int j=0; j<6;j++){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("--------j----threadName-----" + j + "====" + Thread.currentThread().getName());
                    }

                    int score=(int)(Math.random()*35+60);
                    map.put(Thread.currentThread().getName(), score);
                    System.out.println(Thread.currentThread().getName()+"的平均成绩为"+score);
                    try {
                        // 访问完后，释放
                        semp.release();
                        cb.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public void run() {
        int result=0;
        Set<String> set = map.keySet();
        for(String s:set){
            result+=map.get(s);
        }
        System.out.println("平均成绩为:"+(result/10)+"分");
    }

    public static void main(String[] args) {
        ThreadsTest t=new ThreadsTest();
        t.count();
    }

}
