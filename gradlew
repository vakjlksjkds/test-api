#!/bin/sh

#
# Copyright © 2015-2021 the original authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# SPDX-License-Identifier: Apache-2.0
#

##############################################################################
#
#   Gradle start up script for UN*X
#
##############################################################################

# Set default Gradle user home if not set
if [ -z "$GRADLE_USER_HOME" ]; then
  GRADLE_USER_HOME="$HOME/.gradle"
fi

# Find the wrapper jar
WRAPPER_JAR="$(dirname "$0")/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Could not find gradle-wrapper.jar. Please run 'gradle wrapper' first."
  exit 1
fi

# Execute the wrapper jar
exec java -Dorg.gradle.appname=$(basename "$0") -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
