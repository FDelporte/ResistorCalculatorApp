# Resistor calculator application for mobile phone and desktop

Based on
 * the Maven library [Resistor calculator](https://github.com/FDelporte/ResistorCalculator)
 * existing JavaFX project of my book ["Getting started with Java on Raspberry Pi"](https://webtechie.be/books/), 
 which contains a JavaFX application for desktop only: ["Example application: visualize the Raspberry Pi pins with JavaFX"](https://github.com/FDelporte/JavaOnRaspberryPi/tree/master/Chapter_02_Tools/javafx-resistors)
 * starter application "Gluon Mobile - Single View": Gluon Mobile Applications are Java application written in JavaFX. 
 These applications ensure that developers can create high performance, great looking, and cloud connected mobile apps from a single Java code base.

## Pre-requisites

Please checkout the prerequisites to run this application [here](https://github.com/gluonhq/client-maven-plugin#requirements).

## Instructions

> **Note**: The following are command line instructions. For IDE specific instructions please checkout
[IDE documentation](https://docs.gluonhq.com/client/#_ide) of the client plugin.

These application can run on the JVM on desktop platforms. To run the application, execute the following command:

```
mvn javafx:run
```

The same application can also run natively for on any targeted OS, including Android, iOS, Linux, Mac and Windows.

To create a native image, execute the following command:

```
mvn client:build client:run
```

> **Note**: The above client commands are target-platform dependent and might change depending on the platform.
For more details, please check
[Client Maven Goals](https://github.com/gluonhq/client-maven-plugin#2-goals).

## Configuration

To configure the client plugin, please checkout the [Configuration documentation](https://docs.gluonhq.com/client/#_configuration).

## More information

Here are some helpful links:

* [Gluon Client documentation](https://docs.gluonhq.com/client)
* [Gluon Mobile documentation](https://docs.gluonhq.com/mobile)
* [Client Maven Plugin](https://github.com/gluonhq/client-maven-plugin)
* [Client Gradle Plugin](https://github.com/gluonhq/client-gradle-plugin)
