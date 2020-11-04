#!/bin/sh
set -e
echo "Downloading"
sh -c "curl -sL https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.2.0/graalvm-ce-java11-windows-amd64-20.2.0.zip --output graalvm.zip"

echo "Extracting"
sh -c "mkdir C:/graalvm"
sh -c "unzip graalvm.zip -d C:/graalvm"
