package com.niit.teacherservice.controller;

import com.niit.common.entity.Teacher;
import com.niit.common.util.Result;
import com.niit.teacherservice.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师控制器
 */
@RestController
//@RequestMapping("/api/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {
    
    @Autowired
    private TeacherService teacherService;
    
    /**
     * 创建教师
     */
    @PostMapping
    public Result<Teacher> createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }
    
    /**
     * 根据ID获取教师
     */
    @GetMapping("/{id}")
    public Result<Teacher> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }
    
    /**
     * 根据用户ID获取教师
     */
    @GetMapping("/user/{userId}")
    public Result<Teacher> getTeacherByUserId(@PathVariable Long userId) {
        return teacherService.getTeacherByUserId(userId);
    }
    
    /**
     * 获取所有教师
     */
    @GetMapping
    public Result<List<Teacher>> getAllTeachers() {
        return teacherService.getAllTeachers();
    }
    
    /**
     * 根据姓名搜索教师
     */
    @GetMapping("/search/name")
    public Result<List<Teacher>> searchTeachersByName(@RequestParam String name) {
        return teacherService.searchTeachersByName(name);
    }
    
    /**
     * 根据专业搜索教师
     */
    @GetMapping("/search/major")
    public Result<List<Teacher>> searchTeachersByMajor(@RequestParam String major) {
        return teacherService.searchTeachersByMajor(major);
    }
    
    /**
     * 根据地区搜索教师
     */
    @GetMapping("/search/region")
    public Result<List<Teacher>> searchTeachersByRegion(@RequestParam Long regionId) {
        return teacherService.searchTeachersByRegion(regionId);
    }
    
    /**
     * 根据评分范围搜索教师
     */
    @GetMapping("/search/rating")
    public Result<List<Teacher>> searchTeachersByRating(
            @RequestParam Double minRating,
            @RequestParam Double maxRating) {
        return teacherService.searchTeachersByRating(minRating, maxRating);
    }
    
    /**
     * 获取评分最高的教师
     */
    @GetMapping("/top-rated")
    public Result<List<Teacher>> getTopRatedTeachers() {
        return teacherService.getTopRatedTeachers();
    }
    
    /**
     * 更新教师信息
     */
    @PutMapping
    public Result<Teacher> updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.updateTeacher(teacher);
    }
    
    /**
     * 更新教师评分
     */
    @PutMapping("/{teacherId}/rating")
    public Result<Teacher> updateTeacherRating(
            @PathVariable Long teacherId,
            @RequestParam Double rating) {
        return teacherService.updateTeacherRating(teacherId, rating);
    }
    
    /**
     * 删除教师
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteTeacher(@PathVariable Long id) {
        return teacherService.deleteTeacher(id);
    }
    
    /**
     * 根据状态获取教师列表
     */
    @GetMapping("/status/{status}")
    public Result<List<Teacher>> getTeachersByStatus(@PathVariable Integer status) {
        return teacherService.getTeachersByStatus(status);
    }
    
    /**
     * 获取所有教师列表
     */
    @GetMapping("/list")
    public Result<List<Teacher>> getAllTeachersList() {
        return teacherService.getAllTeachers();
    }
    
    /**
     * 多条件筛选教师（REST接口）
     */
    @GetMapping("/search")
    public Result<List<Teacher>> searchTeachers(
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "regionId", required = false) Long regionId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice) {
        return teacherService.getTeachersWithFilters(subject, regionId, minPrice, maxPrice);
    }
    
    /**
     * 多条件筛选教师，兼容UserService的FeignClient调用
     */
    @GetMapping("/teacher/search")
    public Result<List<Teacher>> searchTeachers(
        @RequestParam(value = "gradeLevel", required = false) String gradeLevel,
        @RequestParam(value = "subject", required = false) String subject,
        @RequestParam(value = "province", required = false) String province,
        @RequestParam(value = "city", required = false) String city,
        @RequestParam(value = "minPrice", required = false) Integer minPrice,
        @RequestParam(value = "maxPrice", required = false) Integer maxPrice
    ) {
        // 这里可根据实际业务完善筛选逻辑，暂时返回全部老师
        return teacherService.getAllTeachers();
    }
    
    /**
     * 获取所有老师列表，兼容FeignClient
     */
    @GetMapping("/teacher/list")
    public Result<List<Teacher>> getAllTeachersListCompat() {
        return teacherService.getAllTeachers();
    }
} 