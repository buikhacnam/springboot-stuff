package com.buinam.schedulemanger.model;

import com.buinam.schedulemanger.dto.StatusDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String senderName;
    private String receiverName;
    private String message;
    private LocalDateTime date;
    private StatusDTO status;
    private Long chatId;
}
