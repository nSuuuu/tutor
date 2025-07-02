package com.niit.common.entity;

import lombok.Data;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "chat_message")
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private String status;
    private Long timestamp;
    // 消息创建时间
    private LocalDateTime createTime;
    // 是否已读
    private Boolean isRead;
} 