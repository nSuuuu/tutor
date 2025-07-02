package com.niit.appointmentservice.controller;

import com.niit.common.entity.Appointment;
import com.niit.common.util.Result;
import com.niit.appointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/list")
    public Result<List<Appointment>> getAllAppointments() {
        return Result.success(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public Result<Appointment> getAppointmentById(@PathVariable Integer id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(Result::success).orElseGet(() -> Result.error("预约不存在"));
    }

    @PostMapping("/add")
    public Result<Appointment> addAppointment(@RequestBody Appointment appointment) {
        Appointment saved = appointmentService.saveAppointment(appointment);
        return Result.success("预约添加成功", saved);
    }

    @PutMapping("/{id}")
    public Result<Appointment> updateAppointment(@PathVariable Integer id, @RequestBody Appointment appointment) {
        appointment.setId(id);
        Appointment saved = appointmentService.saveAppointment(appointment);
        return Result.success("预约更新成功", saved);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteAppointment(@PathVariable Integer id) {
        appointmentService.deleteAppointment(id);
        return Result.success("预约删除成功");
    }

    @GetMapping("/student/{studentId}")
    public Result<List<Appointment>> getAppointmentsByStudentId(@PathVariable Integer studentId) {
        return Result.success(appointmentService.getAppointmentsByStudentId(studentId));
    }

    @GetMapping("/teacher/{teacherId}")
    public Result<List<Appointment>> getAppointmentsByTeacherId(@PathVariable Integer teacherId) {
        return Result.success(appointmentService.getAppointmentsByTeacherId(teacherId));
    }

    @GetMapping("/status/{status}")
    public Result<List<Appointment>> getAppointmentsByStatus(@PathVariable String status) {
        return Result.success(appointmentService.getAppointmentsByStatus(status));
    }

    @PutMapping("/{id}/confirm")
    public Result<Appointment> confirmAppointment(@PathVariable Integer id) {
        Appointment appointment = appointmentService.confirmAppointment(id);
        return Result.success("预约已确认", appointment);
    }

    @PutMapping("/{id}/cancel")
    public Result<Appointment> cancelAppointment(@PathVariable Integer id, @RequestParam String reason) {
        Appointment appointment = appointmentService.cancelAppointment(id, reason);
        return Result.success("预约已取消", appointment);
    }

    @GetMapping("/conflict")
    public Result<Boolean> checkTimeConflict(@RequestParam Integer teacherId,
                                             @RequestParam Integer studentId,
                                             @RequestParam String startTime,
                                             @RequestParam String endTime) {
        java.time.LocalDateTime start = java.time.LocalDateTime.parse(startTime);
        java.time.LocalDateTime end = java.time.LocalDateTime.parse(endTime);
        boolean conflict = appointmentService.hasTimeConflict(teacherId, studentId, start, end);
        return Result.success(conflict);
    }

    // 兼容无/api前缀的FeignClient调用
    @GetMapping("/appointment/teacher/{teacherId}")
    public Result<List<Appointment>> getAppointmentsByTeacherIdV2(@PathVariable Integer teacherId) {
        return Result.success(appointmentService.getAppointmentsByTeacherId(teacherId));
    }

    @PostMapping("/appointment/create")
    public Result<Appointment> createAppointment(@RequestBody Appointment appointment) {
        Appointment saved = appointmentService.saveAppointment(appointment);
        return Result.success(saved);
    }
} 