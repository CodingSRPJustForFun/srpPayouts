@echo off
setlocal
set "SRP_HOME=%~dp0"
cd /d "%SRP_HOME%"
if not exist "config.properties" copy /Y "config.properties.example" "config.properties" >nul

if not exist "target\srpPayouts-1.0-SNAPSHOT.jar" (
    echo The application has not been built yet.
    echo Please run Build SRP Payouts.bat first.
    echo.
    pause
    exit /b 1
)

if not defined JAVA_HOME (
    echo JAVA_HOME is not set. Install Eclipse Temurin 25 from Adoptium and set JAVA_HOME to its installation folder.
    pause
    exit /b 1
)

if not exist "%JAVA_HOME%\bin\java.exe" (
    echo JAVA_HOME does not contain java.exe. Set it to the Eclipse Temurin 25 installation folder.
    pause
    exit /b 1
)

if not exist "logs" mkdir "logs"

"%JAVA_HOME%\bin\java.exe" --enable-native-access=ALL-UNNAMED -Dsrp.home="%SRP_HOME:~0,-1%" -jar "target\srpPayouts-1.0-SNAPSHOT.jar" > "logs\latest-run.log" 2>&1

set RESULT=%ERRORLEVEL%

type "logs\latest-run.log"

echo.

if not "%RESULT%"=="0" echo The run reported an error. The log is saved at logs\latest-run.log.

pause
exit /b %RESULT%