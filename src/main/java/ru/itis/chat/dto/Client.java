package ru.itis.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class Client {
    private UserDto userDto;
    private WebSocketSession session;
}
