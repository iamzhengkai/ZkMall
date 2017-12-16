package com.zk.zkcore.delegates.bottom;

import android.view.KeyEvent;
import android.view.View;

import com.zk.zkcore.delegates.Delegate;
import com.zk.zkcore.util.ToastUtils;

/**
 * 实际每个tab页的内容
 * Created by Administrator on 2017/12/13.
 */

public abstract class BottomItemDelegate
        extends Delegate implements View.OnKeyListener{
    private long mExitTime = 0;
    private static final int EXIT_TIME = 2000;

    @Override
    public void onResume() {
        super.onResume();
        //为了确保能够相应按键
        View rootView = getView();
        if (rootView != null){
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(this);
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis() - mExitTime) > EXIT_TIME){
                ToastUtils.showShortToast("再按一次退出");
                mExitTime = System.currentTimeMillis();
            }else {
                //退出应用
                _mActivity.finish();
                if(mExitTime != 0){
                    mExitTime = 0;
                }
            }
            return true;
        }
        return false;
    }
}
