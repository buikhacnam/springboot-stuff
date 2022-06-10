package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.SendNotificationDTO;
import com.buinam.schedulemanger.service.PushNotificationService;
import com.buinam.schedulemanger.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;
@RestController
@RequestMapping(path = "api/v1/notification")
public class PushNotificationController {
    @Autowired
    private PushNotificationService pushNotificationService;

    @GetMapping ("/send/all")
    public ResponseEntity<CommonResponse> sendMessageFirebase() {
        try {
            System.out.println("Start push notification");
            SendNotificationDTO sendNotificationDTO = new SendNotificationDTO();
            sendNotificationDTO.setTitle("Notification To All");
            sendNotificationDTO.setContent("Have a nice day everyone");
            sendNotificationDTO.setTopic("all");
            sendNotificationDTO.setData("{keyOne: 1, keyTwo: 2}");
            System.out.println("Start send message " + sendNotificationDTO);
            String result = pushNotificationService.sendMessageFirebase(sendNotificationDTO);
            System.out.println("Done send message");
            return new ResponseEntity<>(
                new CommonResponse(
                    "Push notification successfully",
                    true,
                    result,
                    HttpStatus.OK.value()
                ),
                HttpStatus.OK)
            ;
        } catch (Exception e) {
            return new ResponseEntity<>(
                new CommonResponse(
                    "Send notification failed",
                    false,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR)
            ;
        }
    }

    @PostMapping ("/send")
    public ResponseEntity<CommonResponse> sendIndividualMessageFirebase(@RequestBody SendNotificationDTO sendNotificationDTO) {
        try {
            System.out.println("Start push notification " + sendNotificationDTO);
            String result = pushNotificationService.sendMessageFirebase(sendNotificationDTO);
            System.out.println("Done send message");
            return new ResponseEntity<>(
                new CommonResponse(
                    "Send notification successfully",
                    true,
                    result,
                    HttpStatus.OK.value()
                ),
                HttpStatus.OK)
            ;
        } catch (Exception e) {
            return new ResponseEntity<>(
                new CommonResponse(
                    "Send notification failed",
                    false,
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR)
            ;
        }
    }


    @GetMapping ("/send/to/{userName}")
    public ResponseEntity<CommonResponse> sendDefaultIndividualMessageFirebase(@PathVariable(name = "userName") String userName) {
        try {
            System.out.println("Start push notification");
            SendNotificationDTO sendNotificationDTO = new SendNotificationDTO();
            sendNotificationDTO.setTo(userName);
            sendNotificationDTO.setTitle("Notification To " + userName);
            sendNotificationDTO.setContent("Have a nice day, " + userName);
            sendNotificationDTO.setData("{keyOne: 1, keyTwo: 2}");
            System.out.println("Start send message " + sendNotificationDTO);
            String result = pushNotificationService.sendMessageFirebase(sendNotificationDTO);
            System.out.println("Done send message");
            return new ResponseEntity<>(
                    new CommonResponse(
                            "Push notification successfully",
                            true,
                            result,
                            HttpStatus.OK.value()
                    ),
                    HttpStatus.OK)
                    ;
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new CommonResponse(
                            "Send notification failed",
                            false,
                            e.getMessage(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR)
                    ;
        }
    }


}
