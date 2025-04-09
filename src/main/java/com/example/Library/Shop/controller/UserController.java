package com.example.Library.Shop.controller;

import com.example.Library.Shop.model.UserCreateDto;
import com.example.Library.Shop.model.Users;
import com.example.Library.Shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/registerForm")
    public String displayRegisterForm(Model model) {
        model.addAttribute("user", new Users());
        return "user/registerUserForm";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto.mapToUser());
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String getLoginForm(Model model) {

        return "user/loginUser";
    }

}

