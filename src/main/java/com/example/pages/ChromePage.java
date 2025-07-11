package com.example.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.example.pages.AppiumUnavailableException;

public class ChromePage {
    private AndroidDriver driver;

    private static class CameraApp {
        String appPackage;
        String appActivity;
        CameraApp(String pkg, String act) { appPackage = pkg; appActivity = act; }
    }

    // Samsung первым, остальные после
    private static final List<CameraApp> CAMERA_APPS = Arrays.asList(
        new CameraApp("com.sec.android.app.camera", "com.sec.android.app.camera.Camera"), // Samsung
        new CameraApp("com.android.camera", "com.android.camera.Camera"), // Xiaomi, AOSP
        new CameraApp("com.google.android.GoogleCamera", "com.google.android.apps.camera.CameraActivity"), // Google
        new CameraApp("com.motorola.camera2", "com.motorola.camera.Camera"), // Motorola
        new CameraApp("com.huawei.camera", "com.huawei.camera"), // Huawei
        new CameraApp("com.oppo.camera", "com.oppo.camera.Camera"), // Oppo
        new CameraApp("com.vivo.camera", "com.android.camera.Camera"), // Vivo
        new CameraApp("com.asus.camera", "com.asus.camera.CameraApp"), // Asus
        new CameraApp("com.lge.camera", "com.lge.camera.CameraApp"), // LG
        new CameraApp("com.htc.camera", "com.htc.camera.Camera"), // HTC
        new CameraApp("com.sonyericsson.android.camera", "com.sonyericsson.android.camera.CameraActivity") // Sony
    );

    private String getDeviceName() {
        String env = System.getenv("ANDROID_DEVICE_ID");
        if (env != null && !env.isEmpty()) return env;
        try {
            Process proc = Runtime.getRuntime().exec("adb devices");
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith("\tdevice")) {
                    return line.split("\t")[0];
                }
            }
        } catch (Exception e) {
            System.out.println("Не удалось получить deviceName из adb: " + e.getMessage());
        }
        return "Android Device";
    }

    public boolean openChromeOrFallback() {
        String deviceName = getDeviceName();
        System.out.println("Используется deviceName: " + deviceName);
        boolean chromeInstalled = isPackageInstalled("com.android.chrome");
        String appPackage, appActivity, appName;
        if (chromeInstalled) {
            appPackage = "com.android.chrome";
            appActivity = "com.google.android.apps.chrome.Main";
            appName = "Chrome";
            try {
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("platformName", "Android");
                caps.setCapability("appium:automationName", "UiAutomator2");
                caps.setCapability("appium:deviceName", deviceName);
                caps.setCapability("appium:appPackage", appPackage);
                caps.setCapability("appium:appActivity", appActivity);
                caps.setCapability("appium:noReset", true);
                System.out.println("Пробую открыть: " + appPackage + "/" + appActivity + " с automationName=UiAutomator2");
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
                System.out.println(appName + " успешно открыт!");
                return true;
            } catch (Exception e) {
                System.out.println("Не удалось открыть " + appName + ": " + e.getMessage());
            }
        }
        // Если Chrome не установлен или не открылся, пробуем калькулятор
        appPackage = "com.sec.android.app.calculator";
        appActivity = "com.sec.android.app.calculator.Calculator";
        appName = "Calculator";
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:deviceName", deviceName);
            caps.setCapability("appium:appPackage", appPackage);
            caps.setCapability("appium:appActivity", appActivity);
            caps.setCapability("appium:noReset", true);
            System.out.println("Пробую открыть: " + appPackage + "/" + appActivity + " с automationName=UiAutomator2");
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            System.out.println(appName + " успешно открыт!");
            return true;
        } catch (Exception e) {
            System.out.println("Не удалось открыть " + appName + ": " + e.getMessage());
        }
        throw new AppiumUnavailableException("Не удалось открыть Chrome или калькулятор. Проверьте, установлены ли эти приложения и доступен ли Appium.");
    }

    private boolean isPackageInstalled(String packageName) {
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"adb", "shell", "pm", "list", "packages", packageName});
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("package:" + packageName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при проверке наличия пакета: " + e.getMessage());
        }
        return false;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
} 