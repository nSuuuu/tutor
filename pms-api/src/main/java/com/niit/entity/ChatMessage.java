package com.niit.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_read", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean read = false;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    // 兼容旧代码的getter/setter
    public Integer getSenderId() {
        return fromUser != null ? fromUser.getId() : null;
    }
    public void setSenderId(Integer senderId) {
        if (fromUser == null) fromUser = new User();
        fromUser.setId(senderId);
    }
    public Integer getReceiverId() {
        return toUser != null ? toUser.getId() : null;
    }
    public void setReceiverId(Integer receiverId) {
        if (toUser == null) toUser = new User();
        toUser.setId(receiverId);
    }
    public Boolean getIsRead() {
        return read;
    }
    public void setIsRead(Boolean isRead) {
        this.read = isRead;
    }
} 