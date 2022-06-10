# Common Services And Tools

# This service powers some public projects including:

## [Pro Messenger](https://chat-to-me.vercel.app/)

## [Ultimate Calendar](https://i-calender.vercel.app/)

## [Firebase Push Notification Tester](https://fcm-topic.vercel.app/)

## [Excel Importer / Exporter](#)


# Pro Messenger
Documentation is in progress...
# Ultimate Calendar
Documentation is in progress...
# Firebase Push Notification Tester

## Push notitication to specific user

The purpose of this service is to test the push notification feature of Firebase.

Our goal is to make a request to the Firebase server with a Cloud Messaging server key and a proper boder payload with ${firebase.fcm.token}is the device token sending from client.

The request will look like this: 
```
Host: fcm.googleapis.com
Authorization: key=${firebase.fcm.serverkey}"
Content-Type: application/json
Content-Length: 251

{
		   "notification": {
		      "title": "JSA Notification",
		      "body": "Happy Message!"
		   },
		   "data": {
		      "Key-1": "JSA Data 1",
		      "Key-2": "JSA Data 2"
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

After user log in, we can check if the device is "web", we will subscribe that device token to a topic by run this:

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


# Excel Importer / Exporter
Documentation is in progress...