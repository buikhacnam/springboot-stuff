package com.buinam.schedulemanger.controller;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message") //destination for sending to all /app/message
    //for those subscribing to /chatroom/public
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }

    @MessageMapping("/private-message") //destination for sending to specific user: /app/private-message
    public Message recMessage(@Payload Message message) {
        // for those subscribing to /user/{username}/private
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;
}

enum Status {
    JOIN,
    MESSAGE,
    LEAVE
}