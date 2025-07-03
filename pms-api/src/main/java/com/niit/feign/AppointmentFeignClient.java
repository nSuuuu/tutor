package com.niit.feign;

import com.niit.entity.Appointment;
import com.niit.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pms-appointment-service")
public interface AppointmentFeignClient {
    
    @GetMapping("/appointment/list")
    Result<List<Appointment>> getAllAppointments();
    
    @GetMapping("/appointment/{id}")
    Result<Appointment> getAppointmentById(@PathVariable("id") Integer id);
    
    @GetMapping("/appointment/student/{studentId}")
    Result<List<Appointment>> getAppointmentsByStudentId(@PathVariable("studentId") Integer studentId);
    
    @GetMapping("/appointment/teacher/{teacherId}")
    Result<List<Appointment>> getAppointmentsByTeacherId(@PathVariable("teacherId") Integer teacherId);
    
    @GetMapping("/appointment/status/{status}")
    Result<List<Appointment>> getAppointmentsByStatus(@PathVariable("status") String status);
    
    @PostMapping("/appointment/create")
    Result<Appointment> createAppointment(@RequestBody Appointment appointment);
    
    @PutMapping("/appointment/update")
    Result<Appointment> updateAppointment(@RequestBody Appointment appointment);
    
    @PutMapping("/appointment/{id}/status/{status}")
    Result<Appointment> updateAppointmentStatus(@PathVariable("id") Integer id, @PathVariable("status") String status);
    
    @DeleteMapping("/appointment/{id}")
    Result<String> deleteAppointment(@PathVariable("id") Integer id);
} 