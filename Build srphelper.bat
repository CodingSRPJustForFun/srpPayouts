@echo off
setlocal

cd /d "%~dp0"

if not defined JAVA_HOME (
    echo JAVA_HOME is not set. Install Eclipse Temurin 25 from Adoptium and set JAVA_HOME to its installation folder.
    pause
    exit /b 1
)

if not exist "%JAVA_HOME%\bin\javac.exe" (
    echo JAVA_HOME does not point to a JDK. Set it to the Eclipse Temurin 25 installation folder.
    pause
    exit /b 1
)

where mvn >nul 2>nul
if errorlevel 1 (
    echo Maven was not found. Install Apache Maven and ensure mvn is on PATH.
    echo JAVA_HOME must point to the Eclipse Temurin 25 JDK to build from source.
    pause
    exit /b 1
)

call mvn clean package
set RESULT=%ERRORLEVEL%

echo.

if "%RESULT%"=="0" (
    echo Build succeeded. You can now run "Run srphelper.bat".
) else (
    echo Build failed. Copy the complete error output when asking for help.
)

pause
exit /b %RESULT%