package com.buinam.schedulemanger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor


public class SendNotificationDTO  {
    private String title;
    private String content;
    private String data;
    private String topic;
    private String to;
}
