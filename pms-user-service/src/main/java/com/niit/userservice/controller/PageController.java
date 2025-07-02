package com.niit.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class PageController {

    @Autowired
    private com.niit.api.feign.AppointmentFeignClient appointmentFeignClient;

    @GetMapping("/orders/teacher")
    public String ordersTeacherPage(javax.servlet.http.HttpSession session) {
        Object userType = session.getAttribute("userType");
        if (userType != null && userType.toString().equals("teacher")) {
            return "appointments_teacher";
        } else {
            return "appointments_student";
        }
    }

    @GetMapping("/notices")
    public String noticesPage() {
        return "notices";
    }

    @GetMapping("/chat/list")
    public String chatListPage() {
        return "chat_list";
    }

    @GetMapping("/schedule_teacher")
    public String scheduleTeacherPage(javax.servlet.http.HttpSession session, Model model) {
        com.niit.common.entity.User user = (com.niit.common.entity.User) session.getAttribute("user");
        java.util.List<com.niit.common.entity.Appointment> appointments = new java.util.ArrayList<>();
        if (user != null && user.getRole() == 1) {
            try {
                appointments = appointmentFeignClient.getAppointmentsByTeacherId(user.getId()).getData();
                if (appointments == null) appointments = new java.util.ArrayList<>();
            } catch (Exception e) {
                // 远程调用异常，返回空列表
            }
        }
        model.addAttribute("appointments", appointments);
        return "schedule_teacher";
    }

    @GetMapping("/appointments/teacher")
    public String appointmentsTeacherPage() {
        return "appointments_teacher";
    }
} 