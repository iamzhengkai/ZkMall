package com.zk.ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.zk.ec.R;
import com.zk.zkcore.ui.recycler.RgbValue;

/**
 * Created by Administrator on 2017/12/16.
 */

public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar>{
    //顶部距离
    private int mDistanceY = 0;
    //变化的速率
    private static final int SHOW_SPEED = 3;
    //定义变化的颜色
    private final RgbValue RGB_VALUE = RgbValue.create(255,124,2);
    public TranslucentBehavior() {
    }
    //TODO 必须要有两个参数的构造方法，不然会报错。因为CoordinateLayout是通过反射该构造方法来创建对象的
    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rv_index;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    //TODO bug修改：快速滑动时 透明度异常
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        //增加滑动距离
        mDistanceY += dy;
        //toolbar的高度
        final int targetHeight = child.getBottom();
        //当滑动时，并且距离小于 toolbar 高度的时候，调整渐变色
        if(mDistanceY > 0 && mDistanceY <= targetHeight){
            final float scale = Math.abs((float) mDistanceY/targetHeight);
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int) alpha,RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));
        }else if (mDistanceY > targetHeight){
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));
        }
    }
}
