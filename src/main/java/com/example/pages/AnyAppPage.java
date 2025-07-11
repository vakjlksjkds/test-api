package com.example.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import com.example.pages.AppiumUnavailableException;

public class AnyAppPage {
    private AndroidDriver driver;

    public boolean openApp(String deviceName, String appPackage, String appActivity) {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("automationName", "UiAutomator2");
            caps.setCapability("deviceName", deviceName);
            caps.setCapability("appPackage", appPackage);
            caps.setCapability("appActivity", appActivity);
            System.out.println("Пробую открыть: " + appPackage + "/" + appActivity + " на устройстве: " + deviceName);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            System.out.println("Приложение успешно открыто!");
            return true;
        } catch (Exception e) {
            System.out.println("Не удалось открыть приложение: " + e.getMessage());
            throw new AppiumUnavailableException("Не удалось открыть приложение: " + e.getMessage(), e);
        }
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
} 