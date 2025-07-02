package com.niit.courseservice.controller;

import com.niit.common.entity.Course;
import com.niit.common.util.Result;
import com.niit.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    // 获取所有课程
    @GetMapping("/list")
    public Result<List<Course>> getAllCourses() {
        return Result.success(courseService.getAllCourses());
    }

    // 根据ID获取课程
    @GetMapping("/{id}")
    public Result<Course> getCourseById(@PathVariable Integer id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            return Result.success(course.get());
        } else {
            return Result.error("课程不存在");
        }
    }

    // 添加课程
    @PostMapping("/add")
    public Result<Course> addCourse(@RequestBody Course course) {
        Course savedCourse = courseService.saveCourse(course);
        return Result.success("课程添加成功", savedCourse);
    }

    // 更新课程
    @PutMapping("/{id}")
    public Result<Course> updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        course.setId(id);
        Course savedCourse = courseService.saveCourse(course);
        return Result.success("课程更新成功", savedCourse);
    }

    // 删除课程
    @DeleteMapping("/{id}")
    public Result<?> deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return Result.success("课程删除成功");
    }

    // 根据老师ID获取课程
    @GetMapping("/teacher/{teacherId}")
    public Result<List<Course>> getCoursesByTeacherId(@PathVariable Integer teacherId) {
        List<Course> courses = courseService.getCoursesByTeacherId(teacherId);
        return Result.success(courses);
    }

    // 根据学生ID获取课程
    @GetMapping("/student/{studentId}")
    public Result<List<Course>> getCoursesByStudentId(@PathVariable Integer studentId) {
        List<Course> courses = courseService.getCoursesByStudentId(studentId);
        return Result.success(courses);
    }

    // 根据课程状态获取课程
    @GetMapping("/status/{status}")
    public Result<List<Course>> getCoursesByStatus(@PathVariable Course.CourseStatus status) {
        List<Course> courses = courseService.getCoursesByStatus(status);
        return Result.success(courses);
    }

    // 根据课程类型获取课程
    @GetMapping("/type/{type}")
    public Result<List<Course>> getCoursesByType(@PathVariable Course.CourseType type) {
        List<Course> courses = courseService.getCoursesByType(type);
        return Result.success(courses);
    }

    // 更新课程状态
    @PutMapping("/{id}/status")
    public Result<Course> updateCourseStatus(@PathVariable Integer id, @RequestParam Course.CourseStatus status) {
        Optional<Course> courseOpt = courseService.getCourseById(id);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setStatus(status);
            Course savedCourse = courseService.saveCourse(course);
            return Result.success("课程状态更新成功", savedCourse);
        } else {
            return Result.error("课程不存在");
        }
    }
} 