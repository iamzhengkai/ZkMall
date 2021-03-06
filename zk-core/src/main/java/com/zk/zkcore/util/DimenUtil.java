package com.zk.zkcore.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.zk.zkcore.app.ConfigurationManager;

/**
 * Created by Administrator on 2017/12/9.
 */

public class DimenUtil {
    public static int getScreenWidth(){
        final Resources resources = ConfigurationManager.getApplicationContext().getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight(){
        final Resources resources = ConfigurationManager.getApplicationContext().getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        return metrics.heightPixels;
    }
}
