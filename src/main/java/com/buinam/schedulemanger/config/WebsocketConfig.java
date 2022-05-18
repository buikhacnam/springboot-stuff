package com.buinam.schedulemanger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

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

/*
setUserDestinationPrefix
public MessageBrokerRegistry setUserDestinationPrefix(String destinationPrefix)
Configure the prefix used to identify user destinations. User destinations provide the ability for a user to subscribe to queue names unique to their session as well as for others to send messages to those unique, user-specific queues.
For example when a user attempts to subscribe to "/user/queue/position-updates", the destination may be translated to "/queue/position-updatesi9oqdfzo" yielding a unique queue name that does not collide with any other user attempting to do the same. Subsequently when messages are sent to "/user/{username}/queue/position-updates", the destination is translated to "/queue/position-updatesi9oqdfzo".

The default prefix used to identify such destinations is "/user/".


 */