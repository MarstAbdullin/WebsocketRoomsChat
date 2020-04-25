package ru.itis.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itis.chat.dto.UserDto;
import ru.itis.chat.service.UserService;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletResponse response, UserDto userDto) {
        userService.login(userDto);
        if (userDto.getId() != null) {
            response.addCookie(new Cookie("token", userDto.getToken()));
            return "redirect:/";
        }
        return "redirect:/login";
    }
}
