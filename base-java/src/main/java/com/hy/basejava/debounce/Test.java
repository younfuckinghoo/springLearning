package com.hy.basejava.debounce;

import java.util.Timer;
import java.util.TimerTask;

/**
* Description: 函数防抖
* @createDate: 2023/8/16 15:13
* @author haoyong
* @lastModifyBy haoyong
*/

public class Test {

    public static void main(String[] args) {
        try {
            testDebounce();
//        testThrottle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试防抖 理论上只有最后一个能执行
     * 如果连续点击的频率小于100毫秒 那么最后一次点击生效
     */
    public static void testDebounce() throws InterruptedException {
        DebounceBuilder debounceBuilder = new DebounceBuilder(100);
        Runnable run = ()->{printMsg("-----------------------");};
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            debounceBuilder.startInvoke(()->{printMsg("-----------------------"+ finalI);});
            Thread.sleep(1l);
        }

        Thread.sleep(1000l);
        debounceBuilder.startInvoke(()->{printMsg("----------xxxx-------------");});
    }

    /**
     * 每间隔2000毫秒有一次点击生效
     * @throws InterruptedException
     */
    public static void testThrottle() throws InterruptedException {
        ThrottleBuilder throttleBuilder = new ThrottleBuilder(2000);
        Runnable run = ()->{printMsg("-----------------------");};
        for (int i = 0; i < 10000; i++) {
            int finalI = i;
            throttleBuilder.startInvoke(()->{printMsg("-----------------------"+ finalI);});
           Thread.sleep(1l);

        }
    }


    public static void printMsg(String msg){
        System.out.println(msg);
    }
}
