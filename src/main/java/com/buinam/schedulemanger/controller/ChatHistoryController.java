package com.buinam.schedulemanger.controller;


import com.buinam.schedulemanger.model.Chat;
import com.buinam.schedulemanger.model.Message;
import com.buinam.schedulemanger.repository.ChatRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

