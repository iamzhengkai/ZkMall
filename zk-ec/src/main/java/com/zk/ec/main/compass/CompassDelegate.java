package com.zk.ec.main.compass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.ec.R;
import com.zk.zkcore.delegates.bottom.BottomItemDelegate;

/**
 * Created by Administrator on 2017/12/13.
 */

public class CompassDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_compass;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
