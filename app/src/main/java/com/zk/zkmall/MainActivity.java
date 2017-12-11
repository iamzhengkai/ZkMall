package com.zk.zkmall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.zk.ec.launcher.LauncherDelegate;
import com.zk.ec.sign.ISignListener;
import com.zk.ec.sign.SignUpDelegate;
import com.zk.ec.sign.SigninDelegate;
import com.zk.zkcore.activities.ProxyActivity;
import com.zk.zkcore.delegates.CoreDelegate;
import com.zk.zkcore.ui.launcher.ILauncherListener;
import com.zk.zkcore.ui.launcher.OnLauncherFinishTag;
import com.zk.zkcore.util.ToastUtils;

public class MainActivity extends ProxyActivity implements ISignListener, ILauncherListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public CoreDelegate setRootDelegate() {
//        return new TestDelegate();
        return new LauncherDelegate();
//        return new LauncherScrollDelegate();
//        return new SignUpDelegate();
    }


    @Override
    public void onSignInSuccess() {
        ToastUtils.showLongToast("登陆成功!");
        startWithPop(new TestDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        ToastUtils.showLongToast("注册成功!");
        startWithPop(new TestDelegate());
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                ToastUtils.showLongToast("启动结束,用户已登陆");
                startWithPop(new TestDelegate());
                break;
            case NOT_SIGNED:
                ToastUtils.showLongToast("启动结束,用户没有登陆");
                startWithPop(new SigninDelegate());
                break;
            default:
                break;
        }
    }
}
