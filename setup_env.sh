#!/usr/bin/env bash
set -e

# Цвета для вывода
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

function info() { echo -e "${GREEN}[INFO] $1${NC}"; }
function error() { echo -e "${RED}[ERROR] $1${NC}"; }

# 1. Проверка ОС
OS="$(uname -s)"
info "ОС: $OS"

# 2. Проверка Java
if ! command -v java &> /dev/null; then
  error "Java не установлена!"
  if [[ "$OS" == "Darwin" ]]; then
    info "Устанавливаю Java через brew..."
    brew install openjdk@17 || brew install openjdk
    info "Добавьте JAVA_HOME в .zshrc/.bashrc, если потребуется."
  else
    info "Скачайте и установите Java JDK вручную: https://adoptium.net/ или через пакетный менеджер."
    exit 1
  fi
else
  info "Java найдена: $(java -version 2>&1 | head -n 1)"
fi

# 3. Проверка Node.js и npm
if ! command -v node &> /dev/null; then
  error "Node.js не установлен!"
  if [[ "$OS" == "Darwin" ]]; then
    info "Устанавливаю Node.js через brew..."
    brew install node
  else
    info "Скачайте и установите Node.js: https://nodejs.org/"
    exit 1
  fi
else
  info "Node.js найден: $(node -v)"
fi
if ! command -v npm &> /dev/null; then
  error "npm не установлен!"
  exit 1
else
  info "npm найден: $(npm -v)"
fi

# 4. Проверка Appium
if ! command -v appium &> /dev/null; then
  info "Устанавливаю Appium глобально через npm..."
  npm install -g appium
else
  info "Appium найден: $(appium -v)"
fi

# 5. Проверка adb
if ! command -v adb &> /dev/null; then
  error "adb не найден! Установите Android Platform Tools."
  if [[ "$OS" == "Darwin" ]]; then
    brew install --cask android-platform-tools
  else
    info "Скачайте Platform Tools: https://developer.android.com/studio/releases/platform-tools"
    exit 1
  fi
else
  info "adb найден: $(adb version | head -n 1)"
fi

# 6. Проверка gradlew
if [[ ! -f "gradlew" ]]; then
  error "gradlew не найден! Проверьте структуру проекта."
  exit 1
fi
chmod +x gradlew
info "gradlew готов к использованию."

# 7. Проверка run_tests.sh
if [[ -f "run_tests.sh" ]]; then
  chmod +x run_tests.sh
  info "run_tests.sh готов к использованию."
fi

# 8. Проверка переменных среды для Android SDK
if [[ -z "$ANDROID_HOME" && -z "$ANDROID_SDK_ROOT" ]]; then
  error "ANDROID_HOME или ANDROID_SDK_ROOT не установлены!"
  info "Установите переменную среды ANDROID_HOME или ANDROID_SDK_ROOT и перезапустите терминал."
  if [[ "$OS" == "Darwin" ]]; then
    echo 'export ANDROID_HOME="$HOME/Library/Android/sdk"' >> ~/.zshrc
    echo 'export PATH="$PATH:$ANDROID_HOME/platform-tools"' >> ~/.zshrc
    info "Добавлено в ~/.zshrc (или добавьте вручную в .bashrc)"
  else
    info "Для Windows: добавьте путь к SDK в переменные среды. Пример: C:\\Users\\<user>\\AppData\\Local\\Android\\Sdk"
    exit 1
  fi
else
  info "ANDROID_HOME или ANDROID_SDK_ROOT установлены."
fi

# 9. Проверка устройства
adb devices
info "Если устройство не отображается как 'device', проверьте USB-отладку и драйверы."

# 10. Сборка проекта
info "Запускаю сборку проекта..."
./gradlew clean build --refresh-dependencies
info "Сборка завершена. Всё готово к запуску тестов!" 