
javac -sourcepath . -sourcepath .\CTC main.java -sourcepath .\TrackController
java main

del main.class

rem maybe compile a javadoc?
set /P GEN_JAVADOC="Do you want to compile the javadoc?(Y/N)"
if "%CTC_JAVADOC%" == "Y"(
  del doc/*
  javadoc -header "<h1>BitsPlease Documents</h1>" *.java
  cp *.html ../doc/
  cp stylesheet.css ../doc/
  cp package-list ../doc/
  cp script.js .../doc/
  del *.html
  del stylesheet.css
  del package-list
  del script.js
)
