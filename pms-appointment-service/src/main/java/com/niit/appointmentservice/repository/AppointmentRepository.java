package com.niit.appointmentservice.repository;

import com.niit.common.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByStudentId(Integer studentId);
    List<Appointment> findByTeacherId(Integer teacherId);
} 