package org.evy.framework.config;

import org.aeonbits.owner.ConfigCache;

/**
 * This class provides a method to access the framework configuration settings.
 * It uses ConfigCache to create or retrieve the configuration instance.
 */

public final class ConfigManager {

    private ConfigManager(){}

    public static FrameworkConfig get(){
        return ConfigCache.getOrCreate(FrameworkConfig.class);
    }


}
