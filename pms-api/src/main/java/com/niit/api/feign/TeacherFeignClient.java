package com.niit.api.feign;

import com.niit.common.entity.Teacher;
import com.niit.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pms-teacher-service")
public interface TeacherFeignClient {
    
    @GetMapping("/teacher/list")
    Result<List<Teacher>> getAllTeachers();
    
    @GetMapping("/teacher/{id}")
    Result<Teacher> getTeacherById(@PathVariable("id") Integer id);
    
    @GetMapping("/teacher/user/{userId}")
    Result<Teacher> getTeacherByUserId(@PathVariable("userId") Integer userId);
    
    @GetMapping("/teacher/number/{teacherNumber}")
    Result<Teacher> getTeacherByNumber(@PathVariable("teacherNumber") String teacherNumber);
    
    @GetMapping("/teacher/subject/{subject}")
    Result<List<Teacher>> getTeachersBySubject(@PathVariable("subject") String subject);
    
    @GetMapping("/teacher/experience/{minYears}")
    Result<List<Teacher>> getTeachersByExperience(@PathVariable("minYears") Integer minYears);
    
    @PostMapping("/teacher/create")
    Result<Teacher> createTeacher(@RequestBody Teacher teacher);
    
    @PutMapping("/teacher/update")
    Result<Teacher> updateTeacher(@RequestBody Teacher teacher);
    
    @DeleteMapping("/teacher/{id}")
    Result<String> deleteTeacher(@PathVariable("id") Integer id);

    @GetMapping("/teacher/search")
    Result<List<Teacher>> searchTeachers(
        @RequestParam(required = false) String gradeLevel,
        @RequestParam(required = false) String subject,
        @RequestParam(required = false) String province,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) Integer minPrice,
        @RequestParam(required = false) Integer maxPrice
    );
} 