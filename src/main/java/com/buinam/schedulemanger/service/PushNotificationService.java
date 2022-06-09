package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.SendNotificationDTO;

import java.util.concurrent.Future;

public interface PushNotificationService {
    public String sendMessageFirebase(SendNotificationDTO sendNotificationDTO);
}
