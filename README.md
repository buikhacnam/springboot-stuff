# Common Services And Tools
This repository is the backend service for some of my personal projects and also a collection of common tools that I use in my daily work.
I think it's nice to have all of them in one place, so that I can easily share / find them when needed.
# This service powers some projects/tools including:


## <a href="#spring-security-and-jwt-1">Spring Security And JWT</a>

## <a href="#loggin-via-microsoft-azure">Loggin Via Micorsoft Azure</a>

## <a href="#spring-security-and-LDAP-1">Spring Security And LDAP</a>

## <a href="#microservices-the-demo-1">Microservices the demo</a>

## <a href="#pro-messenger">Websocket Pro messenger</a>

## <a href="#ultimate-calendar-1">Ultimate Calendar</a>

## <a href="#relationship-experiment-1">Relationship Experiment</a>

## <a href="#kafka-1">Kafka</a>

## <a href="#redis-1">Redis</a>

## <a href="#firebase-push-notification-tester-1">Firebase Push Notification Tester</a>

## <a href="#common-techniques-and-tools">Some Other Tools</a>


# Spring Security and JWT

Please check this repository:

https://github.com/buikhacnam/security-jwt-in-action


# Loggin via Microsoft Azure

Please check this repository:

https://github.com/buikhacnam/azure-login

# Spring Security and LDAP

Please check this repository:

https://github.com/buikhacnam/Spring-Security-LDAP

# Microservices the demo

Please check this repository:

https://github.com/buikhacnam/mi

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

In this project, we use javax.persistence.EntityManager to natively query the database for multiple filtering and sorting purposes.
Please check the file ScheduleController and ScheduleService.java for more details.

```
  private LazyLoadDTO executeSearchScheduleByCondition(String pageSize, String pageNumber, String name, LocalDateTime fromDate, LocalDateTime toDate, String description, String location, String textSearch, String orderBy, String orderType, boolean isCount) {
        
        LazyLoadDTO lazyLoadDTO = new LazyLoadDTO();
        List<Object> listParam = new ArrayList<>();
        StringBuilder strQuery = new StringBuilder();


        if(isCount) {
            strQuery.append("SELECT COUNT(*) FROM schedule s WHERE 1=1 ");
        } else {
            strQuery.append("SELECT * FROM schedule s WHERE 1=1 ");
        } 

        if(!Strings.isNullOrEmpty(name)) {
            strQuery.append(" AND name LIKE ? ");
            listParam.add("%" + name + "%");
        }

        if(fromDate != null) {
            strQuery.append(" AND create_date_time >= ? ");
            listParam.add(fromDate);
        }

        if(toDate != null) {
            strQuery.append(" AND create_date_time <= ? ");
            listParam.add(toDate);
        }

        if(!Strings.isNullOrEmpty(description)) {
            strQuery.append(" AND description LIKE ? ");
            listParam.add("%" + description + "%");
        }

        if(!Strings.isNullOrEmpty(location)) {
            strQuery.append(" AND location LIKE ? ");
            listParam.add("%" + location + "%");
        }

        if (!Strings.isNullOrEmpty(textSearch)) {
            strQuery.append(" AND ( s.name LIKE ? OR s.description LIKE ? OR s.location LIKE ? ) ");
            listParam.add("%" + textSearch.trim().toLowerCase() + "%");
            listParam.add("%" + textSearch.trim().toLowerCase() + "%");
            listParam.add("%" + textSearch.trim().toLowerCase() + "%");
        }
        if (Strings.isNullOrEmpty(orderBy)) {
            // if there is no orderBy, then order by start_date
            strQuery.append(" ORDER BY s.update_date_time DESC ");
        } else {
            if (!Strings.isNullOrEmpty(orderType)) {
                // there is orderType, then order by orderBy and orderType
                strQuery.append(" ORDER BY ".concat("s.".concat(orderBy)).concat(" ").concat(orderType));
            } else {
                // there is no orderType, then order by orderBy only
                strQuery.append(" ORDER BY ".concat("s.".concat(orderBy)));
            }
        }
        
        Query query = null;

        if (isCount) {
            query = em.createNativeQuery(strQuery.toString());
        } else {
            query = em.createNativeQuery(strQuery.toString(), Schedule.class);
        }

        for(int i = 0; i < listParam.size(); i++) {
            query.setParameter(i + 1, listParam.get(i));
        }


        if (isCount) {
            BigDecimal count = new BigDecimal((BigInteger) query.getSingleResult());
            lazyLoadDTO.setCount(count);
        } else {
            if(!Strings.isNullOrEmpty(pageSize) && !Strings.isNullOrEmpty(pageNumber)) {
                int page = Integer.parseInt(pageNumber);
                int size = Integer.parseInt(pageSize);
                Pageable pageable = PageRequest.of(page, size);
    
                if(pageable.getPageNumber() - 1 < 0) {
                    query.setFirstResult(0);
                } else {
                    query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
                }
                query.setMaxResults(pageable.getPageSize());
                lazyLoadDTO.setListObject(query.getResultList());
            }
        }

        return lazyLoadDTO;
    }

```

