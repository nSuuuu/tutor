package com.niit.teacherservice.controller;

import com.niit.common.dto.TeacherInfoDTO;
import com.niit.teacherservice.service.TeacherService;
import com.niit.common.entity.Teacher;
import com.niit.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SelectController {
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/select")
    public String home(
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "regionId", required = false) Long regionId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            Model model) {

        System.out.println("subject=" + subject +
                ", regionId=" + regionId +
                ", minPrice=" + minPrice + ", maxPrice=" + maxPrice);

        Result<List<Teacher>> result = teacherService.getTeachersWithFilters(subject, regionId, minPrice, maxPrice);
        List<Teacher> teachers = (result.getCode() != null && result.getCode() == 200 && result.getData() != null) ? result.getData() : new ArrayList<>();

        // DTO转换，使用TeacherInfoDTO的全字段构造方法（如有字段缺失可补充）
        List<TeacherInfoDTO> teacherDTOs = teachers.stream().map(t ->
            new TeacherInfoDTO(
                t.getId(),
                t.getUser() != null ? t.getUser().getRealName() : null,
                t.getUser() != null ? t.getUser().getAvatar() : null,
                t.getExperienceYears() != null ? t.getExperienceYears() : 0,
                t.getSubject(),
                t.getEducation(),
                t.getIntroduction(),
                t.getRating() != null ? t.getRating() : 0.0,
                t.getMajor(),
                t.getHourlyRate() != null ? t.getHourlyRate() : 0.0,
                t.getUser() != null ? t.getUser().getAddress() : null,
                t.getUser() != null ? t.getUser().getPhone() : null
            )
        ).collect(Collectors.toList());

        model.addAttribute("teachers", teacherDTOs);
        model.addAttribute("subject", subject);
        model.addAttribute("regionId", regionId);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "select"; // 确保这个和你的模板名一致
    }

} 