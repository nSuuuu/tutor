package com.niit.noticeservice.repository;

import com.niit.common.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    List<Notice> findByType(String type);
    List<Notice> findByUserId(Integer userId);
} 