#!/bin/bash

# Скрипт для запуска теста Яндекс.Музыка с правильными переменными окружения

echo "🔧 Настройка переменных окружения..."
export ANDROID_HOME=/Users/vlad/Library/Android/sdk
export ANDROID_SDK_ROOT=/Users/vlad/Library/Android/sdk

echo "📱 Проверка подключения устройства..."
adb devices

echo "🎵 Запуск теста Яндекс.Музыка..."
./gradlew clean test --no-build-cache --rerun-tasks --info

echo "✅ Тест завершен!" 