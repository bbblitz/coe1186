#/bin/bash


mkdir bin

# copy dependencies into ./bin
cp *.java ./bin
cp ../../TrainModel/*.java ./bin

cd ./bin

# compile
javac Main.java

# run
java Main

# clean up
cd ..
rm -rf ./bin