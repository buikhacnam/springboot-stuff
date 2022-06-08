package com.buinam.schedulemanger.controller;

import com.buinam.schedulemanger.dto.FcmSubscribeDTO;
import com.buinam.schedulemanger.model.UserDevice;
import com.buinam.schedulemanger.service.UserDeviceService;
import com.buinam.schedulemanger.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/v1/user-device")
public class UserDeviceController {


    @Autowired
    private UserDeviceService userDeviceService;

    @PostMapping("/save")
    public ResponseEntity<CommonResponse> saveUserDevice(@RequestBody UserDevice userDevice) {
        try {
            UserDevice saved = userDeviceService.saveUserDevice(userDevice);
            String deviceType = saved.getDeviceType();
            if (deviceType.equals("web")) {
                //subscribe user to topic all, since web is not support FCM topic subscription
                FcmSubscribeDTO fcmSubscribeDTO = new FcmSubscribeDTO();
                fcmSubscribeDTO.setTopic("all");
                fcmSubscribeDTO.setFcmToken(saved.getFcmToken());
                userDeviceService.subscribe(fcmSubscribeDTO);
            }
            return new ResponseEntity<>(
                new CommonResponse(
                    "Save user device successfully",
                    true,
                    saved,
                    HttpStatus.OK.value()
                ),
                HttpStatus.OK)
            ;
        } catch (Exception e) {
            return new ResponseEntity<>(
                new CommonResponse(
                    "Save user device failed",
                    false,
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR)
            ;
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<CommonResponse> subscribe(@RequestBody FcmSubscribeDTO fcmSubscribeDTO) {
        try {
            userDeviceService.subscribe(fcmSubscribeDTO);
            return new ResponseEntity<>(
                new CommonResponse(
                    "Subscribe successfully",
                    true,
                        null,
                    HttpStatus.OK.value()
                ),
                HttpStatus.OK)
            ;
        } catch (Exception e) {
            return new ResponseEntity<>(
                new CommonResponse(
                    "Subscribe failed",
                    false,
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR)
            ;
        }
    }
}
