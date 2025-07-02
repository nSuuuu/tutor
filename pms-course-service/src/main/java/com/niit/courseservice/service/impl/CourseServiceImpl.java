package com.niit.courseservice.service.impl;

import com.niit.common.entity.Course;
import com.niit.common.entity.User;
import com.niit.courseservice.repository.CourseRepository;
import com.niit.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getCoursesByTeacher(User teacher) {
        return courseRepository.findByTeacher(teacher);
    }

    @Override
    public List<Course> getCoursesByStudent(User student) {
        return courseRepository.findByStudent(student);
    }

    @Override
    public List<Course> getCoursesByTeacherId(Integer teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<Course> getCoursesByStudentId(Integer studentId) {
        return courseRepository.findByStudentId(studentId);
    }

    @Override
    public List<Course> getCoursesByStatus(Course.CourseStatus status) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getStatus() == status)
                .toList();
    }

    @Override
    public List<Course> getCoursesByType(Course.CourseType type) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getType() == type)
                .toList();
    }
} 