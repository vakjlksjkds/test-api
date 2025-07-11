# Инструкции по импорту проекта в Android Studio

## Шаг 1: Открытие проекта

1. Запустите Android Studio
2. Выберите "Open an existing Android Studio project"
3. Укажите путь к папке проекта: `/Users/vlad/Downloads/+7 996-910-78-81/тест`
4. Нажмите "OK"

## Шаг 2: Ожидание синхронизации

- Android Studio автоматически обнаружит Gradle проект
- Дождитесь завершения синхронизации Gradle (индикатор внизу справа)
- Если появится уведомление о Gradle JDK, выберите Java 11 или новее

## Шаг 3: Проверка структуры проекта

После импорта вы должны увидеть следующую структуру:

```
AppiumTestProject/
├── .idea/                    # Конфигурация Android Studio
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── Main.java           # Главный класс
│   │   │   ├── base/
│   │   │   │   └── DriverManager.java
│   │   │   ├── pages/
│   │   │   │   ├── LoginPage.java
│   │   │   │   ├── ChromePage.java
│   │   │   │   └── AnyAppPage.java
│   │   │   └── utils/
│   │   │       ├── ElementUtils.java
│   │   │       └── TestDataUtils.java
│   │   └── resources/
│   │       ├── appium.properties
│   │       └── log4j2.xml
│   └── test/
│       ├── java/com/example/tests/
│       │   ├── LoginTest.java
│       │   ├── ChromeOpenTest.java
│       │   └── AnyAppOpenTest.java
│       └── resources/
│           └── testng.xml
├── build.gradle              # Конфигурация Gradle
├── settings.gradle
├── gradle.properties
└── README.md
```

## Шаг 4: Проверка конфигурации

### Проверьте файлы конфигурации:

1. **build.gradle** - должен содержать все необходимые зависимости
2. **settings.gradle** - должен содержать `rootProject.name = 'AppiumTestProject'`
3. **gradle.properties** - настройки для Android Studio

### Проверьте конфигурации запуска:

1. Откройте "Run/Debug Configurations" (Run → Edit Configurations)
2. Должны быть доступны:
   - **Main** - для запуска главного класса
   - **TestNG** - для запуска тестов

## Шаг 5: Тестирование импорта

### Запуск главного класса:

1. Откройте `src/main/java/com/example/Main.java`
2. Нажмите зеленую кнопку "Run" рядом с методом `main`
3. Должно появиться сообщение в консоли

### Запуск тестов:

1. Откройте любой тестовый класс (например, `LoginTest.java`)
2. Нажмите зеленую кнопку "Run" рядом с классом или методом
3. Тесты запустятся (могут упасть, если Appium сервер не запущен)

## Шаг 6: Настройка окружения

### Установка Appium Server 1.x:

```bash
# Удалите Appium 2.x если установлен
npm uninstall -g appium

# Установите Appium 1.x
npm install -g appium@1.22.3
```

### Проверка подключения устройства:

```bash
adb devices
```

### Запуск Appium Server:

```bash
appium
```

## Шаг 7: Запуск тестов через Android Studio

### Способ 1: Через конфигурацию TestNG

1. Run → Edit Configurations
2. Выберите конфигурацию "TestNG"
3. Нажмите "Run"

### Способ 2: Через Gradle

1. Откройте Gradle панель (View → Tool Windows → Gradle)
2. Разверните Tasks → verification
3. Дважды кликните на "test"

### Способ 3: Через терминал

```bash
./gradlew test
```

## Возможные проблемы и решения

### Проблема: "Gradle sync failed"

**Решение:**
1. File → Invalidate Caches and Restart
2. Проверьте версию Java (должна быть 11+)
3. File → Sync Project with Gradle Files

### Проблема: "Cannot resolve symbol"

**Решение:**
1. Build → Clean Project
2. Build → Rebuild Project
3. Проверьте, что все зависимости загружены

### Проблема: "Test execution failed"

**Решение:**
1. Убедитесь, что Appium Server запущен
2. Проверьте подключение устройства: `adb devices`
3. Проверьте версию Appium Server: `appium --version`

### Проблема: "NoSuchMethodError"

**Решение:**
1. Убедитесь, что используется Appium 1.x сервер
2. Проверьте совместимость версий в `build.gradle`

## Полезные настройки Android Studio

### Настройка отображения:

1. View → Tool Windows → Project (для отображения структуры проекта)
2. View → Tool Windows → Gradle (для управления Gradle задачами)
3. View → Tool Windows → Terminal (для командной строки)

### Настройка редактора:

1. File → Settings → Editor → Code Style → Java
2. Настройте отступы и форматирование

### Настройка отладки:

1. Поставьте breakpoint в тестовом коде
2. Run → Debug вместо Run

## Проверка работоспособности

После импорта выполните следующие команды для проверки:

```bash
# Очистка и сборка
./gradlew clean build

# Компиляция
./gradlew compileJava compileTestJava

# Запуск тестов (без выполнения)
./gradlew testClasses
```

Если все команды выполняются без ошибок, проект готов к использованию в Android Studio! 