# Relationship Experiment

Please check the repository: https://github.com/buikhacnam/relationship-expreriment

# Kafka

Please check the repository: https://github.com/buikhacnam/kafka-demo

# Redis

Please check the repository: https://github.com/buikhacnam/redis-the-demo

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


# Common techniques and tools

## Search API using Specification, Pageable and Sort

Please check the repository: https://github.com/buikhacnam/filter-specification


## Entity Manager Example
Please check the repository: https://github.com/buikhacnam/entity-manager-example


## Calling external API using Resttemplate

Please check the repository: https://github.com/buikhacnam/youtube-springboot-restemplate


## Using EntityManager to query database
Check file StudentController.java and Student.java for details.

In student Entity:

```
@NamedNativeQuery(name = "getAllEnrolledStudent",
        query = "SELECT * FROM student_enrolled",
        resultSetMapping = "student_enrolled_kkk"
)
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "student_enrolled_kkk",
                classes = {
                        @ConstructorResult(
                                targetClass = StudentEnrolledDTO.class,
                                columns = {
                                        @ColumnResult(name = "subject_id", type = Long.class),
                                        @ColumnResult(name = "student_id", type = Long.class)
                                })
                }
        )
})
public class Student {
....
}
```

Then you the StudentController.java file:

```
            Query query = em.createNativeQuery("SELECT * FROM student_enrolled s WHERE s.student_id = ?", "student_enrolled_kkk");

```

Or you can set up the a BaseResultSet file and target all the tables you want to query:

```
@Entity

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "get_result_one",
                classes = {
                        @ConstructorResult(
                                targetClass = ResultsetDemoOne.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "name", type = String.class)
                                })
                }
        ),
        @SqlResultSetMapping(
                name = "get_result_two",
                classes = {
                        @ConstructorResult(
                                targetClass = ResultsetDemoTwo.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "title", type = String.class)
                                })
                }
        )
})
public class BaseResultSet {
    @Id
    private Long id;
}
```

Then you can query them like this:
```
    @GetMapping("/result-one")
    List<Object> getSomeData() {
        Query query = em.createNativeQuery("SELECT * FROM result_set_demo_one", "get_result_one");
        return query.getResultList();
    }


    @GetMapping("/result-two")
    List<Object> getSomeData2() {
        Query query = em.createNativeQuery("SELECT * FROM result_set_demo_two", "get_result_two");
        return query.getResultList();
    }
```

## Import / export data from excel file to / from database

We're gonna use org.apache.poi for this.

Example exel file: https://docs.google.com/spreadsheets/d/176k61Sevo23G04-gw_xTaz-k1bksD9R0/edit?usp=sharing&ouid=101348996556543503772&rtpof=true&sd=true

Check implementation of PlayerController.java for details.

## @ManyToMany, @OneToMany relationship in JPA

We demo these relationships in Student.java, Subject.java and Teacher.java

Student and Subject are @ManyToMany relationship, that means one student can enroll to many subjects and one subject can be enrolled by many students. Note that we will need to add @JsonIgnore annotation to either Student.java or Subject.java to prevent infinite loop.

Teacher and Subject are @OneToMany relationship, that means one teacher can teach many subjects and one subject is only taught by one teacher.

Check implementation of StudentController.java, SubjectController, TeacherController for details.

