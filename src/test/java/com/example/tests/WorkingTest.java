package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WorkingTest {
    
    @Test(description = "Рабочий тест без зависимостей")
    public void workingTest() {
        System.out.println("Working test is running!");
        Assert.assertTrue(true, "This test should always pass");
    }
    
    @Test(description = "Тест математики")
    public void mathTest() {
        int result = 2 + 2;
        Assert.assertEquals(result, 4, "2 + 2 should equal 4");
        System.out.println("Math test passed: 2 + 2 = " + result);
    }
    
    @Test(description = "Тест строк")
    public void stringTest() {
        String message = "Hello TestNG";
        Assert.assertTrue(message.length() > 0, "Message should not be empty");
        Assert.assertTrue(message.contains("TestNG"), "Message should contain TestNG");
        System.out.println("String test passed: " + message);
    }
} 