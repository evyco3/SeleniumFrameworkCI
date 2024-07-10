package org.evy.framework.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.evy.framework.enums.BrowserType;
import org.evy.framework.enums.LogType;
import org.evy.framework.utils.LoggerUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.EnumMap;
import java.util.Map;

/**
 * Factory class to manage WebDriver instances based on BrowserType.
 * Used exclusively within the Driver class for setting up WebDriver instances.
 */
public final class DriverFactory {

    private static final Map<BrowserType,DriverSupplier> driversMap=new EnumMap<>(BrowserType.class);

    static {
        // Initialize the driversMap with suppliers for each supported BrowserType
        driversMap.put(BrowserType.CHROME,new ChromeDriverSupplier());
        driversMap.put(BrowserType.FIREFOX,new FirefoxDriverSupplier());
        driversMap.put(BrowserType.EDGE,new EdgeDriverSupplier());
        driversMap.put(BrowserType.EXPLORER,new InternetExplorerDriverSupplier());
        driversMap.put(BrowserType.SAFARI,new SafariDriverSupplier());
    }

    /**
     * Retrieves a WebDriver instance based on the specified BrowserType.
     *
     * @param browserType The type of browser for which to retrieve the WebDriver instance.
     * @return WebDriver instance for the specified browser type.
     * @throws UnsupportedOperationException If the specified browser type is not supported.
     * @throws RuntimeException If there is an error setting up the WebDriver instance.
     */
    static WebDriver getDriver(BrowserType browserType){
        DriverSupplier supplier=driversMap.get(browserType);
        try {
            if(supplier==null){
                LoggerUtils.log(DriverFactory.class, LogType.WARN,"Unsupported browserType: " + browserType);
                throw new UnsupportedOperationException("Unsupported browser type: " + browserType);
            }
            LoggerUtils.log(DriverFactory.class,LogType.INFO,"Setup Driver: "+browserType);
            return supplier.get();
        } catch (Exception e) {
            LoggerUtils.log(DriverFactory.class, LogType.ERROR, "Failed to setup driver for: " + browserType);
            throw new RuntimeException("Failed to setup driver for: " + browserType, e);
        }
    }

    /**
     * Supplier interface for creating WebDriver instances.
     */
    public interface DriverSupplier {
        WebDriver get();
    }

    /**
     * Supplier class for ChromeDriver.
     */
    private static class ChromeDriverSupplier implements DriverSupplier {
        @Override
        public WebDriver get() {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }
    }

    /**
     * Supplier class for FirefoxDriver.
     */
    private static class FirefoxDriverSupplier implements DriverSupplier {
        @Override
        public WebDriver get() {
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        }
    }

    /**
     * Supplier class for EdgeDriver.
     */
    private static class EdgeDriverSupplier implements DriverSupplier {
        @Override
        public WebDriver get() {
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver();
        }
    }

    /**
     * Supplier class for InternetExplorerDriver.
     */
    private static class InternetExplorerDriverSupplier implements DriverSupplier {
        @Override
        public WebDriver get() {
            WebDriverManager.iedriver().setup();
            return new InternetExplorerDriver();
        }
    }

    /**
     * Supplier class for SafariDriver.
     */
    private static class SafariDriverSupplier implements DriverSupplier {
        @Override
        public WebDriver get() {
            WebDriverManager.safaridriver().setup();
            return new SafariDriver();
        }
    }
}
