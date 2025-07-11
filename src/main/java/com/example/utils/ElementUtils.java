package com.example.utils;

// import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class ElementUtils {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public ElementUtils(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10L);
    }

    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void click(By locator) {
        waitForElement(locator).click();
    }

    public void sendKeys(By locator, String text) {
        waitForElement(locator).sendKeys(text);
    }

    public boolean isElementPresent(By locator) {
        try {
            waitForElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean openChrome() {
        try {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:deviceName", "emulator-5554");
            caps.setCapability("appPackage", "com.android.chrome");
            caps.setCapability("appActivity", "com.google.android.apps.chrome.Main");
            caps.setCapability("noReset", true);
            caps.setCapability("appium:newCommandTimeout", 300);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            System.out.println("Chrome успешно открыт!");
            return true;
        } catch (Exception e) {
            System.out.println("Не удалось открыть Chrome: " + e.getMessage());
            return false;
        }
    }
} 