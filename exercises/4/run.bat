md bin

javac -d bin -cp "CommandLineJunit\*" src\*.java

java -cp bin MonkeySim %1
