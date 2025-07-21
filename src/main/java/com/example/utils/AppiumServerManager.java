package com.example.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppiumServerManager {
    public static void checkAppiumServerAvailable() {
        if (!isAppiumRunning()) {
            throw new RuntimeException("Appium server is not running on http://localhost:4723/wd/hub. Пожалуйста, запустите Appium сервер вручную перед запуском тестов.");
        }
    }

    public static boolean isAppiumRunning() {
        try {
            URL url = new URL("http://localhost:4723/wd/hub/status");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000);
            conn.connect();
            return conn.getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }
} 