package org.evy.framework.config;

import org.aeonbits.owner.Converter;
import org.evy.framework.enums.BrowserType;

import java.lang.reflect.Method;
/**
 * This class is a custom converter that transforms a string representation
 * of a browser type into a BrowserType enum.
 */

public final class BrowserTypeConverter implements Converter<BrowserType> {


    @Override
    public BrowserType convert(Method method, String browserType) {
        return BrowserType.valueOf(browserType.toUpperCase());
    }


}
