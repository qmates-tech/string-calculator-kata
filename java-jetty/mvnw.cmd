@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
set "PROPERTIES_FILE=%SCRIPT_DIR%.mvn\wrapper\maven-wrapper.properties"

if not exist "%PROPERTIES_FILE%" (
    echo Error: %PROPERTIES_FILE% not found >&2
    exit /b 1
)

for /f "tokens=1,* delims==" %%a in ('findstr "^distributionUrl=" "%PROPERTIES_FILE%"') do set "DISTRIBUTION_URL=%%b"

if "%DISTRIBUTION_URL%"=="" (
    echo Error: distributionUrl not found >&2
    exit /b 1
)

for /f "tokens=4 delims=-" %%v in ("%DISTRIBUTION_URL%") do set "MAVEN_VERSION=%%v"
set "MAVEN_VERSION=%MAVEN_VERSION:-bin.zip=%"

if "%MAVEN_USER_HOME%"=="" set "MAVEN_USER_HOME=%USERPROFILE%\.m2"
set "MAVEN_HOME=%MAVEN_USER_HOME%\wrapper\dists\apache-maven-%MAVEN_VERSION%"

if not exist "%MAVEN_HOME%" (
    echo Downloading Maven %MAVEN_VERSION%...
    mkdir "%MAVEN_HOME%\.." 2>nul
    set "TMP_ZIP=%TEMP%\maven.zip"
    powershell -Command "Invoke-WebRequest -Uri '%DISTRIBUTION_URL%' -OutFile '%TMP_ZIP%'"
    powershell -Command "Expand-Archive -Path '%TMP_ZIP%' -DestinationPath '%MAVEN_HOME%\..' -Force"
    del "%TMP_ZIP%"
    echo Maven %MAVEN_VERSION% installed
)

"%MAVEN_HOME%\bin\mvn.cmd" %*
