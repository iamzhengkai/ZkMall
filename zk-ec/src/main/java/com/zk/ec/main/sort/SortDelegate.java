package com.zk.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.ec.R;
import com.zk.zkcore.delegates.bottom.BottomItemDelegate;

/**
 * Created by Administrator on 2017/12/13.
 */

public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
