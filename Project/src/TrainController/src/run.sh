#/bin/bash

mkdir bin

cp *.java ./bin

javac Main.java -d ./bin

java -classpath ./bin Main

rm -rf ./bin