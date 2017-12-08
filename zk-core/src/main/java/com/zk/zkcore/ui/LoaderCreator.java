package com.zk.zkcore.ui;

import android.content.Context;
import android.text.TextUtils;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by Administrator on 2017/12/9.
 */

public final class LoaderCreator {
    private static final WeakHashMap<String,Indicator> LOADING_MAP = new WeakHashMap<>();

    public static AVLoadingIndicatorView create(Context context,String type){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type) == null){
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicator(String name){
        if (TextUtils.isEmpty(name)){
            return null;
        }
        final StringBuilder builder = new StringBuilder();
        if (!name.contains(".")){
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            builder.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }
        builder.append(name);
        try {
            Class<?> drawableClass = Class.forName(builder.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
