#!/bin/sh
set -e
echo "--- Downloading --- "
sh -c "curl -sL https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.2.0/graalvm-ce-java11-linux-amd64-20.2.0.tar.gz --output graalvm.tar.gz"

echo "--- Extracting ---"
sh -c "mkdir -p ~/graalvm"
sh -c "tar -xf graalvm.tar.gz -C ~/graalvm --strip-components=1"
sh -c "rm graalvm.tar.gz"

echo "--- Setting environment variables ---"
export GRAALVM_HOME=~/graalvm
export JAVA_HOME=~/graalvm
export PATH=~/graalvm/bin:$PATH

echo "--- Checking environment variables ---"
echo "GRAALVM_HOME=$GRAALVM_HOME"
echo "JAVA_HOME=$JAVA_HOME"
echo "PATH=$PATH"

echo "--- GRAALVM ---"
echo "GRAALVM version"
sh -c "~/graalvm/bin/java -version"

echo "--- JAVA ---"
echo "JAVA version"
sh -c "java -version"

echo "--- MAVEN ---"
echo "MAVEN version"
sh -c "mvn -version"