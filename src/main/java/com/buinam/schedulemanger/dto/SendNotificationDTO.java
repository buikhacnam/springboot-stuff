package com.buinam.schedulemanger.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendNotificationDTO  {
    private Long id;
    private String fromUser;
    private String toUser;
    private String title;
    private String content;
    private String data;
    private String topic;
    private Integer retryNum;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
    private String createdBy;
    private String updatedBy;
    private String urlWeb;
    private String notificationType;
    private Long key;

    public Long getKey() {
        return id;
    }
}
