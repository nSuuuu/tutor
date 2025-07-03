package com.niit.controller;

import com.niit.entity.Notice;
import com.niit.entity.User;
import com.niit.service.NoticeService;
import javax.servlet.http.HttpSession;

import com.niit.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/notices")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public String noticeList(
            @RequestParam(required = false) Integer userId,
            HttpSession session,
            Model model) {
        User user = (User) session.getAttribute("user");
        Integer targetUserId = (userId != null) ? userId : (user != null ? user.getId() : null);
        if (targetUserId == null) return "redirect:/login";
        List<Notice> notices = noticeService.getNoticesByUser(targetUserId);
        int unreadCount = noticeService.getUnreadCount(targetUserId);
        model.addAttribute("notices", notices);
        model.addAttribute("unreadCount", unreadCount);
        return "notices";
    }

    @PostMapping("/read")
    public String markRead(@RequestParam Integer noticeId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        noticeService.markAsRead(noticeId);
        return "redirect:/notices";
    }

    @PostMapping("/read-all")
    public String markAllRead(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        noticeService.markAllAsRead(user.getId());
        return "redirect:/notices";
    }

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