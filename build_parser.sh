#!/bin/bash
#Genera parser y visitors y los compila
#Asume antlr4 esta en el PATH
java org.antlr.v4.Tool -visitor -o src/antlr -package eightBit.compile -no-listener grammar/EightBit.g4 $*

if [ $? -eq 0 ]; then
  javac -d lib -Xlint:deprecation src/antlr/grammar/*.java
  echo "*** ANTLR compilation success ***"
else
  echo "*** ANTLR compilation failed ***"
fi
