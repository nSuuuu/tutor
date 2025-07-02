package com.niit.regionservice.service;

import com.niit.common.entity.HupRegion;
import com.niit.common.util.Result;

import java.util.List;

/**
 * 地区服务接口
 */
public interface RegionService {
    
    /**
     * 创建地区
     */
    Result<HupRegion> createRegion(HupRegion region);
    
    /**
     * 根据ID获取地区
     */
    Result<HupRegion> getRegionById(Long id);
    
    /**
     * 获取所有地区
     */
    Result<List<HupRegion>> getAllRegions();
    
    /**
     * 根据父级ID获取地区列表
     */
    Result<List<HupRegion>> getRegionsByParentId(Long parentId);
    
    /**
     * 根据级别获取地区列表
     */
    Result<List<HupRegion>> getRegionsByLevel(Integer level);
    
    /**
     * 根据名称搜索地区
     */
    Result<List<HupRegion>> searchRegionsByName(String name);
    
    /**
     * 根据代码获取地区
     */
    Result<HupRegion> getRegionByCode(String code);
    
    /**
     * 获取所有省份
     */
    Result<List<HupRegion>> getAllProvinces();
    
    /**
     * 根据省份ID获取城市列表
     */
    Result<List<HupRegion>> getCitiesByProvinceId(Long provinceId);
    
    /**
     * 根据城市ID获取区县列表
     */
    Result<List<HupRegion>> getDistrictsByCityId(Long cityId);
    
    /**
     * 更新地区信息
     */
    Result<HupRegion> updateRegion(HupRegion region);
    
    /**
     * 删除地区
     */
    Result<Void> deleteRegion(Long id);
    
    /**
     * 根据状态获取地区列表
     */
    Result<List<HupRegion>> getRegionsByStatus(Integer status);
} 