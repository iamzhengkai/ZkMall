package com.zk.ec.main.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.ec.R;
import com.zk.zkcore.delegates.Delegate;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by Administrator on 2017/12/16.
 */

public class GoodsDetailDelegate extends Delegate {

    public static GoodsDetailDelegate create(){
        return new GoodsDetailDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    //fragment 切换动画
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
