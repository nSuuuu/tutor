package com.niit.userservice.repository;

import com.niit.common.entity.ChatMessage;
import com.niit.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findBySenderIdAndReceiverIdOrderByCreateTimeAsc(Integer senderId, Integer receiverId);
    List<ChatMessage> findByReceiverIdAndIsReadFalse(Integer receiverId);
    // List<ChatMessage> findByFromUser(User user);
    // List<ChatMessage> findByToUser(User user);
} 