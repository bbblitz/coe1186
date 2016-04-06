@echo off

rem Make the directories we're going to use for running the program
mkdir build
mkdir bin

rem Copy all the dependencies into the build folder
copy CTC/* .\build
copy TrackController/* .\build
copy TrackModel/* .\build
copy TrainModel/* .\build
copy TrainController/* .\build

rem Copy our "real" main.java last so it dosen't conflict with any other ones.
copy .\main.java .\build

rem Actually compile the whole program
javac -sourcepath .\build main.java -d .\bin

rem Run the program
java -classpath bin main

rem Ask if the user wants to compile a javadoc, and do it if they do
set /P GEN_JAVADOC="Do you want to compile the javadoc?(Y/N)"
if /i "%GEN_JAVADOC%" == "Y" goto :GENDOC
goto :END

rem Generate the javadoc if the user wanted to
:GENDOC
  del .\doc
  javadoc -header "<h1>BitsPlease Documents</h1>" .\build\*.java
  copy *.html ..\doc\
  copy stylesheet.css ..\doc\
  copy package-list ..\doc\
  copy script.js ...\doc\
  del *.html
  del stylesheet.css
  del package-list
  del script.js

rem Where to skip to if the user didn't want to generate the javadoc
:END
  rem Delete everything, so we don't get any conflicts after the first compile
  del /Q .\bin
  del /Q .\build
