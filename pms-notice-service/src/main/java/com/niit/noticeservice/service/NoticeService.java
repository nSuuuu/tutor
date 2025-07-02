package com.niit.noticeservice.service;

import com.niit.common.entity.Notice;
import java.util.List;
import java.util.Optional;

public interface NoticeService {
    List<Notice> getAllNotices();
    Optional<Notice> getNoticeById(Integer id);
    Notice saveNotice(Notice notice);
    void deleteNotice(Integer id);
    List<Notice> getNoticesByType(String type);
    List<Notice> getNoticesByUserId(Integer userId);
} 