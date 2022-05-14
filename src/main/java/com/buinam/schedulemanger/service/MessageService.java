package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.LazyLoadDTO;

public interface MessageService {
    LazyLoadDTO searchMessage(String senderName, String receiverName, String message, String fromDate, String toDate, String pageSize, String pageNumber);

}
