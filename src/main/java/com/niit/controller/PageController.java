package com.niit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // 登录页面
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 注册页面
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // 学生功能页面
    @GetMapping("/student/page")
    public String studentPage() {
        return "appointments_student";
    }

    // 教师功能页面
    @GetMapping("/teacher/page")
    public String teacherPage() {
        return "appointments_teacher";
    }

    // 课程功能页面
    @GetMapping("/course/page")
    public String coursePage() {
        return "course_list"; // 如有其它课程页面可调整
    }

    // 订单功能页面
    @GetMapping("/order/page")
    public String orderPage() {
        return "order_list"; // 如有其它订单页面可调整
    }

    // 聊天功能页面
    @GetMapping("/chat/page")
    public String chatPage() {
        return "chat_list";
    }

    // 通知功能页面
    @GetMapping("/notice/page")
    public String noticePage() {
        return "notice_list"; // 如有其它通知页面可调整
    }

    // 你可以继续为其它页面添加入口
} 