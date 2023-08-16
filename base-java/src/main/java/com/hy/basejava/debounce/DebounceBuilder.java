package com.hy.basejava.debounce;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description: 函数防抖
 *
 * @author haoyong
 * @createDate: 2023/8/16 15:22
 * @lastModifyBy haoyong
 */
public class DebounceBuilder {
    /**
     * 计时器
     */
    private Timer timer = new Timer();

    /**
     * 延迟时间
     */
    private long delayTime;

    private TimerTask timerTask;

    public DebounceBuilder(long delayTime) {
        this.delayTime = delayTime;
    }

    public void startInvoke(Runnable runnable) {
        if (timer == null){
            timer = new Timer();
        }
        if (this.timerTask != null) {
            this.timerTask.cancel();
        }
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
                timerTask.cancel();
                timerTask = null;
                timer.cancel();
                timer = null;
            }
        };
        timer.schedule(this.timerTask, delayTime);

    }

}
