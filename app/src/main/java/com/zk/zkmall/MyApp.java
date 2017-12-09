package com.zk.zkmall;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.zk.ec.icon.FontEcMoudle;
import com.zk.zkcore.app.Core;
import com.zk.zkcore.net.interceptors.DebugInterceptor;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Core.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcMoudle())
                .withApiHost("http://127.0.0.1")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .configure();
    }
}
