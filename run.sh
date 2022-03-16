#!/bin/bash

if [ ! -f ./target/*.jar ]; then
    echo "It seems you have have not built the project yet! Please start with ./build.sh"
	exit 1
fi

java -jar target/*.jar $1
