package com.niit.studentservice.service;

import com.niit.common.entity.Student;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();
    Optional<Student> getStudentById(Integer id);
    Student saveStudent(Student student);
    void deleteStudent(Integer id);
} 