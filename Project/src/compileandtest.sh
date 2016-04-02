#/bin/bash

# Make the directories we're going to use for running the program
mkdir build
mkdir bin

# Copy all the dependencies into the build folder
cp CTC/* ./build
cp TrackController/* ./build
cp TrackModel/* ./build
cp TrainModel/* ./build
cp TrainController/* ./build

# Copy our "real" main.java last so it dosen't conflict with any other ones.
cp ./main.java ./build

# Actually compile the whole program
javac -sourcepath ./build main.java -d ./bin

# Run the program
java -classpath ./bin main

# Ask if the user wants to compile a javadoc, and do it if they do
read  -p "Do you want to compile the javadoc?(Y/N):" GEN_JAVADOC
if [[ $GEN_JAVADOC == "Y" || $GEN_JAVADOC == "y" ]]; then
  rm -rf ./doc
  javadoc -heder "<h1>BitsPlease Documetation</h1>" ./build/*.java
  cp *.html ../doc/
  cp stylesheet.css ../doc/
  cp package-list ../doc/
  cp script.js ../doc/
  del *html *.css *.js package-list
fi

#Delete everything, so we don't get any conflicts after the first compile
rm -rf ./bin
rm -rf ./build
