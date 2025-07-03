package com.niit.feign;

import com.niit.entity.User;
import com.niit.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "pms-user-service")
@RequestMapping("/user")
public interface UserFeignClient {
    @GetMapping("/{id}")
    Result<User> getUserById(@PathVariable("id") Integer id);
} 