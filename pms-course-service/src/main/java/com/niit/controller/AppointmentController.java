package com.niit.controller;

import com.niit.entity.Appointment;
import com.niit.entity.User;
import com.niit.entity.Order;
import com.niit.entity.Course;
import com.niit.repository.AppointmentRepository;
import com.niit.repository.UserRepository;
import com.niit.repository.OrderRepository;
import com.niit.repository.CourseRepository;
import com.niit.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/student")
    public String studentAppointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != 2) {
            return "redirect:/login";
        }
        List<Appointment> appointments = appointmentRepository.findByStudentId(user.getId());
        Map<Integer, User> teacherMap = userRepository.findAllById(
                appointments.stream().map(Appointment::getTeacherId).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(User::getId, t -> t));
        Map<Integer, Boolean> paidOrderMap = new HashMap<>();
        for (Appointment appt : appointments) {
            List<Course> courses = courseRepository.findByStudentId(appt.getStudentId());
            Course course = null;
            for (Course c : courses) {
                if (c.getTeacher().getId().equals(appt.getTeacherId()) &&
                        c.getSubject().equals(appt.getSubject()) &&
                        c.getStartTime().equals(appt.getStartTime())) {
                    course = c;
                    break;
                }
            }
            boolean paid = false;
            if (course != null) {
                List<Order> orders = orderRepository.findByCourseId(course.getId());
                paid = orders.stream().anyMatch(o -> o.getStatus() == Order.OrderStatus.已支付);
            }
            paidOrderMap.put(appt.getId(), paid);
        }
        model.addAttribute("appointments", appointments);
        model.addAttribute("teacherMap", teacherMap);
        model.addAttribute("paidOrderMap", paidOrderMap);
        return "appointments_student";
    }

    @GetMapping("/teacher")
    public String teacherAppointments(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        List<Appointment> appointments = appointmentRepository.findByTeacherId(user.getId());
        Map<Integer, User> studentMap = userRepository.findAllById(
                appointments.stream().map(Appointment::getStudentId).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(User::getId, s -> s));
        Map<Integer, Boolean> paidOrderMap = new HashMap<>();
        for (Appointment appt : appointments) {
            List<Course> courses = courseRepository.findByStudentId(appt.getStudentId());
            Course course = null;
            for (Course c : courses) {
                if (c.getTeacher().getId().equals(appt.getTeacherId()) &&
                    c.getSubject().equals(appt.getSubject()) &&
                    c.getStartTime().equals(appt.getStartTime())) {
                    course = c;
                    break;
                }
            }
            boolean paid = false;
            if (course != null) {
                List<Order> orders = orderRepository.findByCourseId(course.getId());
                paid = orders.stream().anyMatch(o -> o.getStatus() == Order.OrderStatus.已支付);
            }
            paidOrderMap.put(appt.getId(), paid);
        }
        model.addAttribute("appointments", appointments);
        model.addAttribute("studentMap", studentMap);
        model.addAttribute("paidOrderMap", paidOrderMap);
        return "appointments_teacher";
    }

    @PostMapping("/book")
    public String book(@RequestParam Integer teacherId,
                       @RequestParam String subject,
                       @RequestParam String startTime,
                       @RequestParam String endTime,
                       HttpSession session) {
        User student = (User) session.getAttribute("user");
        if (student == null) return "redirect:/login";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        appointmentService.createAppointment(
                student.getId(),
                teacherId,
                subject,
                LocalDateTime.parse(startTime, formatter),
                LocalDateTime.parse(endTime, formatter)
        );
        return "redirect:/appointments/student";
    }

    @PostMapping("/cancel")
    public String cancelAppointment(@RequestParam Integer appointmentId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Appointment appt = appointmentRepository.findById(appointmentId).orElse(null);
        if (appt != null) {
            if (appt.getStudentId().equals(user.getId())) {
                appt.setStatus("CANCELLED");
                appointmentRepository.save(appt);
                return "redirect:/appointments/student";
            }
            if (appt.getTeacherId().equals(user.getId())) {
                appt.setStatus("CANCELLED");
                appointmentRepository.save(appt);
                return "redirect:/appointments/teacher";
            }
        }
        return "redirect:/appointments/student";
    }

    @PostMapping("/accept")
    public String acceptAppointment(@RequestParam Integer appointmentId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Appointment appt = appointmentRepository.findById(appointmentId).orElse(null);
        if (appt != null && appt.getTeacherId().equals(user.getId()) && "PENDING".equals(appt.getStatus())) {
            appt.setStatus("CONFIRMED");
            appointmentRepository.save(appt);
        }
        return "redirect:/appointments/teacher";
    }
} 