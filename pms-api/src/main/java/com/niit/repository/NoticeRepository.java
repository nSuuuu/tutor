package com.niit.repository;

import com.niit.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    List<Notice> findByType(String type);
    List<Notice> findByUserId(Integer userId);

    List<Notice> findByUserIdOrderByCreateTimeDesc(Integer userId);

    List<Notice> findByUserIdAndReadFalseOrderByCreateTimeDesc(Integer userId);

    int countByUserIdAndReadFalse(Integer userId);
}