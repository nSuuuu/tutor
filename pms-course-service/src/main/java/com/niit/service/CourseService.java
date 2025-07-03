package com.niit.service;

import com.niit.entity.Course;
import com.niit.entity.User;
import com.niit.entity.Course;
import com.niit.entity.User;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    /**
     * 获取所有课程
     */
    List<Course> getAllCourses();
    
    /**
     * 根据ID获取课程
     */
    Optional<Course> getCourseById(Integer id);
    
    /**
     * 保存课程
     */
    Course saveCourse(Course course);
    
    /**
     * 删除课程
     */
    void deleteCourse(Integer id);
    
    /**
     * 根据老师用户对象获取课表
     */
    List<Course> getCoursesByTeacher(User teacher);
    
    /**
     * 根据学生用户对象获取课表
     */

    List<Course> getCoursesByStudent(User student);

    /**
     * 根据老师ID获取课表
     */
    List<Course> getCoursesByTeacherId(Integer teacherId);
    
    /**
     * 根据学生ID获取课表
     */
    List<Course> getCoursesByStudentId(Integer studentId);
    
    /**
     * 根据课程状态获取课程列表
     */
    List<Course> getCoursesByStatus(Course.CourseStatus status);
    
    /**
     * 根据课程类型获取课程列表
     */
    List<Course> getCoursesByType(Course.CourseType type);

}