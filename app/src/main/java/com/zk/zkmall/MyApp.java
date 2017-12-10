package com.zk.zkmall;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.zk.ec.icon.FontEcMoudle;
import com.zk.zkcore.app.Core;
import com.zk.zkcore.net.interceptors.DebugInterceptor;
import com.zk.zkcore.net.interceptors.GlobalHearInterceptor;

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
                .withBombApplictionId("7a90f3d08dcdf248c9ef28449626f077")
                .withBombRestApiKey("6eae5b00c5ccbfafcf3aaa1a2fc01a67")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .withInterceptor(new GlobalHearInterceptor())
                .configure();
    }
}
