package com.niit.regionservice.service.impl;

import com.niit.common.entity.HupRegion;
import com.niit.common.util.Result;
import com.niit.regionservice.repository.RegionRepository;
import com.niit.regionservice.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 地区服务实现类
 */
@Service
@Transactional
public class RegionServiceImpl implements RegionService {
    
    @Autowired
    private RegionRepository regionRepository;
    
    @Override
    public Result<HupRegion> createRegion(HupRegion region) {
        try {
            // 检查代码是否已存在
            HupRegion existingRegion = regionRepository.findByCode(region.getCode());
            if (existingRegion != null) {
                return Result.error("地区代码已存在");
            }
            
            // 设置默认值
            if (region.getStatus() == null) {
                region.setStatus(1); // 1-正常状态
            }
            
            HupRegion savedRegion = regionRepository.save(region);
            return Result.success(savedRegion);
        } catch (Exception e) {
            return Result.error("创建地区失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<HupRegion> getRegionById(Long id) {
        try {
            Optional<HupRegion> region = regionRepository.findById(id);
            if (region.isPresent()) {
                return Result.success(region.get());
            } else {
                return Result.error("地区不存在");
            }
        } catch (Exception e) {
            return Result.error("获取地区信息失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<HupRegion>> getAllRegions() {
        try {
            List<HupRegion> regions = regionRepository.findAll();
            return Result.success(regions);
        } catch (Exception e) {
            return Result.error("获取地区列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<HupRegion>> getRegionsByParentId(Long parentId) {
        try {
            List<HupRegion> regions = regionRepository.findByParentId(parentId);
            return Result.success(regions);
        } catch (Exception e) {
            return Result.error("获取地区列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<HupRegion>> getRegionsByLevel(Integer level) {
        try {
            List<HupRegion> regions = regionRepository.findByLevel(level);
            return Result.success(regions);
        } catch (Exception e) {
            return Result.error("获取地区列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<HupRegion>> searchRegionsByName(String name) {
        try {
            List<HupRegion> regions = regionRepository.findByNameContaining(name);
            return Result.success(regions);
        } catch (Exception e) {
            return Result.error("搜索地区失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<HupRegion> getRegionByCode(String code) {
        try {
            HupRegion region = regionRepository.findByCode(code);
            if (region != null) {
                return Result.success(region);
            } else {
                return Result.error("地区不存在");
            }
        } catch (Exception e) {
            return Result.error("获取地区信息失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<HupRegion>> getAllProvinces() {
        try {
            List<HupRegion> provinces = regionRepository.findAllProvinces();
            return Result.success(provinces);
        } catch (Exception e) {
            return Result.error("获取省份列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<HupRegion>> getCitiesByProvinceId(Long provinceId) {
        try {
            List<HupRegion> cities = regionRepository.findCitiesByProvinceId(provinceId);
            return Result.success(cities);
        } catch (Exception e) {
            return Result.error("获取城市列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<HupRegion>> getDistrictsByCityId(Long cityId) {
        try {
            List<HupRegion> districts = regionRepository.findDistrictsByCityId(cityId);
            return Result.success(districts);
        } catch (Exception e) {
            return Result.error("获取区县列表失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<HupRegion> updateRegion(HupRegion region) {
        try {
            if (region.getId() == null) {
                return Result.error("地区ID不能为空");
            }
            
            Optional<HupRegion> existingRegion = regionRepository.findById(Long.valueOf(region.getId()));
            if (!existingRegion.isPresent()) {
                return Result.error("地区不存在");
            }
            
            HupRegion updatedRegion = regionRepository.save(region);
            return Result.success(updatedRegion);
        } catch (Exception e) {
            return Result.error("更新地区信息失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Void> deleteRegion(Long id) {
        try {
            if (!regionRepository.existsById(id)) {
                return Result.error("地区不存在");
            }
            
            regionRepository.deleteById(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除地区失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<HupRegion>> getRegionsByStatus(Integer status) {
        try {
            List<HupRegion> regions = regionRepository.findByStatus(status);
            return Result.success(regions);
        } catch (Exception e) {
            return Result.error("获取地区列表失败: " + e.getMessage());
        }
    }
} 