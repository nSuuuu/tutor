package com.niit.chatservice.service;

import com.niit.common.entity.ChatMessage;
import com.niit.common.util.Result;

import java.util.List;

/**
 * 聊天服务接口
 */
public interface ChatService {
    
    /**
     * 发送消息
     */
    Result<ChatMessage> sendMessage(ChatMessage message);
    
    /**
     * 获取聊天历史记录
     */
    Result<List<ChatMessage>> getChatHistory(Long senderId, Long receiverId);
    
    /**
     * 获取用户的所有聊天记录
     */
    Result<List<ChatMessage>> getUserChatHistory(Long userId);
    
    /**
     * 获取未读消息
     */
    Result<List<ChatMessage>> getUnreadMessages(Long receiverId);
    
    /**
     * 标记消息为已读
     */
    Result<Void> markMessageAsRead(Long messageId);
    
    /**
     * 标记与特定用户的聊天为已读
     */
    Result<Void> markChatAsRead(Long userId, Long otherUserId);
    
    /**
     * 获取未读消息数量
     */
    Result<Long> getUnreadMessageCount(Long receiverId);
    
    /**
     * 删除消息
     */
    Result<Void> deleteMessage(Long messageId);
    
    /**
     * 删除聊天记录
     */
    Result<Void> deleteChatHistory(Long senderId, Long receiverId);
} 