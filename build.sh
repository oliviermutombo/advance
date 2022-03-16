#!/bin/bash

if mvn clean install test -Dspring.profiles.active=test ; then
   echo Project succssfully built! You may now execute run.sh [your name - optional]
else
	info=$'Something went wrong!\nPossible reasons:\n- Make sure maven is installed globally. For more info, visit https://maven.apache.org/install.html \n- Make sure JAVA_HOME variable is set!'
	echo "$info"
   exit 1
fi