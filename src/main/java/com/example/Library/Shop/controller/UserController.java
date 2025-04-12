package com.example.Library.Shop.controller;

import com.example.Library.Shop.model.dto.UserCreateDto;
import com.example.Library.Shop.model.Users;
import com.example.Library.Shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/registerForm")
    public String displayRegisterForm(Model model) {
        model.addAttribute("userCreateDto", new UserCreateDto());
        return "user/registerUserForm";
    }

    @PostMapping("/registerUser")
    public String registerUser(@Valid @ModelAttribute UserCreateDto userCreateDto, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userCreateDto", userCreateDto);
            return "user/registerUserForm";
        }
        else if(userService.findByEmail(userCreateDto.getEmail()).isPresent()){
            bindingResult.rejectValue("email", "error.email", "Emailul tau a fost deja inregistrat!");
            return "user/registerUserForm";
        }

        else if (!(userCreateDto.getPassword().equals(userCreateDto.getConfirmPassword()))) {
            bindingResult.rejectValue("verifypassword", "error.verifypassword", "Parolele nu se potrivesc!");
            return "user/registerUserForm";
}
        userService.createUser(userCreateDto.mapToUser());
        return "redirect:/";
    }

    @GetMapping("/loginForm")
    public String getLoginForm(Model model) {
    return "user/loginUser";
    }

}

