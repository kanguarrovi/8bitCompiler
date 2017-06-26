@echo off
REM Genera parser y visitors y los compila
REM Asume antlr4 esta en el PATH

call antlr4 -visitor  -o src/antlr -package eightBit.compile -no-listener grammar/EightBit.g4 %*
if %ERRORLEVEL% == 1 (goto :error)
javac -d lib  -Xlint:deprecation src/antlr/*.java
goto :end
:error 
echo "*** ANTLR compilation failed ***"
:end

