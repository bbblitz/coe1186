@echo off

rem Make the directories we're going to use for runing the program
mkdir build
mkdir bin

rem Copy all the dependencies into the build folder
cp CTC/* .\build
cp TrackController/* .\build
cp TrackModel/* .\build
cp TrainModel/* .\build
cp TrainController/* .\build

rem Copy our "real" main.java last so it dosen't conflict with any other ones.
cp .\main.java .\build

rem Actually compile the whole program
javac -sourcepath .\build main.java -d .\bin

rem Run the program
java -classpath bin main

rem Ask if the user wants to compile a javadoc, and do it if they do
set /P GEN_JAVADOC="Do you want to compile the javadoc?(Y/N)"

if /i "%GEN_JAVADOC%" == "Y" goto :GENDOC
goto :END


:GENDOC
  del .\doc
  javadoc -header "<h1>BitsPlease Documents</h1>" .\build\*.java
  cp *.html ..\doc\
  cp stylesheet.css ..\doc\
  cp package-list ..\doc\
  cp script.js ...\doc\
  del *.html
  del stylesheet.css
  del package-list
  del script.js

:END
  rem Delete everything, so we don't get any conflicts after the first compile
  del /Q .\bin
  del /Q .\build
