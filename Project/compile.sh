#!/bin/sh

#remove any old files that were compiled before
rm -rf ./bin

#And re-create the bin directory
mkdir bin

#Then compile everything, starting with main
javac -s ./src -d ./bin main.java
