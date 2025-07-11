package com.example;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class OpenYandexMusic {
    public static void main(String[] args) {
        String deviceName = getDeviceName();
        String appPackage = "ru.yandex.music";
        String appActivity = ".main.MainScreenActivity";
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:deviceName", deviceName);
            caps.setCapability("appium:appPackage", appPackage);
            caps.setCapability("appium:appActivity", appActivity);
            caps.setCapability("appium:noReset", true);
            System.out.println("Открываю приложение: " + appPackage + "/" + appActivity);
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);
            Thread.currentThread().join(); // Оставляем процесс живым, приложение не закроется
        } catch (Exception e) {
            System.out.println("Ошибка запуска приложения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String getDeviceName() {
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
            System.out.println("Не удалось получить deviceName: " + e.getMessage());
        }
        return "Android Device";
    }
} 