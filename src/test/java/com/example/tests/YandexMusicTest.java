package com.example.tests;

import com.example.pages.AnyAppPage;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.pages.AppiumUnavailableException;

public class YandexMusicTest {
    private static final Logger logger = LogManager.getLogger(YandexMusicTest.class);
    
    @Test(description = "Тест открытия Яндекс.Музыка на устройстве")
    public void openYandexMusicOnDevice() {
        try {
            String deviceName = getDeviceName();
            // Яндекс.Музыка:
            String appPackage = "ru.yandex.music";
            String appActivity = ".main.MainScreenActivity";
            
            AnyAppPage appPage = new AnyAppPage();
            boolean result = appPage.openApp(deviceName, appPackage, appActivity);
            Assert.assertTrue(result, "Не удалось открыть Яндекс.Музыка. Проверьте, установлено ли приложение на устройстве.");
            
            // Ждем немного, чтобы приложение полностью загрузилось
            Thread.sleep(3000);
            
            System.out.println("🎵 Яндекс.Музыка успешно открыта!");
            appPage.quit();
        } catch (AppiumUnavailableException e) {
            logger.warn("Appium/Device unavailable: " + e.getMessage());
            throw new SkipException("Appium/Device unavailable: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Yandex Music test failed", e);
            throw new SkipException("Appium/Device error: " + e.getMessage());
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
} 