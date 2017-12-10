package com.zk.zkmall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.zk.ec.sign.SignUpDelegate;
import com.zk.zkcore.activities.ProxyActivity;
import com.zk.zkcore.delegates.CoreDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    @Override
    public CoreDelegate setRootDelegate() {
//        return new TestDelegate();
//        return new LauncherDelegate();
//        return new LauncherScrollDelegate();
        return new SignUpDelegate();
    }


}
