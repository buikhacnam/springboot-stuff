package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.model.Chat;
import com.buinam.schedulemanger.repository.ChatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/chat-history")
public class ChatHistoryController {

    @Autowired
    private ChatRepository chatRepository;


    @GetMapping("/{id}")
    public Object getChatHistoryById(@PathVariable(name = "id") Long id)  {
        Optional<Chat> chat = chatRepository.findById(id);
        return chat.orElse(null);
    }
}

