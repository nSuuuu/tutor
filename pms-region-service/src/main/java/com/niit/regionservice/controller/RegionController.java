package com.niit.regionservice.controller;

import com.niit.common.entity.HupRegion;
import com.niit.common.util.Result;
import com.niit.regionservice.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地区控制器
 */
@RestController
@RequestMapping("/api/region")
@CrossOrigin(origins = "*")
public class RegionController {
    
    @Autowired
    private RegionService regionService;
    
    /**
     * 创建地区
     */
    @PostMapping
    public Result<HupRegion> createRegion(@RequestBody HupRegion region) {
        return regionService.createRegion(region);
    }
    
    /**
     * 根据ID获取地区
     */
    @GetMapping("/{id}")
    public Result<HupRegion> getRegionById(@PathVariable Long id) {
        return regionService.getRegionById(id);
    }
    
    /**
     * 获取所有地区
     */
    @GetMapping
    public Result<List<HupRegion>> getAllRegions() {
        return regionService.getAllRegions();
    }
    
    /**
     * 根据父级ID获取地区列表
     */
    @GetMapping("/parent/{parentId}")
    public Result<List<HupRegion>> getRegionsByParentId(@PathVariable Long parentId) {
        return regionService.getRegionsByParentId(parentId);
    }
    
    /**
     * 根据级别获取地区列表
     */
    @GetMapping("/level/{level}")
    public Result<List<HupRegion>> getRegionsByLevel(@PathVariable Integer level) {
        return regionService.getRegionsByLevel(level);
    }
    
    /**
     * 根据名称搜索地区
     */
    @GetMapping("/search")
    public Result<List<HupRegion>> searchRegionsByName(@RequestParam String name) {
        return regionService.searchRegionsByName(name);
    }
    
    /**
     * 根据代码获取地区
     */
    @GetMapping("/code/{code}")
    public Result<HupRegion> getRegionByCode(@PathVariable String code) {
        return regionService.getRegionByCode(code);
    }
    
    /**
     * 获取所有省份
     */
    @GetMapping("/provinces")
    public Result<List<HupRegion>> getAllProvinces() {
        return regionService.getAllProvinces();
    }
    
    /**
     * 根据省份ID获取城市列表
     */
    @GetMapping("/cities/{provinceId}")
    public Result<List<HupRegion>> getCitiesByProvinceId(@PathVariable Long provinceId) {
        return regionService.getCitiesByProvinceId(provinceId);
    }
    
    /**
     * 根据城市ID获取区县列表
     */
    @GetMapping("/districts/{cityId}")
    public Result<List<HupRegion>> getDistrictsByCityId(@PathVariable Long cityId) {
        return regionService.getDistrictsByCityId(cityId);
    }
    
    /**
     * 更新地区信息
     */
    @PutMapping
    public Result<HupRegion> updateRegion(@RequestBody HupRegion region) {
        return regionService.updateRegion(region);
    }
    
    /**
     * 删除地区
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteRegion(@PathVariable Long id) {
        return regionService.deleteRegion(id);
    }
    
    /**
     * 根据状态获取地区列表
     */
    @GetMapping("/status/{status}")
    public Result<List<HupRegion>> getRegionsByStatus(@PathVariable Integer status) {
        return regionService.getRegionsByStatus(status);
    }
} 