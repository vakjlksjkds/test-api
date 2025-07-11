# Appium Test Project для Android Studio

Этот проект содержит автоматизированные тесты для Android приложений с использованием Appium, TestNG и Page Object Model.

## Требования

- Android Studio 2023.1 или новее
- Java 11 или новее
- Appium Server 1.x (рекомендуется версия 1.22.3)
- Android SDK
- Подключенное Android устройство или эмулятор

## Импорт проекта в Android Studio

1. Откройте Android Studio
2. Выберите "Open an existing Android Studio project"
3. Укажите путь к папке проекта
4. Дождитесь синхронизации Gradle

## Структура проекта

```
src/
├── main/java/com/example/
│   ├── Main.java                    # Главный класс приложения
│   ├── base/
│   │   └── BaseTest.java           # Базовый класс для тестов
│   ├── pages/
│   │   ├── LoginPage.java          # Page Object для страницы входа
│   │   └── CalculatorPage.java     # Page Object для калькулятора
│   └── utils/
│       ├── AppiumUtils.java        # Утилиты для Appium
│       └── TestDataLoader.java     # Загрузчик тестовых данных
└── test/java/com/example/tests/
    ├── LoginTest.java              # Тест входа в приложение
    ├── CalculatorTest.java         # Тест открытия калькулятора
    └── ChromeTest.java            # Тест открытия Chrome
```

## Настройка окружения

### 1. Установка Appium Server 1.x

```bash
# Удалите Appium 2.x если установлен
npm uninstall -g appium

# Установите Appium 1.x
npm install -g appium@1.22.3
```

### 2. Проверка подключения устройства

```bash
adb devices
```

### 3. Запуск Appium Server

```bash
appium
```

## Запуск тестов

### Через Android Studio

1. Откройте проект в Android Studio
2. Перейдите в папку `src/test/java/com/example/tests/`
3. Правый клик на тестовом классе → "Run 'TestClassName'"

### Через Gradle

```bash
# Запуск всех тестов
./gradlew test

# Запуск конкретного теста
./gradlew test --tests LoginTest

# Запуск с отчетом Allure
./gradlew test
./gradlew allureReport
./gradlew allureServe
```

### Через TestNG

```bash
# Запуск через TestNG XML
./gradlew test --tests "TestNG"
```

## Конфигурация тестов

### Настройки Appium

Основные настройки находятся в `src/main/java/com/example/utils/AppiumUtils.java`:

```java
// Настройки для реального устройства
DesiredCapabilities capabilities = new DesiredCapabilities();
capabilities.setCapability("platformName", "Android");
capabilities.setCapability("deviceName", "Android Device");
capabilities.setCapability("automationName", "UiAutomator2");
```

### Тестовые данные

Тестовые данные хранятся в JSON файлах в `src/test/resources/`:

- `login_data.json` - данные для тестов входа
- `test_data.json` - общие тестовые данные

## Отчеты

### Allure Reports

После выполнения тестов:

```bash
# Генерация отчета
./gradlew allureReport

# Просмотр отчета в браузере
./gradlew allureServe
```

### TestNG Reports

Отчеты TestNG автоматически генерируются в `build/reports/tests/`

## Устранение проблем

### Ошибка "NoSuchMethodError"

Убедитесь, что используется совместимая версия Selenium:
- Appium 1.x → Selenium 3.141.59
- Appium 2.x → Selenium 4.x

### Ошибка "Response code 404"

1. Проверьте, что Appium Server запущен
2. Убедитесь, что устройство подключено: `adb devices`
3. Проверьте версию Appium Server: `appium --version`

### Проблемы с Android Studio

1. Синхронизируйте проект: File → Sync Project with Gradle Files
2. Очистите проект: Build → Clean Project
3. Перезапустите Android Studio

## Полезные команды

```bash
# Очистка проекта
./gradlew clean

# Сборка проекта
./gradlew build

# Запуск тестов с подробным выводом
./gradlew test --info

# Проверка подключенных устройств
adb devices

# Перезапуск ADB сервера
adb kill-server
adb start-server
```

## Поддержка

При возникновении проблем:

1. Проверьте логи в папке `logs/`
2. Убедитесь в совместимости версий Appium Server и Java Client
3. Проверьте подключение устройства через ADB 