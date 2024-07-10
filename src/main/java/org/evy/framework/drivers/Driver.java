package org.evy.framework.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.evy.framework.config.ConfigManager;
import org.evy.framework.enums.BrowserType;
import org.evy.framework.enums.LogType;
import org.evy.framework.utils.LoggerUtils;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

/**
 * Singleton class managing WebDriver instances using ThreadLocal for thread safety.
 * This class handles initialization, configuration, and quitting of WebDriver.
 * Methods initDriver and quitDriver are intended to be used exclusively within a test framework
 * (@TestBase Class) setup and tearDown methods.
 */
public final class Driver {

    private static final ThreadLocal<WebDriver> driverThreads = new ThreadLocal<>();

    private static final Driver instance = new Driver();

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private Driver() {
    }

    /**
     * Retrieves the singleton instance of Driver.
     *
     * @return Singleton instance of Driver.
     */
    public static Driver getInstance() {
        return instance;
    }

    /**
     * Initializes a WebDriver instance based on the specified BrowserType.
     * This method sets up the WebDriver, configures it according to framework settings,
     * and logs initialization details.
     *
     * @param browserType The type of browser to initialize.
     * @throws RuntimeException If WebDriver initialization fails.
     */
    public void initDriver(BrowserType browserType) {
        try {
            WebDriver driver = DriverFactory.getDriver(browserType);
            if (driver != null) {
                driverThreads.set(driver);
                LoggerUtils.log(Driver.class, LogType.INFO, "Initialize Driver");
                configuration(driver);
            } else {
                LoggerUtils.log(Driver.class, LogType.ERROR, "Failed to create WebDriver instance");
                throw new RuntimeException("Unable to initialize Driver.. WebDriver instance is null");
            }
        } catch (Exception e) {
            LoggerUtils.log(Driver.class, LogType.ERROR, "Error During initialization driver");
            throw new RuntimeException("Unable to initialize Driver..", e);
        }
    }

    /**
     * Quits the current WebDriver instance.
     * This method closes the WebDriver and logs the operation.
     *
     * @throws RuntimeException If WebDriver quitting fails.
     */
    public void quitDriver() {
        try {
            WebDriver driver = driverThreads.get();
            if (driver != null) {
                driver.quit();
                driverThreads.remove();
                LoggerUtils.log(Driver.class, LogType.INFO, "Quitting Driver");
            } else {
                LoggerUtils.log(Driver.class, LogType.WARN, "WebDriver instance is null. Skipping quit operation.");
            }
        } catch (Exception e) {
            LoggerUtils.log(Driver.class, LogType.ERROR, "Error during quitting driver");
            throw new RuntimeException("Unable to quit Driver ..", e.getCause());
        }
    }

    /**
     * Configures the WebDriver instance with implicit wait, page load timeout, maximizes the window,
     * and navigates to the specified URL.
     *
     * @param driver The WebDriver instance to configure.
     */
    public void configuration(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.get().implicitTime()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigManager.get().pageLoadTime()));
        driver.manage().window().maximize();
        driver.get(ConfigManager.get().url());
    }

    /**
     * Retrieves the current WebDriver instance associated with the current thread.
     *
     * @return The WebDriver instance.
     */
    public WebDriver getDriver() {
        return driverThreads.get();
    }
}
