package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.FcmSubscribeDTO;
import com.buinam.schedulemanger.model.UserDevice;
import com.buinam.schedulemanger.repository.UserDeviceRepository;
import com.buinam.schedulemanger.utils.HeaderRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDeviceServiceImpl implements UserDeviceService {

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Value("${firebase.fcm.serverkey}")
    private String fcmServerKey;


    @Override
    public UserDevice saveUserDevice(UserDevice userDevice) {
        UserDevice userGadget = userDeviceRepository.findByUserNameAndDeviceType(userDevice.getUserName(), userDevice.getDeviceType());

        if (userGadget == null) {
            return userDeviceRepository.save(userDevice);
        } else {
            //update fcm token
            userGadget.setFcmToken(userDevice.getFcmToken());
            return userDeviceRepository.save(userGadget);

        }
    }

    @Override
    public void subscribe(FcmSubscribeDTO fcmSubscribeDTO) {
        RestTemplate restTemplate = new RestTemplate();
        List<HeaderRequestInterceptor> lstHeaderRequest = new ArrayList<>();
        lstHeaderRequest.add(new HeaderRequestInterceptor("Authorization", "key=" + fcmServerKey));
        String url = "https://iid.googleapis.com/iid/v1/" + fcmSubscribeDTO.getFcmToken() + "/rel/topics/" + fcmSubscribeDTO.getTopic();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(lstHeaderRequest);
        restTemplate.setInterceptors(interceptors);

        String firebaseRes = restTemplate.postForObject(url, null, String.class);
        System.out.println("firebaseRes: " + firebaseRes);
    }
}
/*
In node.js
function subscribeTokenToTopic(token, topic) {
  fetch('https://iid.googleapis.com/iid/v1/'+token+'/rel/topics/'+topic, {
    method: 'POST',
    headers: new Headers({
      'Authorization': 'key='+fcm_server_key
    })
  }).then(response => {
    if (response.status < 200 || response.status >= 400) {
      throw 'Error subscribing to topic: '+response.status + ' - ' + response.text();
    }
    console.log('Subscribed to "'+topic+'"');
  }).catch(error => {
    console.error(error);
  })
}

 */