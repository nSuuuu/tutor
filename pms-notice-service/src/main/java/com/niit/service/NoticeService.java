package com.niit.service;

import com.niit.entity.Notice;
import java.util.List;
import java.util.Optional;

public interface NoticeService {
    Notice sendNotice(Integer userId, String title, String content, Notice.NoticeType type);
    Notice sendNoticeWithRef(Integer userId, String title, String content,
                             Notice.NoticeType type, Integer refId, Notice.RefType refType);
    List<Notice> getNoticesByUser(Integer userId);
    List<Notice> getUnreadNoticesByUser(Integer userId);
    int getUnreadCount(Integer userId);
    void markAsRead(Integer noticeId);
    void markAllAsRead(Integer userId);
    List<Notice> getAllNotices();
    Optional<Notice> getNoticeById(Integer id);
    Notice saveNotice(Notice notice);
    void deleteNotice(Integer id);
    List<Notice> getNoticesByType(String type);
    List<Notice> getNoticesByUserId(Integer userId);
} 