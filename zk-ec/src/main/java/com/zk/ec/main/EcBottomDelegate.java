package com.zk.ec.main;

import android.graphics.Color;

import com.zk.ec.main.cart.ShoppingCartDelegate;
import com.zk.ec.main.compass.CompassDelegate;
import com.zk.ec.main.index.IndexDelegate;
import com.zk.ec.main.mine.MineDelegate;
import com.zk.ec.main.sort.SortDelegate;
import com.zk.zkcore.delegates.bottom.BaseBottomDelegate;
import com.zk.zkcore.delegates.bottom.BottomItemDelegate;
import com.zk.zkcore.delegates.bottom.BottomTabBean;
import com.zk.zkcore.delegates.bottom.ItemBuilder;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/12/13.
 */

public class EcBottomDelegate extends BaseBottomDelegate{
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"),new IndexDelegate());
        items.put(new BottomTabBean("{fa-bars}","分类"),new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}","发现"),new CompassDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"),new ShoppingCartDelegate());
        items.put(new BottomTabBean("{fa-user}","我的"),new MineDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
