package com.niit.api.feign;


import com.niit.common.entity.ChatMessage;
import com.niit.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "pms-chat-service")
public interface ChatFeignClient {
    
    @GetMapping("/chat/messages")
    Result<List<ChatMessage>> getAllMessages();
    
    @GetMapping("/chat/{id}")
    Result<ChatMessage> getMessageById(@PathVariable("id") Integer id);
    
    @GetMapping("/chat/conversation/{senderId}/{receiverId}")
    Result<List<ChatMessage>> getConversation(@PathVariable("senderId") Integer senderId, 
                                             @PathVariable("receiverId") Integer receiverId);
    
    @GetMapping("/chat/user/{userId}")
    Result<List<ChatMessage>> getMessagesByUserId(@PathVariable("userId") Integer userId);
    
    @GetMapping("/chat/unread/{userId}")
    Result<List<ChatMessage>> getUnreadMessagesByUserId(@PathVariable("userId") Integer userId);
    
    @PostMapping("/chat/send")
    Result<ChatMessage> sendMessage(@RequestBody ChatMessage message);
    
    @PutMapping("/chat/update")
    Result<ChatMessage> updateMessage(@RequestBody ChatMessage message);
    
    @PutMapping("/chat/{id}/read")
    Result<ChatMessage> markMessageAsRead(@PathVariable("id") Integer id);
    
    @DeleteMapping("/chat/{id}")
    Result<String> deleteMessage(@PathVariable("id") Integer id);
} 