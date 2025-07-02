package com.niit.noticeservice.service.impl;

import com.niit.common.entity.Notice;
import com.niit.noticeservice.repository.NoticeRepository;
import com.niit.noticeservice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public List<Notice> getAllNotices() { return noticeRepository.findAll(); }

    @Override
    public Optional<Notice> getNoticeById(Integer id) { return noticeRepository.findById(id); }

    @Override
    public Notice saveNotice(Notice notice) { return noticeRepository.save(notice); }

    @Override
    public void deleteNotice(Integer id) { noticeRepository.deleteById(id); }

    @Override
    public List<Notice> getNoticesByType(String type) { return noticeRepository.findByType(type); }

    @Override
    public List<Notice> getNoticesByUserId(Integer userId) { return noticeRepository.findByUserId(userId); }
} 