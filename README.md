# Common Services And Tools

# This service powers some public projects including:

## <a href="#pro-messenger-1">Pro messenger</a>

## <a href="#ultimate-calendar-1">Ultimate Calendar</a>

## <a href="#firebase-push-notification-tester-1">Firebase Push Notification Tester</a>

## <a href="#excel-data-importer-and-exporter">Excel Data Importer / Exporter</a>

# Pro Messenger

## Live app: https://chat-to-me.vercel.app/

First, we set up the WebsocketConfig:

```
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private HttpHandshakeInterceptor handshakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("registerStompEndpoints");
        registry.addEndpoint("/ws").addInterceptors(new HttpHandshakeInterceptor()).setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chatroom", "/user");
        registry.setApplicationDestinationPrefixes("/app");
//        /user/{username}/private
        registry.setUserDestinationPrefix("/user");
    }

}

```

Users can connect to the server with the following URL:

```
domainname/ws?tokenWS=${accessToken}
```

Define routes for sending, receiving message in ChatController.java

```
@MessageMapping("/message") //destination for sending to all /app/message
    //for those subscribing to /chatroom/public
    @SendTo("/chatroom/public")
    public MessageDTO receiveMessage(@Payload MessageDTO messagePayload, Principal principal) {
        System.out.println(messagePayload.toString());

        return messagePayload;
    }

    @MessageMapping("/private-message") //destination for sending to specific user: /app/private-message
    public void recMessage(@Payload MessageDTO messagePayload, Principal principal){

        if(!principal.getName().equals(messagePayload.getSenderName())){
            throw new RuntimeException("You are not allowed to send message to other users");
        }

        // for those subscribing to /user/{username}/private
        simpMessagingTemplate.convertAndSendToUser(messagePayload.getReceiverName(),"/private",messagePayload);

        System.out.println(messagePayload.toString());
    }
```

## On the client side:

-   Sending a public message: stompClient.send("/app/message", {}, JSON.stringify(chatMessage));
-   Sending a private message: stompClient.send("/app/private-message", {}, JSON.stringify(chatMessage));
-   Receiving a public message: stompClient.subscribe("/chatroom/public", function(message) {
    console.log(message);
    });
-   Receiving a private message: stompClient.subscribe("/user/{username}/private", function(message) {
    console.log(message);
    });

# Ultimate Calendar

## Live app: https://i-calender.vercel.app/

# Firebase Push Notification Tester

## Live app: https://fcm-topic.vercel.app/

## Push notitication to specific user

The purpose of this service is to test the push notification feature of Firebase.

Our goal is to make a request to the Firebase server with a Cloud Messaging server key and a proper boder payload with \${firebase.fcm.token}is the device token sending from client.

The request will look like this:

```
Host: fcm.googleapis.com
Authorization: key=${firebase.fcm.serverkey}"
Content-Type: application/json
Content-Length: 251

{
		   "notification": {
		      "title": "New Notification",
		      "body": "Hi there!"
		   },
		   "data": {
		      "Key-1": "one",
		      "Key-2": "two"
		   },
		   "to": "${firebase.fcm.token}",
		   "priority": "high"
		}

```

Save token and device type of each user to database

```
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserDevice  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "fcm_token")
    private String fcmToken;
    @Column(name = "device_type")
    private String deviceType;
}
```

After the user login, we will save deviceType and the fcmToken of the user to the database.

We then use this information to send push notification to the user. (check the file PushNotificationServiceImpl.java for details)

## Push notification to all users using web browser

There is no direct API to send push notification to all users using web browser.

So we need a way to work around:

After user log in, we can check if the device is "web", we will subscribe that device token to a topic by run this (check UserDeviceServiceImpl.java for details)
:

```
public void subscribe(FcmSubscribeDTO fcmSubscribeDTO) {
        RestTemplate restTemplate = new RestTemplate();
        List<HeaderRequestInterceptor> lstHeaderRequest = new ArrayList<>();
        lstHeaderRequest.add(new HeaderRequestInterceptor("Authorization", "key=" + fcmServerKey));
        String url = "https://iid.googleapis.com/iid/v1/" + fcmSubscribeDTO.getFcmToken() + "/rel/topics/" + fcmSubscribeDTO.getTopic();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(lstHeaderRequest);
        restTemplate.setInterceptors(interceptors);

        String firebaseRes = restTemplate.postForObject(url, null, String.class);
    }

```

Basically, the request will look like this:

```
POST /iid/v1/${firebase.fcm.token}/rel/topics/${topicName} HTTP/1.1
Host: iid.googleapis.com
Authorization: key=${firebase.fcm.serverkey}
```

So then we can send a nonification to all users who subscribe to a topic named 'all' for example:

```
Host: fcm.googleapis.com
Authorization: key=${firebase.fcm.serverkey}"
Content-Type: application/json
Content-Length: 251

{
		   "notification": {
		      "title": "Notification To All",
		      "body": "Hello everyone!"
		   },
		   "data": {
		      "Key-1": "one",
		      "Key-2": "two"
		   },
		   "to": "/topics/all",
		   "priority": "high"
		}

```

# Excel Data Importer And Exporter

Documentation is in progress...
