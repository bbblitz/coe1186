
rem dependencies
cp "..\TrackController\TrackController.java" .
cp "..\TrackController\PLCDecoder.java" .
cp "..\TrackController\LineController.java" .

javac TrackEditor.java
java TrackEditor

del *.class
del TrackController.java
del PLCDecoder.java
del LineController.java

pause
