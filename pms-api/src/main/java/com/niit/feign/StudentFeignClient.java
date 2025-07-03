package com.niit.feign;

import com.niit.entity.Student;
import com.niit.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pms-student-service")
public interface StudentFeignClient {
    
    @GetMapping("/student/list")
    Result<List<Student>> getAllStudents();
    
    @GetMapping("/student/{id}")
    Result<Student> getStudentById(@PathVariable("id") Integer id);
    
    @GetMapping("/student/user/{userId}")
    Result<Student> getStudentByUserId(@PathVariable("userId") Integer userId);
    
    @GetMapping("/student/number/{studentNumber}")
    Result<Student> getStudentByNumber(@PathVariable("studentNumber") String studentNumber);
    
    @GetMapping("/student/grade/{grade}")
    Result<List<Student>> getStudentsByGrade(@PathVariable("grade") String grade);
    
    @PostMapping("/student/create")
    Result<Student> createStudent(@RequestBody Student student);
    
    @PutMapping("/student/update")
    Result<Student> updateStudent(@RequestBody Student student);
    
    @DeleteMapping("/student/{id}")
    Result<String> deleteStudent(@PathVariable("id") Integer id);
} 