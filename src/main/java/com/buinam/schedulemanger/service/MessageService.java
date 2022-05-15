package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.LazyLoadDTO;
import com.buinam.schedulemanger.model.Chat;

import java.util.List;

public interface MessageService {
    LazyLoadDTO searchMessage(String senderName, String receiverName, String message, String fromDate, String toDate, String pageSize, String pageNumber);

    List<Chat> searchConversations(String senderName);
}
