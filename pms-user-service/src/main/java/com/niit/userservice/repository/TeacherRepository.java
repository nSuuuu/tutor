package com.niit.userservice.repository;


import com.niit.common.entity.Teacher;
import com.niit.common.dto.TeacherInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByUser_Id(Integer userId);

    @Query("SELECT new com.niit.common.dto.TeacherInfoDTO(" +
            "t.id, u.realName, u.avatar, t.experienceYears, " +
            "t.subject, t.education, t.introduction, t.rating, " +
            "t.major, t.hourlyRate, u.address, u.phone" +
            ") " +
            "FROM com.niit.common.entity.Teacher t " +
            "JOIN t.user u " +
            "WHERE (:gradeLevel IS NULL OR :gradeLevel = '' OR :gradeLevel = '全部' OR :gradeLevel = 'all' OR :gradeLevel = '全部年级' OR t.major = :gradeLevel) " +
            "AND (:subject IS NULL OR t.subject LIKE :subject) " +
            "AND (:province IS NULL OR u.address LIKE :province) " +
            "AND (:city IS NULL OR u.address LIKE :city) " +
            "AND (:minPrice IS NULL OR t.hourlyRate >= :minPrice) " +
            "AND (:maxPrice IS NULL OR t.hourlyRate <= :maxPrice) " +
            "ORDER BY t.rating DESC, t.id DESC")
    List<TeacherInfoDTO> findTeachersWithFilters(
            String gradeLevel,
            String subject,
            String province,
            String city,
            Integer minPrice,
            Integer maxPrice
    );
} 