# FIVE CARD DRAW
[![N|Solid](https://www.advance.io/wp-content/uploads/2016/09/advance.png)](https://www.advance.io/)   
By Olivier Mutombo  
[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

This variant of the five card game (fcd) has been implemented as part of an assessment by advance and provides the following functionality:

- Simulate shuffling a standard deck of 52 cards
- Deal a single hand of 5 cards to the player
- Evaluate the player's hand, informing them of the highest ranked poker hand that matches their hand of 5 cards

## Notes

- This is a single-user game designed to allow extensibility to a server side application in future versions
- Few of the algorithms made use of external resources but they've been adjusted to fit advance.io requirements

## Tech

This console based java application has been built using springboot by implementing the CommandLineRunner interface. The choice of this framework is discussed under **Discussion Points** at the end of this page.


## Folder structure

**src/  
├─ main/  
│  ├─ java/  
│  │  ├─ com.advance.io.fcd/  
│  │  │  ├─ enums/  
│  │  │  │  ├─ HandStrength.java  
│  │  │  ├─ exceptions/  
│  │  │  │  ├─ OutOfCardsException.java  
│  │  │  ├─ models/  
│  │  │  │  ├─ Card.java  
│  │  │  │  ├─ Deck.java  
│  │  │  │  ├─ RoundResult.java  
│  │  │  ├─ services/  
│  │  │  │  ├─ IDealerService.java  
│  │  │  │  ├─ IDeckService.java  
│  │  │  │  ├─ impl/  
│  │  │  │  │  ├─ DealerService.java  
│  │  │  │  │  ├─ DeckService.java  
│  │  │  │  │  ├─ GameService.java  
│  │  │  │  │  ├─ PokerService.java  
│  │  │  ├─ utils/  
│  │  │  │  ├─ ConsoleUtil.java  
│  │  │  ├─ FcdApplication.java  
│  ├─ resources/  
│  │  ├─ application.properties  
├─ test/  
│  ├─ resources/  
│  │  ├─ application-test.properties  
│  ├─ java/  
│  │  ├─ com.advance.io.fcd/  
│  │  │  ├─ FcdApplicationTests.java  
│  │  │  ├─ services.impl/  
│  │  │  │  ├─ DealerServiceTest.java  
│  │  │  │  ├─ DeckServiceTest.java  
│  │  │  │  ├─ GameServiceTest.java  
│  │  │  │  ├─ PokerServiceTest.java**
## Pre-requisites
- Install java JDK (I used Java 11)
- Should you go for a version below, you may need to set-up your JRE as well! However I would recommend Java 11 or higher
- Install maven from https://maven.apache.org/install.html
- Internet connection (Make sure firewall rules allow you to download dependencies)

## Installation
In order to play this game you will first need to run the `build.sh` script.

```sh
> cd [root folder]
> chmod +x *sh
> ./build.sh
```
The above script will download all the necessary dependencies and run tests as per screenshots below
You will notice that I also have created an `application-test.properties` This is to ensure that tests have their own configurations and prevent the test from getting stuck expecting user input since this is a console application.

![N|Solid](https://raw.githubusercontent.com/oliviermutombo/advance/master/screenshots/build1.png)  
...
![N|Solid](https://github.com/oliviermutombo/advance/blob/master/screenshots/build2.png?raw=true)

## Gameplay

In order to play this game you will need to run the `run.sh` script (`run.sh` takes an optional argument as the player's name)

```sh
> cd [root folder]
> ./run.sh <optional player's name>
```
The above script will download all the necessary dependencies and run tests as per screenshots below

![N|Solid](https://github.com/oliviermutombo/advance/blob/master/screenshots/play1.png?raw=true)  
...
![N|Solid](https://github.com/oliviermutombo/advance/blob/master/screenshots/play2.png?raw=true)

You will notice the log level is set to `WARN`. This was to prevent springboot `INFO` log entries to crowd our playground. Should you not want the `WARN` entries either, simply set `logging.level.root` to `OFF`
## Notes

Should the card suit symbols not display on your terminal, try one of the following:
- Change your terminal's font to one that allows UTF-8
- Set the attribute **`poker.show-suit-symbol`** to **`false`** in your **application.properties** file.
  it should look like this:
    ```java
    poker.show-suit-symbol=false
    ```
  This will remplace the symbols with the first letter of the associated suit. e.g. **D** for Diamond, **C** for club, **H** for Heart and **S** for Spade
  However, since this demo executes from a jar file that is not associated to external resources, you will need to re-build the project everytime you make changes to this file. Under normal circumstances we would probably would have used a config server for this.

## Javadoc

I have also generated javadocs which should help you quickly understand how the application works. Please refer to the javadoc folder in the root directory and launch index.html

***

## Discussion Points
**- We would like to change the application from a console based application to a web based (HTML frontend with the backend in your chosen language) application.**
This is the main reason why I have chosen to build this console application with springboot and disabling the web server with the below line in the application.properties file:
```java
spring.main.web-application-type=NONE
```
The advantage of this is that it still allows to build an app as microservice by making use of all the other features of springboot such as inversion of control and more...
Should we need to turn this into backend, all we'll have to do will be toggling spring.main.web-application-type ON, implement controllers, set-up a new properties file for the web version and we're done. The services have already been implemented in such a way that this will be seemless.

**We would like to replace the shuffling algorithm with a more realistic shuffling algorithm.**
Inline with the previous point, all you have to do here is to provide another implemention of the DealerService which overrides the **shuffle** method and off you go!

**We would like to accommodate a different poker variant, such as Badugi, where not only the size of the hand is changed (4 cards instead of 5), but the hand ranks are also changed.**
The application.properties file provides an attribute called **`poker.hand-size`** which allows you to change the size of the hand on the fly! Should you need to implement a new variant, again all you will have to do is implement a new **`PokerService`**.

Thank you very much - should you need further imformation, do not hesitate to contact me.
I will keep this project public for a couple of weeks.

Olivier Mutombo
oliviermtb@gmail.com
