
rem dependencies
rem cp "..\TrackController\TrackController.java" .
rem cp "..\TrackController\PLCDecoder.java" .
rem cp "..\TrackController\LineController.java" .
copy dummy\LineController.java .

javac TrackEditor.java
java TrackEditor

del *.class
del TrackController.java
del PLCDecoder.java
del LineController.java

pause
