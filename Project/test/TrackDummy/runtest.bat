@echo off
cp ../../src/TrackDummy.java .
javac tester.java
@echo on
java tester
@echo off
del TrackDummy.java
del TrackDummy.class
del tester.class
@echo on
pause
