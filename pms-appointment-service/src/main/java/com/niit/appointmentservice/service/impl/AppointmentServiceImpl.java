package com.niit.appointmentservice.service.impl;

import com.niit.common.entity.Appointment;
import com.niit.appointmentservice.repository.AppointmentRepository;
import com.niit.appointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(Integer id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void deleteAppointment(Integer id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAppointmentsByStudentId(Integer studentId) {
        return appointmentRepository.findByStudentId(studentId);
    }

    @Override
    public List<Appointment> getAppointmentsByTeacherId(Integer teacherId) {
        return appointmentRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findAll().stream()
                .filter(a -> status.equalsIgnoreCase(a.getStatus()))
                .toList();
    }

    @Override
    public Appointment confirmAppointment(Integer appointmentId) {
        Optional<Appointment> opt = appointmentRepository.findById(appointmentId);
        if (opt.isPresent()) {
            Appointment appointment = opt.get();
            appointment.setStatus("CONFIRMED");
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("预约不存在");
    }

    @Override
    public Appointment cancelAppointment(Integer appointmentId, String reason) {
        Optional<Appointment> opt = appointmentRepository.findById(appointmentId);
        if (opt.isPresent()) {
            Appointment appointment = opt.get();
            appointment.setStatus("CANCELLED");
            appointment.setSubject(appointment.getSubject() + "（取消原因：" + reason + ")");
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("预约不存在");
    }

    @Override
    public boolean hasTimeConflict(Integer teacherId, Integer studentId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Appointment> teacherAppointments = appointmentRepository.findByTeacherId(teacherId);
        List<Appointment> studentAppointments = appointmentRepository.findByStudentId(studentId);
        return teacherAppointments.stream().anyMatch(a -> overlap(a, startTime, endTime)) ||
               studentAppointments.stream().anyMatch(a -> overlap(a, startTime, endTime));
    }

    private boolean overlap(Appointment a, LocalDateTime start, LocalDateTime end) {
        return (a.getStartTime().isBefore(end) && a.getEndTime().isAfter(start));
    }
} 