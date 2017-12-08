package com.zk.zkcore.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/8.
 */

public final class Mall {
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }
    public static HashMap<String,Object> getConfigurations(){
        return Configurator.getInstance().getConfigs();
    }

    public static Context getAppContext(){
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
