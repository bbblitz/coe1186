@echo off
rem Dependencies:
rem cp "..\Track"
rem cp "..\TrackController\TrackController.java" .
rem cp "..\TrackController\PLCDecoder.java" .
rem cp "..\TrackController\LineController.java" .

rem cp "..\Track Controller\TrackInterface.java" .
rem cp "..\Track Controller\Track.java" .
rem cp "..\Track Controller\Switch.java" .
rem cp "..\Track Controller\Crossing.java" .

copy dummy\* .

rem Compile and run the thing:
javac Config.java
javac main.java
java main
del *.class
del LineController.java

rem Compile the javadoc
set /P CTC_JAVADOC="Do you want to compile the javadoc?(Y/N)"
if "%CTC_JAVADOC%" == "Y"(
  del doc/*
  javadoc -header "<h1>BitsPlease CTC Documents</h1>" *.java
  cp *.html doc/
  cp stylesheet.css doc/
  cp package-list doc/
  cp script.js doc/
  del *.html
  del stylesheet.css
  del package-list
  del script.js
)


rem Remove dependencies:
rem del TrackInterface.java
rem del Track.java
rem del Switch.java
rem del Crossing.java
pause
@echo on
