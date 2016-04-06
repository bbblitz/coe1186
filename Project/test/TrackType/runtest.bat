@echo off
cp ../../src/TrackType.java .
javac tester.java
@echo on
java tester
@echo off
del TrackType.java
del TrackType.class
del tester.class
@echo on
pause
