package com.niit.chatservice.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niit.common.entity.ChatMessage;
import com.niit.chatservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * WebSocket聊天处理器
 */
@Controller
public class ChatWebSocketHandler {
    
    @Autowired
    private ChatService chatService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    /**
     * 处理发送消息
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        // 保存消息到数据库
        chatService.sendMessage(chatMessage);
        
        // 发送给特定用户
        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiverId().toString(),
                "/queue/messages",
                chatMessage
        );
        
        return chatMessage;
    }
    
    /**
     * 处理用户加入聊天
     */
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                              SimpMessageHeaderAccessor headerAccessor) {
        // 添加用户名到WebSocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderId());
        return chatMessage;
    }
    
    /**
     * 处理私聊消息
     */
    @MessageMapping("/chat.private")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
        // 保存消息到数据库
        chatService.sendMessage(chatMessage);
        
        // 发送给接收者
        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiverId().toString(),
                "/queue/private",
                chatMessage
        );
        
        // 发送给发送者确认
        messagingTemplate.convertAndSendToUser(
                chatMessage.getSenderId().toString(),
                "/queue/private",
                chatMessage
        );
    }
} 