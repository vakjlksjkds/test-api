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

---

## Быстрый старт: автоматическая подготовка окружения

### macOS/Linux/WSL
1. Откройте терминал в папке проекта.
2. Выполните:
   ```sh
   chmod +x setup_env.sh
   ./setup_env.sh
   ```
   Скрипт автоматически проверит и установит:
   - Java JDK
   - Node.js и npm
   - Appium
   - adb (Android Platform Tools)
   - Права на gradlew и run_tests.sh
   - Переменные среды для Android SDK
   - Подключение устройства
   - Сборку проекта

### Windows
1. Откройте **Git Bash** или **WSL** в папке проекта.
2. Выполните:
   ```sh
   bash setup_env.sh
   ```
   Если что-то не установится автоматически, следуйте инструкциям скрипта (например, скачайте Java, Node.js, Android SDK вручную).

#### Альтернатива для Windows (cmd.exe)
- Для запуска в обычном cmd.exe нужен отдельный `.bat`-скрипт (по запросу).
- В .bat-скрипте используются Chocolatey/winget для установки зависимостей.
- Если нужен такой скрипт — напишите!

---

## Запуск тестов
После подготовки окружения:
```sh
./run_tests.sh
```
или
```sh
./gradlew test
```

---

## Важно
- Для мобильных тестов должен быть установлен Android SDK и подключено устройство (или эмулятор).
- Appium Server должен быть запущен:
  ```sh
  appium --base-path /wd/hub --port 4723
  ```
- Если что-то не работает — проверьте переменные среды и логи. 

