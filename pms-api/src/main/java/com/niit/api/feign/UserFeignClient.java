package com.niit.api.feign;

import com.niit.common.entity.User;
import com.niit.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "pms-user-service")
@RequestMapping("/user")
public interface UserFeignClient {
    @GetMapping("/{id}")
    Result<User> getUserById(@PathVariable("id") Integer id);
} 