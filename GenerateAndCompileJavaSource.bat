java -jar lib\antlr.jar -package com.panickapps.ray.parser -no-listener -visitor src\com\panickapps\ray\parser\Ray.g4
pause
javac -cp lib\antlr.jar src\com\panickapps\ray\parser\*.java
pause