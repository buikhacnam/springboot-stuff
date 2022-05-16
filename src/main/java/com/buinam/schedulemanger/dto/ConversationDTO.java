package com.buinam.schedulemanger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConversationDTO {
    Long id;
    String user;
    Long unRead;
}
