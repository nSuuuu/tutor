package com.niit.teacherservice.service.impl;

import com.niit.common.entity.Teacher;
import com.niit.common.util.BusinessException;
import com.niit.common.util.Result;
import com.niit.teacherservice.repository.TeacherRepository;
import com.niit.teacherservice.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 教师服务实现类
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Override
    public Result<Teacher> createTeacher(Teacher teacher) {
        try {
            // 检查用户ID是否已存在
            Optional<Teacher> existingTeacher = teacherRepository.findById(Long.valueOf(teacher.getId()));
            if (existingTeacher.isPresent()) {
                return Result.error("该用户已经是教师");
            }
            
            // 设置默认值
            if (teacher.getRating() == null) {
                teacher.setRating(5.0);
            }
            if (teacher.getStatus() == null) {
                teacher.setStatus("1"); // 1-正常状态
            }
            
            Teacher savedTeacher = teacherRepository.save(teacher);
            return Result.success(savedTeacher);
        } catch (Exception e) {
            return Result.error("创建教师失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Teacher> getTeacherById(Long id) {
        try {
            Optional<Teacher> teacher = teacherRepository.findById(id);
            if (teacher.isPresent()) {
                return Result.success(teacher.get());
            } else {
                return Result.error("教师不存在");
            }
        } catch (Exception e) {
            return Result.error("获取教师信息失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Teacher> getTeacherByUserId(Long userId) {
        try {
            Optional<Teacher> teacher = teacherRepository.findById(userId);
            if (teacher.isPresent()) {
                return Result.success(teacher.get());
            } else {
                return Result.error("教师不存在");
            }
        } catch (Exception e) {
            return Result.error("获取教师信息失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<Teacher>> getAllTeachers() {
        try {
            List<Teacher> teachers = teacherRepository.findAll();
            return Result.success(teachers);
        } catch (Exception e) {
            return Result.error("获取教师列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<Teacher>> searchTeachersByName(String name) {
        try {
            List<Teacher> teachers = teacherRepository.findByUserRealNameContaining(name);
            return Result.success(teachers);
        } catch (Exception e) {
            return Result.error("搜索教师失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<Teacher>> searchTeachersByMajor(String major) {
        try {
            List<Teacher> teachers = teacherRepository.findByMajor(major);
            return Result.success(teachers);
        } catch (Exception e) {
            return Result.error("搜索教师失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<Teacher>> searchTeachersByRegion(Long regionId) {
        try {
            List<Teacher> teachers = teacherRepository.findByRegionId(regionId);
            return Result.success(teachers);
        } catch (Exception e) {
            return Result.error("搜索教师失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<Teacher>> searchTeachersByRating(Double minRating, Double maxRating) {
        try {
            List<Teacher> teachers = teacherRepository.findByRatingRange(minRating, maxRating);
            return Result.success(teachers);
        } catch (Exception e) {
            return Result.error("搜索教师失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<Teacher>> getTopRatedTeachers() {
        try {
            List<Teacher> teachers = teacherRepository.findTopRatedTeachers();
            return Result.success(teachers);
        } catch (Exception e) {
            return Result.error("获取评分最高教师失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Teacher> updateTeacher(Teacher teacher) {
        try {
            if (teacher.getId() == null) {
                return Result.error("教师ID不能为空");
            }
            
            Optional<Teacher> existingTeacher = teacherRepository.findById(Long.valueOf(teacher.getId()));
            if (!existingTeacher.isPresent()) {
                return Result.error("教师不存在");
            }
            
            Teacher updatedTeacher = teacherRepository.save(teacher);
            return Result.success(updatedTeacher);
        } catch (Exception e) {
            return Result.error("更新教师信息失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Teacher> updateTeacherRating(Long teacherId, Double rating) {
        try {
            Optional<Teacher> teacherOpt = teacherRepository.findById(teacherId);
            if (!teacherOpt.isPresent()) {
                return Result.error("教师不存在");
            }
            
            Teacher teacher = teacherOpt.get();
            teacher.setRating(rating);
            Teacher updatedTeacher = teacherRepository.save(teacher);
            return Result.success(updatedTeacher);
        } catch (Exception e) {
            return Result.error("更新教师评分失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Void> deleteTeacher(Long id) {
        try {
            if (!teacherRepository.existsById(id)) {
                return Result.error("教师不存在");
            }
            
            teacherRepository.deleteById(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除教师失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<Teacher>> getTeachersByStatus(Integer status) {
        try {
            List<Teacher> teachers = teacherRepository.findByStatus(status);
            return Result.success(teachers);
        } catch (Exception e) {
            return Result.error("获取教师列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<Teacher>> getTeachersWithFilters(
        String subject, Long regionId, Double minPrice, Double maxPrice) {
        try {
            List<Teacher> teachers = teacherRepository.findAll();
            List<Teacher> filtered = teachers.stream()
                .filter(t -> subject == null || subject.equals(t.getSubject()))
                .filter(t -> regionId == null || (t.getRegionId() != null && t.getRegionId().equals(regionId)))
                .filter(t -> minPrice == null || (t.getHourlyRate() != null && t.getHourlyRate() >= minPrice))
                .filter(t -> maxPrice == null || (t.getHourlyRate() != null && t.getHourlyRate() <= maxPrice))
                .collect(java.util.stream.Collectors.toList());
            return Result.success(filtered);
        } catch (Exception e) {
            return Result.error("多条件筛选教师失败: " + e.getMessage());
        }
    }
} 