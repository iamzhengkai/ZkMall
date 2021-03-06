package com.zk.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.zk.ec.R;
import com.zk.ec.R2;
import com.zk.zkcore.app.AccountManager;
import com.zk.zkcore.app.IUserChecker;
import com.zk.zkcore.delegates.Delegate;
import com.zk.zkcore.ui.launcher.ILauncherListener;
import com.zk.zkcore.ui.launcher.OnLauncherFinishTag;
import com.zk.zkcore.util.SPUtils;
import com.zk.zkcore.util.ToastUtils;
import com.zk.zkcore.util.timer.BaseTimerTask;
import com.zk.zkcore.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/9.
 */

public class LauncherDelegate extends Delegate implements ITimerListener {

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvLauncherTimer;

    private Timer mTimer;
    private int mCount = 5;
    private CountDownTimer mCountDownTimer;
    private ILauncherListener mILauncherListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
//        TODO：由于CountDownTimer计时不准确，所以不建议使用
//        initCountDownTimer();
    }

    @OnClick(R2.id.tv_launcher_timer)
    public void onClick(View view){
        //结束计时
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
            ToastUtils.showLongToastSafe("计时被打断！");
        }
        toNext();

    }

    private void initTimer(){
        mTimer = new Timer();
        BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task,0, 1000);
    }

    /**
     * CountDownTimer 计时不准确，不建议使用
     */
    @Deprecated
    private void initCountDownTimer(){
        if (mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }

        mCountDownTimer = new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long l) {
                if (mTvLauncherTimer != null){
                    mTvLauncherTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount--;
                }
            }

            @Override
            public void onFinish() {
                ToastUtils.showLongToastSafe("计时结束!");
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvLauncherTimer != null){
                    mTvLauncherTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount--;
                    if (mCount < 0){
                        if (mTimer != null){
                            mTimer.cancel();
                            mTimer = null;
                        }
                        onTimerEnd();
                    }
                }
            }
        });
    }

    @Override
    public void onTimerEnd() {
        toNext();
    }

    private void toNext(){
        if (SPUtils.getIsUsedFlag()){
            //TODO 判断登陆状态，跳转到下一页面
            AccountManager.checkAccount(new IUserChecker() {//回调:细节未知,但是现在要用
                @Override
                public void onSignIn() {
                    if (mILauncherListener != null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if (mILauncherListener != null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });

        }else {
            //到引导页
//            start(new LauncherScrollDelegate(),SINGLETASK);
            startWithPop(new LauncherScrollDelegate());
        }
    }
}
