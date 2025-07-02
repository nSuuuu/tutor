package com.niit.chatservice.repository;

import com.niit.common.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 聊天消息数据访问层
 */
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    /**
     * 根据发送者和接收者查找聊天记录
     */
    @Query("SELECT c FROM ChatMessage c WHERE (c.senderId = :senderId AND c.receiverId = :receiverId) " +
           "OR (c.senderId = :receiverId AND c.receiverId = :senderId) ORDER BY c.createTime ASC")
    List<ChatMessage> findChatHistory(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
    
    /**
     * 根据用户ID查找所有相关聊天记录
     */
    @Query("SELECT c FROM ChatMessage c WHERE c.senderId = :userId OR c.receiverId = :userId ORDER BY c.createTime DESC")
    List<ChatMessage> findByUserId(@Param("userId") Long userId);
    
    /**
     * 查找未读消息
     */
    @Query("SELECT c FROM ChatMessage c WHERE c.receiverId = :receiverId AND c.isRead = false ORDER BY c.createTime DESC")
    List<ChatMessage> findUnreadMessages(@Param("receiverId") Long receiverId);
    
    /**
     * 统计未读消息数量
     */
    @Query("SELECT COUNT(c) FROM ChatMessage c WHERE c.receiverId = :receiverId AND c.isRead = false")
    Long countUnreadMessages(@Param("receiverId") Long receiverId);
    
    /**
     * 根据发送者ID查找消息
     */
    List<ChatMessage> findBySenderIdOrderByCreateTimeDesc(Long senderId);
    
    /**
     * 根据接收者ID查找消息
     */
    List<ChatMessage> findByReceiverIdOrderByCreateTimeDesc(Long receiverId);
} 