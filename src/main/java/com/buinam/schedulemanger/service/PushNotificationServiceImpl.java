package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.model.UserDevice;
import com.buinam.schedulemanger.repository.UserDeviceRepository;
import com.google.common.base.Strings;
import com.buinam.schedulemanger.dto.SendNotificationDTO;
import com.buinam.schedulemanger.utils.HeaderRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Transactional
@Service
public class PushNotificationServiceImpl implements PushNotificationService {

    @Value("${firebase.fcm.url}")
    private String fcmUrl;
    @Value("${firebase.fcm.serverkey}")
    private String fcmServerKey;

    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Override
    @Async
    @Transactional
    public String sendMessageFirebase(SendNotificationDTO sendNotificationDTO) {
        List<HeaderRequestInterceptor> lstHeaderRequest = new ArrayList<>();
        lstHeaderRequest.add(new HeaderRequestInterceptor("Authorization", "key=" + fcmServerKey));
        lstHeaderRequest.add(new HeaderRequestInterceptor("Content-Type", "application/json; charset=UTF-8"));
        lstHeaderRequest.add(new HeaderRequestInterceptor("Accept-Charset", "UTF-8"));

        try {
            String topic = sendNotificationDTO.getTopic();
            if(!Strings.isNullOrEmpty(topic)){
                // send to all devices
                if (!topic.contains("/topics/")) {
                    topic = "/topics" + "/" + topic;
                    HttpEntity<String> request = buildContent(topic, sendNotificationDTO.getTitle(), sendNotificationDTO.getContent(), sendNotificationDTO.getData());
                    System.out.println("START SEND FIREBASE MESSAGE TO ALL:  "  + request);
                    CompletableFuture<String> pushNotification = send(request, lstHeaderRequest);
//                    CompletableFuture.allOf(pushNotification).join();
                    String firebaseResponse = pushNotification.get();
                    System.out.println("END SEND FIREBASE MESSAGE TO ALL: " +  firebaseResponse);
                }
            } else {
                // TODO: send to a list of specific devices
                List<UserDevice> userDevices = userDeviceRepository.findAllByUserName(sendNotificationDTO.getTo());
                System.out.println("userDevices: " + userDevices);
                for (UserDevice userDevice : userDevices) {
                    HttpEntity<String> request = buildContent(userDevice.getFcmToken(), sendNotificationDTO.getTitle(), sendNotificationDTO.getContent(), sendNotificationDTO.getData());
                    System.out.println("START SEND FIREBASE MESSAGE TO ONE:  " + request);
                    CompletableFuture<String> pushNotification = send(request, lstHeaderRequest);
                    System.out.println("DONE SEND FIREBASE MESSAGE TO ONE");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    private HttpEntity<String> buildContent(String fcmToken, String title, String content, String data) throws Exception {
        JSONObject body = new JSONObject();
        body.put("to", fcmToken);

        JSONObject notification = new JSONObject();
        notification.put("title", new String(title.getBytes(StandardCharsets.UTF_8), "UTF-8"));
        notification.put("body", new String(content.getBytes(StandardCharsets.UTF_8), "UTF-8"));
//        notification.put("title", URLEncoder.encode(title, StandardCharsets.UTF_8.toString()));
//        notification.put("body", URLEncoder.encode(content, StandardCharsets.UTF_8.toString()));
//        notification.put("action", appConfig.getNotificationAction());
//        notification.put("sound", appConfig.getNotificationSound());
        try {
            JSONObject dataJson = new JSONObject(data);
            body.put("notification", notification);
            body.put("data", dataJson);
        } catch (Exception e) {
            log.error("Error convert dataJSON", e);
        }
        System.out.println("BUILD CONTENT RESULT: "+ body.toString());
        return new HttpEntity<>(body.toString());
    }

    @Async
    private CompletableFuture<String> send(HttpEntity<String> entity, List<HeaderRequestInterceptor> lstHeaderRequest) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        if (lstHeaderRequest != null && !lstHeaderRequest.isEmpty()) {
            ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(lstHeaderRequest);
            restTemplate.setInterceptors(interceptors);
        }
        String firebaseResponse = restTemplate.postForObject(fcmUrl, entity, String.class);
        return CompletableFuture.completedFuture(firebaseResponse);
    }
}
