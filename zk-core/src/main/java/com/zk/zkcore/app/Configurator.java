package com.zk.zkcore.app;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * 配置信息存储类，
 * 实际存储配置信息，同时提供配置设置以及存取接口
 * Created by Administrator on 2017/12/8.
 */

public class Configurator {
    private static final HashMap<Object, Object> CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final ArrayList<Interceptor> NETWORK_INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();

    private Configurator() {
        CONFIGS.put(ConfigType.CONFIG_READY, false);
        CONFIGS.put(ConfigType.HANDLER,HANDLER);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static Configurator INSTANCE = new Configurator();
    }

    public HashMap<Object, Object> getConfigs() {
        return CONFIGS;
    }

    public final void configure() {
        initIcons();
        CONFIGS.put(ConfigType.CONFIG_READY, true);
    }

    public Configurator withApiHost(String host) {
        CONFIGS.put(ConfigType.API_HOST, host);
        return this;
    }

    private void checkConfigurations() {
        final boolean isReady = (boolean) CONFIGS.get(ConfigType.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configurations is not completed! Call configure to complete.");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfigurations();
        return (T) CONFIGS.get(key);
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        CONFIGS.put(ConfigType.ICON, ICONS);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        CONFIGS.put(ConfigType.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        CONFIGS.put(ConfigType.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity){
        CONFIGS.put(ConfigType.ACTIVITY,activity);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withNetworkInterceptor(Interceptor interceptor) {
        NETWORK_INTERCEPTORS.add(interceptor);
        CONFIGS.put(ConfigType.NETWORK_INTERCEPTOR, NETWORK_INTERCEPTORS);
        return this;
    }



    public final Configurator withBombApplictionId(String bombApplictionId){
        CONFIGS.put(ConfigType.BMOB_APPLICATION_ID,bombApplictionId);
        return this;
    }

    public final Configurator withBombRestApiKey(String bombRestApiKey){
        CONFIGS.put(ConfigType.BMOB_REST_API_KEY,bombRestApiKey);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }


}
