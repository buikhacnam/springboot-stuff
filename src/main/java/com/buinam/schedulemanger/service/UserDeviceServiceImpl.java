package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.model.UserDevice;
import com.buinam.schedulemanger.repository.UserDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserDeviceServiceImpl implements UserDeviceService {

    @Autowired
    private UserDeviceRepository userDeviceRepository;


    @Override
    public UserDevice saveUserDevice(UserDevice userDevice) {
        UserDevice userGadget = userDeviceRepository.findByUserNameAndDeviceType(userDevice.getUserName(), userDevice.getDeviceType());

        System.out.println("userGadget: " + userGadget);
        if (userGadget == null) {
            return userDeviceRepository.save(userDevice);
        } else {
            //update fcm token
            userGadget.setFcmToken(userDevice.getFcmToken());
            return userDeviceRepository.save(userGadget);

        }
    }
}
