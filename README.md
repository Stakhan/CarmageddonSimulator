# Carmageddon Simulator

Carmageddon Simulator is an information technology project developped by first year engineering students of the École Nationale des Sciences Géographiques. 
This project simulates the behaviour of cars and pedestrians at an intersection and provides statistical data aimed at urban planners. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. **Those instructions assume you are on a debian-like linux system.**

### Prerequisites

Carmageddon Simulator is developped in Java. So you need a version of the JDK if you aim at modifying the code.
There is no need for other languages or platforms. It was tested with versions 8, 11 and 12 of the JDK.

To download the Java Development Kit (JDK) visit:
http://jdk.java.net/12/ or https://www.oracle.com/technetwork/java/javase/downloads/index.html


### Getting source code
If you haven't installed git yet, open a shell and run:
```
sudo apt-get install git
```
Then retrieve the code using:
```
git clone https://github.com/Stakhan/CarmageddonSimulator
```
You now have a local copy of the code and can modify it and run it using your favorite IDE (Eclipse, IntelliJ, Netbeans, ...)

## Compiling sources
Once you have a local copy of the code, you can compile it. For this purpose we will use Another Neat Tool (ANT). Install it using:
```
sudo apt-get install ant
```
Make sure you're in the CarmageddonSimulator:
```
pwd
```
If yes, compile using:
```
ant -f build.xml
```
You should now have a .jar file in the CarmageddonSimulator directory.

You can remove the directory created during build (which are of no use once the .jar has been generated) by running:
```
ant -f build.xml clean
```

## Launching the app !

Once you've produced a .jar file, it's time to run it:

```
java -jar CarmageddonSimulator.jar
```

A first window appear, choose your size preferences there. Then, you get the main window where you can watch the simulation and adjust the different live parameters and events.

Use <kbd>SPACE</kbd> to play and pause the simulation and <kbd>&rarr;</kbd> to advance in the simulation step by step.


## Authors

* **Arthur Dujardin**
* **Elie-Alban Lescout**
* **Samuel Mermet**

See also the list of [contributors](https://github.com/Stakhan/CarmageddonSimulator/contributors) who participated in this project.

## License

This project is licensed under the GNU GPL License
