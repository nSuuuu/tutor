@echo off
echo 停止PMS Spring Cloud微服务项目...

echo.
echo 正在停止所有Java进程...
taskkill /f /im java.exe

echo.
echo 正在停止所有Maven进程...
taskkill /f /im mvn.cmd

echo.
echo 所有服务已停止！
echo.
pause 