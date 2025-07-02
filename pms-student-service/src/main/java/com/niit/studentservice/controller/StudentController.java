package com.niit.studentservice.controller;

import com.niit.common.entity.Student;
import com.niit.common.util.Result;
import com.niit.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    public Result<List<Student>> getAllStudents() {
        return Result.success(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public Result<Student> getStudentById(@PathVariable Integer id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(Result::success).orElseGet(() -> Result.error("学生不存在"));
    }

    @PostMapping("/add")
    public Result<Student> addStudent(@RequestBody Student student) {
        return Result.success("学生添加成功", studentService.saveStudent(student));
    }

    @PutMapping("/{id}")
    public Result<Student> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        student.setId(id);
        return Result.success("学生更新成功", studentService.saveStudent(student));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return Result.success("学生删除成功");
    }
} 