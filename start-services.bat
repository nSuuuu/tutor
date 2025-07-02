@echo off
echo 启动PMS Spring Cloud微服务项目...

echo.
echo 1. 编译项目...
call mvn clean install -DskipTests

echo.
echo 2. 启动网关服务...
start "PMS Gateway" cmd /k "cd pms-gateway && mvn spring-boot:run"

echo.
echo 3. 等待网关启动完成...
timeout /t 10 /nobreak

echo.
echo 4. 启动用户服务...
start "PMS User Service" cmd /k "cd pms-user-service && mvn spring-boot:run"

echo.
echo 5. 等待用户服务启动完成...
timeout /t 10 /nobreak

echo.
echo 6. 启动课程服务...
start "PMS Course Service" cmd /k "cd pms-course-service && mvn spring-boot:run"

echo.
echo 7. 启动订单服务...
start "PMS Order Service" cmd /k "cd pms-order-service && mvn spring-boot:run"

echo.
echo 8. 启动预约服务...
start "PMS Appointment Service" cmd /k "cd pms-appointment-service && mvn spring-boot:run"

echo.
echo 9. 启动通知服务...
start "PMS Notice Service" cmd /k "cd pms-notice-service && mvn spring-boot:run"

echo.
echo 10. 启动学生服务...
start "PMS Student Service" cmd /k "cd pms-student-service && mvn spring-boot:run"

echo.
echo 11. 启动教师服务...
start "PMS Teacher Service" cmd /k "cd pms-teacher-service && mvn spring-boot:run"

echo.
echo 12. 启动聊天服务...
start "PMS Chat Service" cmd /k "cd pms-chat-service && mvn spring-boot:run"

echo.
echo 13. 启动地区服务...
start "PMS Region Service" cmd /k "cd pms-region-service && mvn spring-boot:run"

echo.
echo 所有服务启动完成！
echo.
echo 服务访问地址：
echo - 网关地址: http://localhost:8080
echo - 用户服务: http://localhost:8081
echo - 课程服务: http://localhost:8082
echo - 订单服务: http://localhost:8083
echo - 预约服务: http://localhost:8084
echo - 通知服务: http://localhost:8085
echo - 学生服务: http://localhost:8086
echo - 教师服务: http://localhost:8087
echo - 聊天服务: http://localhost:8088
echo - 地区服务: http://localhost:8089
echo - Nacos控制台: http://localhost:8848/nacos
echo.
pause 