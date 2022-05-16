package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.ConversationDTO;
import com.buinam.schedulemanger.dto.LazyLoadDTO;
import com.buinam.schedulemanger.model.Chat;
import com.buinam.schedulemanger.model.Message;
import com.buinam.schedulemanger.repository.ChatRepository;

import com.buinam.schedulemanger.repository.MessageRepository;
import com.buinam.schedulemanger.service.MessageService;
import com.buinam.schedulemanger.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/chat-history")
public class ChatHistoryController {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;


    @GetMapping("/detail/{id}")
    public Object getChatHistoryById(@PathVariable(name = "id") Long id)  {
        Optional<Chat> chat = chatRepository.findById(id);
        if (chat.isPresent()){
            //find all messages with this chat id
            List<Message> messages = messageRepository.findByChatId(chat.get().getId());
            return messages;
        } else {
            return null;
        }
    }

    @GetMapping("/seen")
    public ResponseEntity<CommonResponse> seenMessage(@RequestParam(required = true) String senderName,
                                                      @RequestParam(required = true) String receiverName) {
        try{
            Chat chat = messageService.seenMessage(senderName, receiverName);
            return new ResponseEntity<>(
                    new CommonResponse(
                            "seen new message successfully",
                            true,
                            chat,
                            HttpStatus.OK.value()
                    ),
                    HttpStatus.OK
            );
        } catch(Exception e) {
            return new ResponseEntity<>(
                    new CommonResponse(
                            e.getMessage(),
                            true,
                            null,
                            HttpStatus.BAD_REQUEST.value()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/private")
    public ResponseEntity<CommonResponse> searchPrivateMessage(
            @RequestParam(required = true) String senderName,
            @RequestParam(required = true) String receiverName,
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String pageSize,
            @RequestParam(required = false) String pageNumber
    ) {
        try{
            LazyLoadDTO messages = messageService.searchMessage(senderName, receiverName, message, fromDate, toDate, pageSize, pageNumber);
            return new ResponseEntity<>(
                    new CommonResponse(
                            "search private message successfully",
                            true,
                            messages,
                            HttpStatus.OK.value()
                    ),
                    HttpStatus.OK
            );
        } catch(Exception e) {
            return new ResponseEntity<>(
                    new CommonResponse(
                            e.getMessage(),
                            true,
                            null,
                            HttpStatus.BAD_REQUEST.value()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

    }

    @GetMapping("/conversation/{senderName}")
    public ResponseEntity<CommonResponse> searchConversations(
            @PathVariable(name = "senderName") String senderName
    ) {
        try{
            List<ConversationDTO> conversations = messageService.searchConversations(senderName);
            return new ResponseEntity<>(
                    new CommonResponse(
                            "search conversations successfully",
                            true,
                            conversations,
                            HttpStatus.OK.value()
                    ),
                    HttpStatus.OK
            );
        } catch(Exception e) {
            return new ResponseEntity<>(
                    new CommonResponse(
                            e.getMessage(),
                            true,
                            null,
                            HttpStatus.BAD_REQUEST.value()
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

    }
}

