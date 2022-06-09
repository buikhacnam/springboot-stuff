package com.buinam.schedulemanger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FcmSubscribeDTO {
    private String fcmToken;
    private String topic;
}
