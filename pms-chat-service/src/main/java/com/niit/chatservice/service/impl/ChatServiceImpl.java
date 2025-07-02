package com.niit.chatservice.service.impl;

import com.niit.common.entity.ChatMessage;
import com.niit.common.util.Result;
import com.niit.chatservice.repository.ChatMessageRepository;
import com.niit.chatservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 聊天服务实现类
 */
@Service
@Transactional
public class ChatServiceImpl implements ChatService {
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    @Override
    public Result<ChatMessage> sendMessage(ChatMessage message) {
        try {
            // 设置默认值
            if (message.getCreateTime() == null) {
                message.setCreateTime(LocalDateTime.now());
            }
            if (message.getIsRead() == null) {
                message.setIsRead(false);
            }
            
            ChatMessage savedMessage = chatMessageRepository.save(message);
            return Result.success(savedMessage);
        } catch (Exception e) {
            return Result.error("发送消息失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<ChatMessage>> getChatHistory(Long senderId, Long receiverId) {
        try {
            List<ChatMessage> messages = chatMessageRepository.findChatHistory(senderId, receiverId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("获取聊天记录失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<ChatMessage>> getUserChatHistory(Long userId) {
        try {
            List<ChatMessage> messages = chatMessageRepository.findByUserId(userId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("获取用户聊天记录失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<List<ChatMessage>> getUnreadMessages(Long receiverId) {
        try {
            List<ChatMessage> messages = chatMessageRepository.findUnreadMessages(receiverId);
            return Result.success(messages);
        } catch (Exception e) {
            return Result.error("获取未读消息失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Void> markMessageAsRead(Long messageId) {
        try {
            Optional<ChatMessage> messageOpt = chatMessageRepository.findById(messageId);
            if (!messageOpt.isPresent()) {
                return Result.error("消息不存在");
            }
            
            ChatMessage message = messageOpt.get();
            message.setIsRead(true);
            chatMessageRepository.save(message);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("标记消息已读失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Void> markChatAsRead(Long userId, Long otherUserId) {
        try {
            List<ChatMessage> unreadMessages = chatMessageRepository.findUnreadMessages(userId);
            for (ChatMessage message : unreadMessages) {
                if (message.getSenderId().equals(otherUserId)) {
                    message.setIsRead(true);
                    chatMessageRepository.save(message);
                }
            }
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("标记聊天已读失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Long> getUnreadMessageCount(Long receiverId) {
        try {
            Long count = chatMessageRepository.countUnreadMessages(receiverId);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("获取未读消息数量失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Void> deleteMessage(Long messageId) {
        try {
            if (!chatMessageRepository.existsById(messageId)) {
                return Result.error("消息不存在");
            }
            
            chatMessageRepository.deleteById(messageId);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除消息失败: " + e.getMessage());
        }
    }
    
    @Override
    public Result<Void> deleteChatHistory(Long senderId, Long receiverId) {
        try {
            List<ChatMessage> messages = chatMessageRepository.findChatHistory(senderId, receiverId);
            chatMessageRepository.deleteAll(messages);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error("删除聊天记录失败: " + e.getMessage());
        }
    }
} 