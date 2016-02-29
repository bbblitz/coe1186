@echo off
rem Dependencies:
rem cp "..\Track"
cp "..\TrackController\TrackController.java" .
cp "..\TrackController\PLCDecoder.java" .
cp "..\TrackController\LineController.java" .

rem cp "..\Track Controller\TrackInterface.java" .
rem cp "..\Track Controller\Track.java" .
rem cp "..\Track Controller\Switch.java" .
rem cp "..\Track Controller\Crossing.java" .

rem Compile and run the thing:
javac Config.java
javac main.java
java main
del *.class
rem del TrackController.java

rem Compile the javadoc
set /P CTC_JAVADOC="Do you want to compile the javadoc?(Y/N)"
if "%CTC_JAVADOC%" == "Y"(
  del ./doc/*
  javadoc -header "<h1>BitsPlease CTC Documents</h1>" *.java
  cp *.html ./doc/
  cp stylesheet.css ./doc/
  cp package-list ./doc/
  cp script.js ./doc/
)


rem Remove dependencies:
rem del TrackInterface.java
rem del Track.java
rem del Switch.java
rem del Crossing.java
pause
@echo on
