package com.zk.zkmall;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.zk.ec.icon.FontEcMoudle;
import com.zk.zkcore.app.Mall;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Mall.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcMoudle())
                .withApiHost("http://www.baidu.com")
                .configure();
    }
}
