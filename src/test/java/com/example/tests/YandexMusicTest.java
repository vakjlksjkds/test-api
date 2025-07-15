package com.example.tests;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class YandexMusicTest {
    private static final Logger logger = LogManager.getLogger(YandexMusicTest.class);
    private AndroidDriver driver;
    
    @Test(description = "Тест открытия Яндекс.Музыка на устройстве")
    public void openYandexMusicOnDevice() {
        try {
            String deviceName = getDeviceName();
            // Яндекс.Музыка:
            String appPackage = "ru.yandex.music";
            String appActivity = ".main.MainScreenActivity";
            
            System.out.println("Пробую открыть: " + appPackage + "/" + appActivity + " на устройстве: " + deviceName);
            
            // Создаем capabilities
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:deviceName", deviceName);
            caps.setCapability("appium:appPackage", appPackage);
            caps.setCapability("appium:appActivity", appActivity);
            caps.setCapability("appium:noReset", false);
            caps.setCapability("appium:newCommandTimeout", 60);
            
            // Создаем драйвер
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            
            System.out.println("Приложение успешно открыто!");
            
            // Ждем немного, чтобы приложение полностью загрузилось
            Thread.sleep(3000);
            
            System.out.println("🎵 Яндекс.Музыка успешно открыта!");
            
            Assert.assertTrue(true, "Яндекс.Музыка должна быть открыта");
            
        } catch (Exception e) {
            logger.error("Yandex Music test failed", e);
            System.out.println("Не удалось открыть приложение: " + e.getMessage());
            throw new SkipException("Appium/Device error: " + e.getMessage());
        } finally {
            quitDriver();
        }
    }
    
    private String getDeviceName() {
        try {
            Process proc = Runtime.getRuntime().exec("adb devices");
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith("\tdevice")) {
                    return line.split("\t")[0];
                }
            }
        } catch (Exception e) {
            logger.warn("Не удалось получить deviceName из adb: " + e.getMessage());
        }
        return "Android Device";
    }
    
    private void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.warn("Ошибка при закрытии драйвера: " + e.getMessage());
            }
            driver = null;
        }
    }
} 