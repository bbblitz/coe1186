@echo off
rem Dependencies:
rem cp "..\Track Controller\TrackInterface.java" .
rem cp "..\Track Controller\Track.java" .
rem cp "..\Track Controller\Switch.java" .
rem cp "..\Track Controller\Crossing.java" .

rem Compile and run the thing:
javac main.java
java main
del main.class
del DummyLine.class
del DummyLineOne.class
del DummySection.class
del DummySwitch.class
del DummyTrackCurved.class
del DummyTrackStraight.class
del Infrastructure.class
del DummyTrackInterface.class
del TrackPane.class
del DetailPane.class
del TrackFailState.class
del TrackPane$1.class
del SchedulePane.class
del LineVisPanel.class
del TrainDetailPane.class

rem Remove dependencies:
rem del TrackInterface.java
rem del Track.java
rem del Switch.java
rem del Crossing.java
pause
@echo on
