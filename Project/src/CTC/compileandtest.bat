@echo off
rem Dependencies:
rem cp "..\Track Controller\TrackInterface.java" .
rem cp "..\Track Controller\Track.java" .
rem cp "..\Track Controller\Switch.java" .
rem cp "..\Track Controller\Crossing.java" .

rem Compile and run the thing:
javac main.java
java main
del *.class

rem Compile the javadoc
rem javadoc -header "<h1>BitsPlease CTC Documents</h1>" *.java
rem cp *.html ./doc/
rem cp stylesheet.css ./doc/
rem cp package-list ./doc/
rem cp script.js ./doc/


rem Remove dependencies:
rem del TrackInterface.java
rem del Track.java
rem del Switch.java
rem del Crossing.java
pause
@echo on
