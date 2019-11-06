package com.lnjecit.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author lnj
 * @description WebSocket配置
 * @date 2019-11-01 10:25
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * 注册 Stomp的端点
     * addEndpoint：添加STOMP协议的端点。这个HTTP URL是供WebSocket或SockJS客户端访问的地址
     * setAllowedOrigins：添加允许跨域访问
     * withSockJS：指定端点使用SockJS协议
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/webSocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * 配置消息代理
     * 启动简单Broker，消息的发送的地址符合配置的前缀来的消息才发送到这个broker
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
    }

}
