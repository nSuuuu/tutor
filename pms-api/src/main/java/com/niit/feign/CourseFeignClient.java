package com.niit.feign;

import com.niit.entity.Course;
import com.niit.entity.Appointment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pms-course-service")
@RequestMapping("/course")
public interface CourseFeignClient {

    @GetMapping("/{id}")
    Course getCourseById(@PathVariable("id") Integer id);

    @GetMapping("/user/{userId}")
    List<Course> getCoursesByUserId(@PathVariable("userId") Integer userId);

    @GetMapping("/teacher/{teacherId}")
    List<Course> getCoursesByTeacherId(@PathVariable("teacherId") Integer teacherId);

    @GetMapping("/student/{studentId}")
    List<Course> getCoursesByStudentId(@PathVariable("studentId") Integer studentId);

    @PostMapping("/create")
    Course createCourse(@RequestBody Course course);

    @PutMapping("/update")
    Course updateCourse(@RequestBody Course course);

    @DeleteMapping("/{id}")
    void deleteCourse(@PathVariable("id") Integer id);

    // 预约相关
    @GetMapping("/appointment/{id}")
    Appointment getAppointmentById(@PathVariable("id") Integer id);

    @GetMapping("/appointment/teacher/{teacherId}")
    List<Appointment> getAppointmentsByTeacherId(@PathVariable("teacherId") Integer teacherId);

    @GetMapping("/appointment/student/{studentId}")
    List<Appointment> getAppointmentsByStudentId(@PathVariable("studentId") Integer studentId);

    @PostMapping("/appointment/create")
    Appointment createAppointment(@RequestBody Appointment appointment);

    @PutMapping("/appointment/update")
    Appointment updateAppointment(@RequestBody Appointment appointment);

    @DeleteMapping("/appointment/{id}")
    void deleteAppointment(@PathVariable("id") Integer id);
} 