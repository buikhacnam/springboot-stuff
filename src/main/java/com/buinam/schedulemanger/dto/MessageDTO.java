package com.buinam.schedulemanger.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDTO {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private StatusDTO status;
}
