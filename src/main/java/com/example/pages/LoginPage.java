package com.example.pages;

import com.example.utils.ElementUtils;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class LoginPage {
    private AndroidDriver driver;
    private ElementUtils elementUtils;

    private By usernameField = By.id("com.example:id/username");
    private By passwordField = By.id("com.example:id/password");
    private By loginButton = By.id("com.example:id/login");

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.elementUtils = new ElementUtils(driver);
    }

    public void login(String username, String password) {
        elementUtils.sendKeys(usernameField, username);
        elementUtils.sendKeys(passwordField, password);
        elementUtils.click(loginButton);
    }
} 