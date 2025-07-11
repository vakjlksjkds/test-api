#!/bin/bash

# Скрипт для запуска тестов с правильными переменными окружения

echo "🔧 Настройка переменных окружения..."
export ANDROID_HOME=/Users/vlad/Library/Android/sdk
export ANDROID_SDK_ROOT=/Users/vlad/Library/Android/sdk

echo "📱 Проверка подключения устройства..."
adb devices

echo "🚀 Запуск тестов..."
./gradlew test

echo "✅ Тесты завершены!" 