@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  runner startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and RUNNER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\runner-0.0.1.jar;%APP_HOME%\lib\log4j-1.2.12.jar;%APP_HOME%\lib\gson-2.8.1.jar;%APP_HOME%\lib\luaj-jse-3.0.1.jar;%APP_HOME%\lib\javafaker-0.15.jar;%APP_HOME%\lib\jedis-2.9.0.jar;%APP_HOME%\lib\commons-pool2-2.4.2.jar;%APP_HOME%\lib\rxjava-2.1.14-RC1.jar;%APP_HOME%\lib\commons-lang3-3.5.jar;%APP_HOME%\lib\snakeyaml-1.20-android.jar;%APP_HOME%\lib\generex-1.0.2.jar;%APP_HOME%\lib\reactive-streams-1.0.2.jar;%APP_HOME%\lib\automaton-1.11-8.jar

@rem Execute runner
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %RUNNER_OPTS%  -classpath "%CLASSPATH%" com.robolucha.runner.Server %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable RUNNER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%RUNNER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
