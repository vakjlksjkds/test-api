package com.example.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleTest {
    
    @Test(description = "Простой тест для проверки работы TestNG")
    public void simpleTest() {
        System.out.println("Simple test is running!");
        Assert.assertTrue(true, "Simple test should pass");
    }
    
    @Test(description = "Еще один простой тест")
    public void anotherSimpleTest() {
        System.out.println("Another simple test is running!");
        Assert.assertEquals(1 + 1, 2, "Basic math should work");
    }
    
    @Test(description = "Тест с проверкой строк")
    public void stringTest() {
        String message = "Hello World";
        Assert.assertTrue(message.contains("Hello"), "Message should contain 'Hello'");
        Assert.assertEquals(message.length(), 11, "Message length should be 11");
    }
} 