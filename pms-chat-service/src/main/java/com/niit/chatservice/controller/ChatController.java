package com.niit.chatservice.controller;

import com.niit.common.entity.ChatMessage;
import com.niit.common.util.Result;
import com.niit.chatservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 聊天控制器
 */
@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    
    @Autowired
    private ChatService chatService;
    
    /**
     * 发送消息
     */
    @PostMapping("/send")
    public Result<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
        return chatService.sendMessage(message);
    }
    
    /**
     * 获取聊天历史记录
     */
    @GetMapping("/history")
    public Result<List<ChatMessage>> getChatHistory(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {
        return chatService.getChatHistory(senderId, receiverId);
    }
    
    /**
     * 获取用户的所有聊天记录
     */
    @GetMapping("/user/{userId}")
    public Result<List<ChatMessage>> getUserChatHistory(@PathVariable Long userId) {
        return chatService.getUserChatHistory(userId);
    }
    
    /**
     * 获取未读消息
     */
    @GetMapping("/unread/{receiverId}")
    public Result<List<ChatMessage>> getUnreadMessages(@PathVariable Long receiverId) {
        return chatService.getUnreadMessages(receiverId);
    }
    
    /**
     * 标记消息为已读
     */
    @PutMapping("/read/{messageId}")
    public Result<Void> markMessageAsRead(@PathVariable Long messageId) {
        return chatService.markMessageAsRead(messageId);
    }
    
    /**
     * 标记与特定用户的聊天为已读
     */
    @PutMapping("/read-chat")
    public Result<Void> markChatAsRead(
            @RequestParam Long userId,
            @RequestParam Long otherUserId) {
        return chatService.markChatAsRead(userId, otherUserId);
    }
    
    /**
     * 获取未读消息数量
     */
    @GetMapping("/unread-count/{receiverId}")
    public Result<Long> getUnreadMessageCount(@PathVariable Long receiverId) {
        return chatService.getUnreadMessageCount(receiverId);
    }
    
    /**
     * 删除消息
     */
    @DeleteMapping("/{messageId}")
    public Result<Void> deleteMessage(@PathVariable Long messageId) {
        return chatService.deleteMessage(messageId);
    }
    
    /**
     * 删除聊天记录
     */
    @DeleteMapping("/history")
    public Result<Void> deleteChatHistory(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {
        return chatService.deleteChatHistory(senderId, receiverId);
    }
} 