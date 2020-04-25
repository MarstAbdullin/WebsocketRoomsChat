package ru.itis.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.itis.chat.handler.AuthHandshakeHandler;
import ru.itis.chat.handler.WebSocketActionHandler;


@Configuration
public class WebSocketConfiguration implements WebSocketConfigurer {
    private final AuthHandshakeHandler authHandshakeHandler;
    private WebSocketActionHandler webSocketActionHandler;

    @Autowired
    public WebSocketConfiguration(WebSocketActionHandler webSocketActionHandler, AuthHandshakeHandler authHandshakeHandler) {
        this.webSocketActionHandler = webSocketActionHandler;
        this.authHandshakeHandler = authHandshakeHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(webSocketActionHandler, "/chat")
                .withSockJS()
                .setInterceptors(authHandshakeHandler);
    }
}
