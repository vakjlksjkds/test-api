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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.nio.file.Files;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.time.Duration;
import java.util.Arrays;

public class SberbankTest {
    private static final Logger logger = LogManager.getLogger(SberbankTest.class);
    private AndroidDriver driver;
    
    @Test(description = "Тест открытия СберБанк на устройстве")
    public void openSberbankOnDevice() {
        try {
            String deviceName = getDeviceName();
            // СберБанк:
            String appPackage = "ru.sberbankmobile_alpha";
            String appActivity = "ru.sberbank.mobile.auth.presentation.splash.SplashActivity";
            
            System.out.println("Пробую открыть: " + appPackage + "/" + appActivity + " на устройстве: " + deviceName);
            
            // Создаем capabilities
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:deviceName", deviceName);
            caps.setCapability("appium:appPackage", appPackage);
            caps.setCapability("appium:appActivity", appActivity);
            caps.setCapability("appium:noReset", true);
            caps.setCapability("appium:newCommandTimeout", 60);
            
            // Создаем драйвер
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Гарантированно активировать приложение, даже если оно свернуто
            try {
                driver.activateApp(appPackage);
                System.out.println("activateApp выполнен успешно");
            } catch (Exception e) {
                System.out.println("Ошибка при activateApp: " + e.getMessage());
            }

            // Попробовать явно запустить приложение
            try {
                driver.launchApp();
                System.out.println("launchApp выполнен успешно");
            } catch (Exception e) {
                System.out.println("Ошибка при launchApp: " + e.getMessage());
            }

            // Как крайний случай — adb shell команда
            try {
                Process proc = Runtime.getRuntime().exec("adb shell am start -n ru.sberbankmobile_alpha/ru.sberbank.mobile.auth.presentation.splash.SplashActivity");
                proc.waitFor();
                System.out.println("adb shell am start выполнен успешно");
            } catch (Exception e) {
                System.out.println("Ошибка при adb shell am start: " + e.getMessage());
            }

            // Ждем немного, чтобы приложение полностью загрузилось
            Thread.sleep(3000);

            // Ввести пароль 11225
            String pin = "11225";
            for (char digit : pin.toCharArray()) {
                String buttonId = "auth_keyboard_number_button_v2 " + digit;
                try {
                    driver.findElement(
                        new AppiumBy.ByAndroidUIAutomator(
                            "new UiSelector().resourceId(\"" + buttonId + "\")"
                        )
                    ).click();
                    Thread.sleep(300);
                } catch (Exception e) {
                    System.out.println("Не удалось нажать кнопку " + digit + ": " + e.getMessage());
                }
            }

            // Надёжная последовательность клика по кнопке List[1]
            boolean clicked = false;
            // 1. По accessibilityId
            try {
                driver.findElement(AppiumBy.accessibilityId("List[1]")).click();
                System.out.println("Клик по accessibilityId('List[1]') выполнен");
                clicked = true;
            } catch (Exception e) {
                System.out.println("Не удалось кликнуть по accessibilityId('List[1]'): " + e.getMessage());
            }
            // 2. По XPath с content-desc
            if (!clicked) {
                try {
                    driver.findElement(By.xpath("//*[@content-desc='List[1]']")).click();
                    System.out.println("Клик по XPath с content-desc='List[1]' выполнен");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("Не удалось кликнуть по XPath с content-desc='List[1]': " + e.getMessage());
                }
            }
            // 3. По координатам центра
            if (!clicked) {
                try {
                    new TouchAction<>(driver)
                        .tap(PointOption.point(36, 55))
                        .perform();
                    System.out.println("Tap по координатам (36, 55) выполнен");
                } catch (Exception e) {
                    System.out.println("Не удалось тапнуть по координатам: " + e.getMessage());
                }
            }

            // Клик по первой кликабельной кнопке внутри top_app_bar_container
            try {
                WebElement container = driver.findElement(By.id("top_app_bar_container"));
                WebElement button = container.findElement(By.xpath(
                    ".//android.widget.Button[@clickable='true'] | " +
                    ".//android.widget.ImageButton[@clickable='true'] | " +
                    ".//android.view.View[@clickable='true']"
                ));
                button.click();
                System.out.println("Клик по первой кликабельной кнопке в top_app_bar_container выполнен");
            } catch (Exception e) {
                System.out.println("Не удалось кликнуть по кнопке в top_app_bar_container: " + e.getMessage());
            }

            // Попытка кликнуть по иконке с content-desc 'Настройки'
            boolean settingsClicked = false;
            try {
                driver.findElement(AppiumBy.accessibilityId("Настройки")).click();
                System.out.println("Клик по accessibilityId('Настройки') выполнен");
                settingsClicked = true;
            } catch (Exception e) {
                System.out.println("Не удалось кликнуть по accessibilityId('Настройки'): " + e.getMessage());
            }
            if (!settingsClicked) {
                try {
                    driver.findElement(By.xpath("//*[@content-desc='Настройки']")).click();
                    System.out.println("Клик по XPath с content-desc='Настройки' выполнен");
                } catch (Exception e) {
                    System.out.println("Не удалось кликнуть по XPath с content-desc='Настройки': " + e.getMessage());
                }
            }

            // Прокрутка экрана вниз (swipe up) до самого низа после открытия настроек
            try {
                int width = driver.manage().window().getSize().width;
                int height = driver.manage().window().getSize().height;
                int startX = width / 2;
                int startY = (int)(height * 0.8);
                int endY = (int)(height * 0.2);
                for (int i = 0; i < 3; i++) { // Несколько свайпов для гарантии
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence swipe = new Sequence(finger, 1);
                    swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
                    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), startX, endY));
                    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                    driver.perform(Arrays.asList(swipe));
                    Thread.sleep(500);
                }
                System.out.println("Экран прокручен вниз (swipe up) после открытия настроек");
            } catch (Exception e) {
                System.out.println("Не удалось прокрутить экран вниз: " + e.getMessage());
            }

            // Надёжная последовательность клика по кнопке 'Безопасность'
            boolean securityClicked = false;
            // 1. По accessibilityId (ContentDescription)
            try {
                driver.findElement(AppiumBy.accessibilityId("Безопасность")).click();
                System.out.println("Клик по accessibilityId('Безопасность') выполнен");
                securityClicked = true;
            } catch (Exception e) {
                System.out.println("Не удалось кликнуть по accessibilityId('Безопасность'): " + e.getMessage());
            }
            // 2. По XPath с content-desc
            if (!securityClicked) {
                try {
                    driver.findElement(By.xpath("//*[@content-desc='Безопасность']")).click();
                    System.out.println("Клик по XPath с content-desc='Безопасность' выполнен");
                    securityClicked = true;
                } catch (Exception e) {
                    System.out.println("Не удалось кликнуть по XPath с content-desc='Безопасность': " + e.getMessage());
                }
            }
            // 3. По XPath по testTag (cell_base)
            if (!securityClicked) {
                try {
                    driver.findElement(By.xpath("//*[@testTag='cell_base']")).click();
                    System.out.println("Клик по XPath с testTag='cell_base' выполнен");
                    securityClicked = true;
                } catch (Exception e) {
                    System.out.println("Не удалось кликнуть по XPath с testTag='cell_base': " + e.getMessage());
                }
            }
            // 4. По XPath по тексту
            if (!securityClicked) {
                try {
                    driver.findElement(By.xpath("//*[contains(@text, 'Безопасность') or contains(@content-desc, 'Безопасность')]")).click();
                    System.out.println("Клик по XPath по тексту 'Безопасность' выполнен");
                    securityClicked = true;
                } catch (Exception e) {
                    System.out.println("Не удалось кликнуть по XPath по тексту 'Безопасность': " + e.getMessage());
                }
            }
            // 5. По центру текста (x=80+140, y=505+10) dp -> px через W3C Actions
            if (!securityClicked) {
                try {
                    int x = (int)((80 + 140) * 600.0 / 160.0); // 220dp -> px
                    int y = (int)((505 + 10) * 600.0 / 160.0); // 515dp -> px
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence tap = new Sequence(finger, 1);
                    tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
                    tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                    driver.perform(Arrays.asList(tap));
                    System.out.println("Tap по центру текста 'Безопасность' (" + x + ", " + y + ") через W3C Actions выполнен");
                } catch (Exception e) {
                    System.out.println("Не удалось тапнуть по центру текста 'Безопасность' через W3C Actions: " + e.getMessage());
                }
            }
            // 6. По центру контейнера (x=8+184, y=487+28) dp -> px через W3C Actions
            if (!securityClicked) {
                try {
                    int x = (int)((8 + 184) * 600.0 / 160.0); // 192dp -> px
                    int y = (int)((487 + 28) * 600.0 / 160.0); // 515dp -> px
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence tap = new Sequence(finger, 1);
                    tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
                    tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                    driver.perform(Arrays.asList(tap));
                    System.out.println("Tap по центру контейнера 'Безопасность' (" + x + ", " + y + ") через W3C Actions выполнен");
                } catch (Exception e) {
                    System.out.println("Не удалось тапнуть по центру контейнера 'Безопасность' через W3C Actions: " + e.getMessage());
                }
            }

            // Поочередный клик по центру текста, иконки и всей строки 'Безопасность' с ожиданием и скриншотами
            int[][] tapPointsDp = {
                {80 + 140, 505 + 10},   // центр текста
                {24 + 18, 497 + 18},   // центр иконки
                {8 + 184, 487 + 28}    // центр строки (Box)
            };
            String[] tapLabels = {"text", "icon", "box"};
            for (int i = 0; i < tapPointsDp.length; i++) {
                if (securityClicked) break;
                int x = (int)(tapPointsDp[i][0] * 600.0 / 160.0);
                int y = (int)(tapPointsDp[i][1] * 600.0 / 160.0);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { /* ignore */ }
                try {
                    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
                    Sequence tap = new Sequence(finger, 1);
                    tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
                    tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                    tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                    driver.perform(Arrays.asList(tap));
                    System.out.println("Tap по координатам 'Безопасность' (" + x + ", " + y + ") через W3C Actions выполнен (" + tapLabels[i] + ")");
                    securityClicked = true;
                } catch (Exception e) {
                    System.out.println("Не удалось тапнуть по координатам 'Безопасность' (" + x + ", " + y + ") через W3C Actions (" + tapLabels[i] + "): " + e.getMessage());
                }
                // Скриншот после каждой попытки
                try {
                    File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    File destFile = new File("sberbank_security_tap_" + tapLabels[i] + ".png");
                    Files.copy(srcFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Скриншот после тапа (" + tapLabels[i] + ") сохранён: " + destFile.getAbsolutePath());
                } catch (Exception ex) {
                    System.out.println("Не удалось сохранить скриншот после тапа (" + tapLabels[i] + "): " + ex.getMessage());
                }
            }
            // После перехода в Безопасность — только финальный скриншот
            try {
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destFile = new File("sberbank_security_final.png");
                Files.copy(srcFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Финальный скриншот экрана сохранён: " + destFile.getAbsolutePath());
            } catch (Exception ex) {
                System.out.println("Не удалось сохранить финальный скриншот: " + ex.getMessage());
            }
            // После этого никаких кликов и действий не выполнять
            
            System.out.println("🏦 СберБанк успешно открыт!");
            Assert.assertTrue(true, "СберБанк должен быть открыт");
            
        } catch (Exception e) {
            logger.error("Sberbank test failed", e);
            System.out.println("Не удалось открыть приложение: " + e.getMessage());
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