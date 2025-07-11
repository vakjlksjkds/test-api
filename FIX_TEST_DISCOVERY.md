# Исправление проблемы "No tests found in the package"

## Проблема
Android Studio не может найти тесты в пакете "com.example.tests" с ошибкой:
```
No tests found in the package "com.example.tests"
```

## Решение

### Шаг 1: Перезапуск Android Studio
1. Закройте Android Studio
2. Откройте проект заново
3. Дождитесь полной синхронизации Gradle

### Шаг 2: Проверка конфигурации модуля
Убедитесь, что файл `AppiumTestProject.iml` содержит правильные настройки:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<module org.jetbrains.idea.devkit.projectRoots.IntelliJPluginModuleType type="PLUGIN_MODULE" version="4">
  <component name="NewModuleRootManager" LANGUAGE_LEVEL="JDK_21">
    <content url="file://$MODULE_DIR$">
      <sourceFolder url="file://$MODULE_DIR$/src/main/java" isTestSource="false" />
      <sourceFolder url="file://$MODULE_DIR$/src/main/resources" type="java-resource" />
      <sourceFolder url="file://$MODULE_DIR$/src/test/java" isTestSource="true" />
      <sourceFolder url="file://$MODULE_DIR$/src/test/resources" type="java-test-resource" />
      <excludeFolder url="file://$MODULE_DIR$/.gradle" />
      <excludeFolder url="file://$MODULE_DIR$/build" />
      <excludeFolder url="file://$MODULE_DIR$/logs" />
    </content>
    <orderEntry type="inheritedJdk" />
    <orderEntry type="sourceFolder" forTests="false" />
    <orderEntry type="sourceFolder" forTests="true" />
  </component>
</module>
```

### Шаг 3: Очистка и пересборка
Выполните в терминале:
```bash
./gradlew clean
./gradlew compileTestJava
```

### Шаг 4: Проверка тестовых классов
Убедитесь, что тестовые классы имеют правильные аннотации:

```java
package com.example.tests;

import org.testng.annotations.Test;
import org.testng.Assert;

public class SimpleTest {
    
    @Test
    public void simpleTest() {
        System.out.println("Simple test is running!");
        Assert.assertTrue(true, "Simple test should pass");
    }
}
```

### Шаг 5: Использование готовых конфигураций
В проекте созданы готовые конфигурации запуска:

1. **SimpleTest** - простой тест для проверки
2. **LoginTest** - тест входа в приложение
3. **TestNG** - общая конфигурация для всех тестов

### Шаг 6: Запуск через конфигурации
1. Откройте "Run/Debug Configurations" (Run → Edit Configurations)
2. Выберите конфигурацию "SimpleTest"
3. Нажмите "Run"

### Шаг 7: Альтернативные способы запуска

#### Способ 1: Через Gradle
```bash
./gradlew test
```

#### Способ 2: Через TestNG XML
```bash
./gradlew test --tests "TestNG"
```

#### Способ 3: Правый клик на тестовом классе
1. Откройте любой тестовый класс
2. Правый клик на имени класса
3. Выберите "Run 'ClassName'"

### Шаг 8: Проверка структуры проекта
Убедитесь, что структура проекта правильная:

```
src/
├── main/java/com/example/
│   └── Main.java
└── test/java/com/example/tests/
    ├── SimpleTest.java
    ├── LoginTest.java
    ├── ChromeOpenTest.java
    └── AnyAppOpenTest.java
```

### Шаг 9: Проверка зависимостей
Убедитесь, что в `build.gradle` есть все необходимые зависимости:

```gradle
dependencies {
    testImplementation 'org.testng:testng:7.8.0'
    // другие зависимости...
}
```

### Шаг 10: Инвалидация кэша
Если ничего не помогает:
1. File → Invalidate Caches and Restart
2. Выберите "Invalidate and Restart"

## Проверка работоспособности

После выполнения всех шагов:

1. Откройте `src/test/java/com/example/tests/SimpleTest.java`
2. Нажмите зеленую кнопку "Run" рядом с классом
3. Должен запуститься тест с выводом:
   ```
   Simple test is running!
   Another simple test is running!
   ```

## Если проблема остается

1. Проверьте версию Java (должна быть 11+)
2. Убедитесь, что TestNG плагин установлен в Android Studio
3. Проверьте, что проект синхронизирован с Gradle
4. Попробуйте создать новый тестовый класс с минимальным кодом

## Полезные команды для диагностики

```bash
# Проверка компиляции тестов
./gradlew compileTestJava

# Проверка структуры скомпилированных классов
find build -name "*.class" | grep -E "(Test|Login)"

# Запуск тестов через Gradle
./gradlew test --info
``` 