package com.zk.ec.main.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.ec.R;
import com.zk.zkcore.delegates.bottom.BottomItemDelegate;

/**
 * Created by Administrator on 2017/12/13.
 */

public class CategoryDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_category;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
