package com.niit.feign;

import com.niit.entity.Notice;
import com.niit.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pms-notice-service")
@RequestMapping("/notice")
public interface NoticeFeignClient {
    
    @GetMapping("/list")
    Result<List<Notice>> getAllNotices();
    
    @GetMapping("/{id}")
    Result<Notice> getNoticeById(@PathVariable("id") Integer id);
    
    @GetMapping("/user/{userId}")
    Result<List<Notice>> getNoticesByUserId(@PathVariable("userId") Integer userId);
    
    @GetMapping("/type/{type}")
    Result<List<Notice>> getNoticesByType(@PathVariable("type") String type);
    
    @GetMapping("/unread/{userId}")
    Result<List<Notice>> getUnreadNoticesByUserId(@PathVariable("userId") Integer userId);
    
    @PostMapping("/create")
    Result<Notice> createNotice(@RequestBody Notice notice);
    
    @PutMapping("/update")
    Result<Notice> updateNotice(@RequestBody Notice notice);
    
    @PutMapping("/{id}/read")
    Result<Notice> markNoticeAsRead(@PathVariable("id") Integer id);
    
    @DeleteMapping("/{id}")
    Result<String> deleteNotice(@PathVariable("id") Integer id);
} 