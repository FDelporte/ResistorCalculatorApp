echo "Exporting environment variables."
sh -c "setx -m PATH 'C:\graalvm\graalvm-ce-java11-20.2.0\bin;%PATH%'"
sh -c "setx -m JAVA_HOME 'C:\graalvm\graalvm-ce-java11-20.2.0\'"

echo %PATH%
echo %JAVA_HOME%

echo "--------------------------------------------------"
echo "JAVA version"
sh -c "java -version"

echo "--------------------------------------------------"
echo "MAVEN version"
sh -c "mvn -version"
