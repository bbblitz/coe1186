::remove any old files that were compiled before
rmdir /S bin

::And re-create the bin directory
mkdir bin

::Then compile everything, starting with main
javac -s ./src -d ./bin main.java
