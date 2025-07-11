package com.example.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DriverManager {
    private static AndroidDriver driver;
    private static Properties properties = new Properties();

    public static AndroidDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    private static void initDriver() {
        try {
            properties.load(new FileInputStream("src/main/resources/appium.properties"));
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, properties.getProperty("platformName"));
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getProperty("deviceName"));
            caps.setCapability(MobileCapabilityType.APP, properties.getProperty("appPath"));
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, properties.getProperty("automationName", "UiAutomator2"));
            driver = new AndroidDriver(new URL(properties.getProperty("appiumServerURL")), caps);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium server URL", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load appium.properties", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
} 