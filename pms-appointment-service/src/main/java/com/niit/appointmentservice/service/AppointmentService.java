package com.niit.appointmentservice.service;

import com.niit.common.entity.Appointment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> getAllAppointments();
    Optional<Appointment> getAppointmentById(Integer id);
    Appointment saveAppointment(Appointment appointment);
    void deleteAppointment(Integer id);
    List<Appointment> getAppointmentsByStudentId(Integer studentId);
    List<Appointment> getAppointmentsByTeacherId(Integer teacherId);
    List<Appointment> getAppointmentsByStatus(String status);
    Appointment confirmAppointment(Integer appointmentId);
    Appointment cancelAppointment(Integer appointmentId, String reason);
    boolean hasTimeConflict(Integer teacherId, Integer studentId, LocalDateTime startTime, LocalDateTime endTime);
} 