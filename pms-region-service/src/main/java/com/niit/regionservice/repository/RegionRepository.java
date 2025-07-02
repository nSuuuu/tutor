package com.niit.regionservice.repository;

import com.niit.common.entity.HupRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 地区数据访问层
 */
@Repository
public interface RegionRepository extends JpaRepository<HupRegion, Long> {
    
    /**
     * 根据父级ID查找地区
     */
    List<HupRegion> findByParentId(Long parentId);
    
    /**
     * 根据级别查找地区
     */
    List<HupRegion> findByLevel(Integer level);
    
    /**
     * 根据名称查找地区
     */
    List<HupRegion> findByNameContaining(String name);
    
    /**
     * 根据代码查找地区
     */
    HupRegion findByCode(String code);
    
    /**
     * 查找所有省份
     */
    @Query("SELECT r FROM HupRegion r WHERE r.level = 1 ORDER BY r.code")
    List<HupRegion> findAllProvinces();
    
    /**
     * 根据省份ID查找城市
     */
    @Query("SELECT r FROM HupRegion r WHERE r.parentId = :provinceId ORDER BY r.code")
    List<HupRegion> findCitiesByProvinceId(@Param("provinceId") Long provinceId);
    
    /**
     * 根据城市ID查找区县
     */
    @Query("SELECT r FROM HupRegion r WHERE r.parentId = :cityId ORDER BY r.code")
    List<HupRegion> findDistrictsByCityId(@Param("cityId") Long cityId);
    
    /**
     * 根据状态查找地区
     */
    List<HupRegion> findByStatus(Integer status);
} 