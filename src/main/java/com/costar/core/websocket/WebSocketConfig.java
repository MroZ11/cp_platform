package com.costar.core.websocket;

import com.costar.core.utils.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.thymeleaf.extras.springsecurity5.util.SpringSecurityContextUtils;

import java.security.Principal;

/**
 * Created by cloud on 2018/4/3.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册STOMP协议终端
        //STOMP即Simple (or Streaming) Text Orientated Messaging Protocol，
        // 简单(流)文本定向消息协议，它提供了一个可互操作的连接格式，允许STOMP客户端与任意STOMP消息代理（Broker）进行交互。
        // STOMP协议由于设计简单，易于开发客户端，因此在多种语言和多种平台上得到广泛地应用。
        //setAllowedOrigins很关键，当上线到服务器是应该配置成服务器地址，或者*，否则nginx代理后无法跨域一直403
        registry.addEndpoint(WsConfig.END_POINT).setAllowedOrigins("*").withSockJS();//核定socketjs协议
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置消息代理
        registry.enableSimpleBroker(WsConfig.DESTINATION_PREFIXES);
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user
        registry.setUserDestinationPrefix(WsConfig.USER_DESTINATION_PREFIX);
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Nullable
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
                if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
                    Principal principal = headerAccessor.getUser();
                    // principal is null here
                }
                return message;
            }

            @Override
            public boolean preReceive(MessageChannel channel) {
                return true;
            }
        });
    }
}
