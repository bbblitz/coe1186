@echo off
cp ../../src/TrainDummy.java .
javac tester.java
@echo on
java tester
@echo off
del TrainDummy.java
del TrainDummy.class
del tester.class
@echo on
pause
