package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.MessageDTO;
import com.buinam.schedulemanger.dto.StatusDTO;
import com.buinam.schedulemanger.model.Message;
import com.buinam.schedulemanger.model.Chat;
import com.buinam.schedulemanger.repository.MessageRepository;
import com.buinam.schedulemanger.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;


    @MessageMapping("/message") //destination for sending to all /app/message
    //for those subscribing to /chatroom/public
    @SendTo("/chatroom/public")
    public MessageDTO receiveMessage(@Payload MessageDTO messagePayload){
        System.out.println(messagePayload.toString());
        String status = String.valueOf(messagePayload.getStatus());
        if (Objects.equals(status, "JOIN")) {
            System.out.println(messagePayload.getSenderName()+ " joined the chat");
            System.out.println("now we can do some chat db clean up");
        }
        return messagePayload;
    }

    @MessageMapping("/private-message") //destination for sending to specific user: /app/private-message
    public void recMessage(@Payload MessageDTO messagePayload){

        //check if the chat between the two users is already exists
        Optional<Chat> messageOptional = Optional.ofNullable(chatRepository.findExistedChat(messagePayload.getSenderName(), messagePayload.getReceiverName()));

        System.out.println("messageOptionalllllllllllllllll: " + messageOptional);

        Message newMessage = new Message();
        newMessage.setSenderName(messagePayload.getSenderName());
        newMessage.setReceiverName(messagePayload.getReceiverName());
        newMessage.setMessage(messagePayload.getMessage());
        newMessage.setDate(LocalDateTime.now());
        newMessage.setStatus(StatusDTO.valueOf(String.valueOf(messagePayload.getStatus())));

         //if not exists, create a new chat
        if(!messageOptional.isPresent()) {
            Chat savedChat = new Chat();
            savedChat.setUserOne(messagePayload.getSenderName());
            savedChat.setUserTwo(messagePayload.getReceiverName());
            savedChat.setUnReadOne(1L);
            savedChat.setUnReadTwo(0L);

            System.out.println("savedMessage: " + savedChat.toString());
            Chat chatSaved = chatRepository.save(savedChat);

            System.out.println("chatSaved" + chatSaved);
            newMessage.setChatId(chatSaved.getId());

            Message message = messageRepository.save(newMessage);

            System.out.println("message is saved" + message.toString());
        }

//        //if exists, push the new message to the chat
        if(messageOptional.isPresent()) {
            Chat savedChat = messageOptional.get();
            if(messagePayload.getReceiverName().equals(savedChat.getUserOne())) {
                savedChat.setUnReadTwo(savedChat.getUnReadTwo() + 1);
            }
            if(messagePayload.getReceiverName().equals(savedChat.getUserTwo())) {
                savedChat.setUnReadOne(savedChat.getUnReadOne() + 1);
            }
            System.out.println("CHAT DOES EXISTS");
            chatRepository.save(savedChat);
            newMessage.setChatId(messageOptional.get().getId());
            messageRepository.save(newMessage);
        }


        // for those subscribing to /user/{username}/private
        simpMessagingTemplate.convertAndSendToUser(messagePayload.getReceiverName(),"/private",messagePayload);

        System.out.println(messagePayload.toString());
    }
}

/*
 public void convertAndSendToUser(String user, String destination, Object payload, @Nullable Map<String, Object> headers, @Nullable MessagePostProcessor postProcessor) throws MessagingException {
        Assert.notNull(user, "User must not be null");
        Assert.isTrue(!user.contains("%2F"), "Invalid sequence \"%2F\" in user name: " + user);
        user = StringUtils.replace(user, "/", "%2F");
        destination = destination.startsWith("/") ? destination : "/" + destination;
        super.convertAndSend(this.destinationPrefix + user + destination, payload, headers, postProcessor);

        IT MEANS:
                 super.convertAndSend("/user/" + "{receiverName}" + "/private", message, null, null);
    }
 */



