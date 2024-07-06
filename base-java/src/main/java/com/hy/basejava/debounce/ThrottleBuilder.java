package com.hy.basejava.debounce;

import java.util.Timer;
import java.util.TimerTask;

/**
* Description: 函数节流
* @createDate: 2023/8/16 15:21
* @author haoyong
* @lastModifyBy haoyong
*/
public class ThrottleBuilder {
    /**
     * 计时器
     */
    private Timer timer;

    /**
     * 延迟时间
     */
    private long delayTime;

    public ThrottleBuilder(long delayTime){
        this.delayTime = delayTime;
    }

    public void startInvoke(Runnable runnable){

        if (this.timer==null){
            this.timer = new Timer();
        }else{
            return;
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
                timer.cancel();
                timer = null;
            }
        },delayTime);

    }

}
