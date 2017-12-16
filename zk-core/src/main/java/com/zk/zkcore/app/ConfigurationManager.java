package com.zk.zkcore.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
 * 配置信息管理类
 * 用于在Application创建时对所有配置参数进行统一配置
 * 管理全局的配置信息，包括所有需要动态配置的参数
 * 是对配置类Configurator的进一步封装，相当于操作Configurator的一个工具类
 * 实际配置信息存放于Configurator中
 * Created by Administrator on 2017/12/8.
 */

public final class ConfigurationManager {
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<Object, Object> getConfigurations() {
        return Configurator.getInstance().getConfigs();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT);
    }

    public static Handler getHandler(){
        return getConfiguration(ConfigType.HANDLER);
    }
}
