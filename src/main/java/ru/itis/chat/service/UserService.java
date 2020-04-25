package ru.itis.chat.service;



import ru.itis.chat.dto.UserDto;

import java.util.Optional;

public interface UserService {
    void register(UserDto userDto);

    void login(UserDto userDto);

    Optional<UserDto> checkToken(String token);

    Optional<UserDto> findUserById(Long id);
}
