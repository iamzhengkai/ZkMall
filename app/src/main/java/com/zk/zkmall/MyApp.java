package com.zk.zkmall;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.zk.ec.database.DatabaseManager;
import com.zk.ec.icon.FontEcMoudle;
import com.zk.zkcore.app.ConfigurationManager;
import com.zk.zkcore.net.interceptors.DebugInterceptor;
import com.zk.zkcore.net.interceptors.GlobalHeaderInterceptor;
import com.zk.zkcore.net.interceptors.LoggingInterceptor;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ConfigurationManager.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcMoudle())
                .withApiHost("http://127.0.0.1")
                .withBombApplictionId("7a90f3d08dcdf248c9ef28449626f077")
                .withBombRestApiKey("6eae5b00c5ccbfafcf3aaa1a2fc01a67")
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .withInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .withNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .withInterceptor(new LoggingInterceptor())
//                .withNetworkInterceptor(new LoggingInterceptor())
//                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .withInterceptor(new GlobalHeaderInterceptor())
                .configure();

        initStetho();
        DatabaseManager.getInstance().init(this);

    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );

        ;
    }
}
