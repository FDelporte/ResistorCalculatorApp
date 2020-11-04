#!/bin/sh
set -e
echo "Downloading"
sh -c "curl -sL https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.2.0/graalvm-ce-java11-windows-amd64-20.2.0.zip --output graalvm.zip"

echo "Extracting"
sh -c "mkdir C:/graalvm"
sh -c "unzip graalvm.zip -d C:/graalvm"

echo "Exporting environment variables."
sh -c "export GRAALVM_HOME=C:graalvm/graalvm-ce-java11-20.2.0/"
sh -c "export JAVA_HOME=$GRAALVM_HOME"
sh -c "export PATH=$PATH:$GRAALVM_HOME/bin"

echo "GRAALVM_HOME=$GRAALVM_HOME"
echo "JAVA_HOME=$JAVA_HOME"
echo "PATH=$PATH"

echo "--------------------------------------------------"
echo "JAVA version"
sh -c "java -version"

echo "--------------------------------------------------"
echo "MAVEN version"
sh -c "mvn -version"
