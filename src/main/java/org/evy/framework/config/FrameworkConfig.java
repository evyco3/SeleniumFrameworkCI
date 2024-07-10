package org.evy.framework.config;

import org.aeonbits.owner.Config;
import org.evy.framework.enums.BrowserType;


/**
 * This interface defines the configuration settings for the framework.
 * It uses the Owner library to map properties from a config file to Java methods.
 */

@Config.Sources("file:${user.dir}/src/main/resources/config.properties")
public interface FrameworkConfig extends Config {

    @DefaultValue("CHROME")
    @Key("browserType")
    @ConverterClass(BrowserTypeConverter.class)
    BrowserType browserType();

    @Key("url")
    String url();

    @Key("email")
    String email();

    @Key("password")
    String password();

    @Key("implicitTime")
    int implicitTime();

    @Key("pageLoadTime")
    int pageLoadTime();


}
