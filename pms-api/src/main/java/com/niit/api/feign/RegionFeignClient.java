package com.niit.api.feign;

import com.niit.common.entity.Region;
import com.niit.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pms-region-service")
@RequestMapping("/region")
public interface RegionFeignClient {
    
    @GetMapping("/list")
    Result<List<Region>> getAllRegions();
    
    @GetMapping("/{id}")
    Result<Region> getRegionById(@PathVariable("id") Integer id);
    
    @GetMapping("/province/{provinceCode}")
    Result<List<Region>> getRegionsByProvince(@PathVariable("provinceCode") String provinceCode);
    
    @GetMapping("/city/{cityCode}")
    Result<List<Region>> getRegionsByCity(@PathVariable("cityCode") String cityCode);
    
    @GetMapping("/name/{name}")
    Result<List<Region>> getRegionsByName(@PathVariable("name") String name);
    
    @PostMapping("/create")
    Result<Region> createRegion(@RequestBody Region region);
    
    @PutMapping("/update")
    Result<Region> updateRegion(@RequestBody Region region);
    
    @DeleteMapping("/{id}")
    Result<String> deleteRegion(@PathVariable("id") Integer id);
} 