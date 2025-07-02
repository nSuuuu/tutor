package com.niit.teacherservice.repository;

import com.niit.common.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 教师数据访问层
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
    /**
     * 根据用户ID查找教师
     */
    // Optional<Teacher> findByUserId(Long userId);
    
    /**
     * 根据专业查找教师
     */
    List<Teacher> findByMajor(String major);
    
    /**
     * 根据状态查找教师
     */
    List<Teacher> findByStatus(Integer status);
    
    /**
     * 根据地区查找教师
     */
    List<Teacher> findByRegionId(Long regionId);
    
    /**
     * 根据评分范围查找教师
     */
    @Query("SELECT t FROM Teacher t WHERE t.rating >= :minRating AND t.rating <= :maxRating")
    List<Teacher> findByRatingRange(@Param("minRating") Double minRating, @Param("maxRating") Double maxRating);
    
    /**
     * 查找评分最高的教师
     */
    @Query("SELECT t FROM Teacher t ORDER BY t.rating DESC")
    List<Teacher> findTopRatedTeachers();
    
    /**
     * 根据用户真实姓名模糊查找教师
     */
    @Query("SELECT t FROM Teacher t WHERE t.user.realName LIKE %:realName%")
    List<Teacher> findByUserRealNameContaining(@Param("realName") String realName);
} 