package com.niit.teacherservice.service;

import com.niit.common.entity.Teacher;
import com.niit.common.util.Result;

import java.util.List;

/**
 * 教师服务接口
 */
public interface TeacherService {
    
    /**
     * 创建教师
     */
    Result<Teacher> createTeacher(Teacher teacher);
    
    /**
     * 根据ID获取教师
     */
    Result<Teacher> getTeacherById(Long id);
    
    /**
     * 根据用户ID获取教师
     */
    Result<Teacher> getTeacherByUserId(Long userId);
    
    /**
     * 获取所有教师
     */
    Result<List<Teacher>> getAllTeachers();
    
    /**
     * 根据姓名搜索教师
     */
    Result<List<Teacher>> searchTeachersByName(String name);
    
    /**
     * 根据专业搜索教师
     */
    Result<List<Teacher>> searchTeachersByMajor(String major);
    
    /**
     * 根据地区搜索教师
     */
    Result<List<Teacher>> searchTeachersByRegion(Long regionId);
    
    /**
     * 根据评分范围搜索教师
     */
    Result<List<Teacher>> searchTeachersByRating(Double minRating, Double maxRating);
    
    /**
     * 获取评分最高的教师
     */
    Result<List<Teacher>> getTopRatedTeachers();
    
    /**
     * 更新教师信息
     */
    Result<Teacher> updateTeacher(Teacher teacher);
    
    /**
     * 更新教师评分
     */
    Result<Teacher> updateTeacherRating(Long teacherId, Double rating);
    
    /**
     * 删除教师
     */
    Result<Void> deleteTeacher(Long id);
    
    /**
     * 根据状态获取教师列表
     */
    Result<List<Teacher>> getTeachersByStatus(Integer status);
    
    /**
     * 多条件筛选教师
     */
    Result<List<Teacher>> getTeachersWithFilters(
        String subject, Long regionId, Double minPrice, Double maxPrice
    );
} 