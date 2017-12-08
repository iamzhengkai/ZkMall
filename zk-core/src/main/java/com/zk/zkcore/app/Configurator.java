package com.zk.zkcore.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/8.
 */

public class Configurator {
    private static final HashMap<String,Object> CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private Configurator(){
        CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        private static Configurator INSTANCE = new Configurator();
    }

    public HashMap<String,Object> getConfigs(){
        return CONFIGS;
    }
    public final void configure(){
        initIcons();
        CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

    public Configurator withApiHost(String host){
        CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    private void checkConfigurations(){
        final boolean isReady = (boolean) CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady){
            throw new RuntimeException("Configurations is not completed! Call configure to complete.");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfigurations();
        return (T) CONFIGS.get(key.name());
    }

    private void initIcons(){
        if (ICONS.size() > 0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i=1;i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }

    public Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }
}
