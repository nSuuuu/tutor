package com.niit.controller;

import com.niit.dto.TeacherInfoDTO;
import com.niit.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SelectController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/select")
    public String home(
            @RequestParam(value = "gradeLevel", required = false) String gradeLevel,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "minPrice", required = false) Integer minPrice,
            @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
            Model model) {

        System.out.println("gradeLevel=" + gradeLevel + ", subject=" + subject +
                ", province=" + province + ", city=" + city +
                ", minPrice=" + minPrice + ", maxPrice=" + maxPrice);

        List<TeacherInfoDTO> teachers = teacherService.getTeachersWithFilters(
                gradeLevel, subject, province, city, minPrice, maxPrice);

        System.out.println("查询到老师数量: " + (teachers != null ? teachers.size() : 0));

        if (teachers == null) {
            teachers = new ArrayList<>();
        }

        model.addAttribute("teachers", teachers);
        model.addAttribute("gradeLevel", gradeLevel);
        model.addAttribute("subject", subject);
        model.addAttribute("province", province);
        model.addAttribute("city", city);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "select"; // 确保这个和你的模板名一致
    }



} 