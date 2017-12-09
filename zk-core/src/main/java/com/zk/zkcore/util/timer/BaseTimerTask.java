package com.zk.zkcore.util.timer;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/12/9.
 */

public class BaseTimerTask extends TimerTask {
    private ITimerListener mTimerListener;

    public BaseTimerTask(ITimerListener timerListener) {
        mTimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mTimerListener != null){
            mTimerListener.onTimer();
        }
    }
}
