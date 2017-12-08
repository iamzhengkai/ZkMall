package com.zk.zkcore.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class BaseDelegate extends SwipeBackFragment {

    private Unbinder mUnbinder = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer){
            rootView = inflater.inflate((int)setLayout(),container,false);
        }else if (setLayout() instanceof View){
            rootView = (View) setLayout();
        }else {
            throw new RuntimeException("setLayout must return a resId or a View!");
        }
        if (rootView != null){
            mUnbinder = ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        }
        return rootView;
    }

    //返回值可以是一个view，也可以是一个layoutId，所以声明为Object
    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState,View rootView);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
