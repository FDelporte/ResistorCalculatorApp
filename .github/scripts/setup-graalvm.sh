#!/bin/sh
set -e
echo "Downloading"
sh -c "curl -sL https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.2.0/graalvm-ce-java11-linux-amd64-20.2.0.tar.gz --output graalvm.tar.gz"

echo "Extracting"
sh -c "mkdir graalvm"
sh -c "tar -xf graalvm.tar.gz -C graalvm --strip-components=1"

echo "Exporting environment variables."
sh -c "export GRAALVM_HOME=graalvm"
sh -c "export JAVA_HOME=$GRAALVM_HOME"
sh -c "export PATH=$PATH:$GRAALVM_HOME/bin"

echo "GRAALVM_HOME=$GRAALVM_HOME"
echo "JAVA_HOME=$JAVA_HOME"
echo "PATH=$PATH"

echo "JAVA version"
sh -c "java -version"

echo "MAVEN version"
sh -c "mvn -version"