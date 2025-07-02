package com.niit.noticeservice.controller;

import com.niit.common.entity.Notice;
import com.niit.common.util.Result;
import com.niit.noticeservice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public Result<List<Notice>> getAllNotices() {
        return Result.success(noticeService.getAllNotices());
    }

    @GetMapping("/{id}")
    public Result<Notice> getNoticeById(@PathVariable Integer id) {
        Optional<Notice> notice = noticeService.getNoticeById(id);
        return notice.map(Result::success).orElseGet(() -> Result.error("通知不存在"));
    }

    @PostMapping("/add")
    public Result<Notice> addNotice(@RequestBody Notice notice) {
        return Result.success("通知添加成功", noticeService.saveNotice(notice));
    }

    @PutMapping("/{id}")
    public Result<Notice> updateNotice(@PathVariable Integer id, @RequestBody Notice notice) {
        notice.setId(id);
        return Result.success("通知更新成功", noticeService.saveNotice(notice));
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteNotice(@PathVariable Integer id) {
        noticeService.deleteNotice(id);
        return Result.success("通知删除成功");
    }

    @GetMapping("/type/{type}")
    public Result<List<Notice>> getNoticesByType(@PathVariable String type) {
        return Result.success(noticeService.getNoticesByType(type));
    }

    @GetMapping("/user/{userId}")
    public Result<List<Notice>> getNoticesByUserId(@PathVariable Integer userId) {
        return Result.success(noticeService.getNoticesByUserId(userId));
    }
} 