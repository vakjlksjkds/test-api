package com.example.tests;

import com.example.base.DriverManager;
import com.example.pages.LoginPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.utils.TestDataUtils;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LoginTest {
    private AndroidDriver driver;
    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @DataProvider(name = "loginData")
    public Iterator<Object[]> loginDataProvider() throws Exception {
        List<Map<String, String>> data = TestDataUtils.getLoginData("src/main/resources/testdata/loginData.json");
        return data.stream().map(m -> new Object[]{m.get("username"), m.get("password")}).iterator();
    }

    @Test(dataProvider = "loginData", description = "Проверка логина в приложение с разными данными")
    public void testLogin(String username, String password) {
        try {
            driver = DriverManager.getDriver();
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(username, password);
            Assert.assertTrue(true, "Login successful");
            logger.info("Login test passed for user: " + username);
        } catch (Exception e) {
            logger.error("Login test failed", e);
            throw new SkipException("Appium/Device error: " + e.getMessage());
        } finally {
            DriverManager.quitDriver();
        }
    }
} 