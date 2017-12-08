package com.zk.zkcore.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.zk.zkcore.R;
import com.zk.zkcore.delegates.MallDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class ProxyActivity extends SupportActivity{
    public abstract MallDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }
    private void initContainer(@Nullable Bundle savedInstanceState){
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container_id);
        setContentView(container);
        if (savedInstanceState == null){
            loadRootFragment(R.id.delegate_container_id,setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
