package ru.itis.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.chat.dto.UserDto;
import ru.itis.chat.service.UserService;


@Controller
public class RegisterController {
    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegister() {
        return "/register";
    }

    @PostMapping("/register")
    public String register(UserDto userDto) {
        userService.register(userDto);
        if (userDto.getId() != null) {
            return "/register";
        }
        return "/login";
    }
}
