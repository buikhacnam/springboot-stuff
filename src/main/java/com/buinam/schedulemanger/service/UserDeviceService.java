package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.FcmSubscribeDTO;
import com.buinam.schedulemanger.model.UserDevice;

public interface UserDeviceService {
   UserDevice saveUserDevice(UserDevice userDevice);

   void subscribe(FcmSubscribeDTO fcmSubscribeDTO);
}
