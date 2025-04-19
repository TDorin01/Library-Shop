package com.example.Library.Shop.controller;

import com.example.Library.Shop.model.dto.UserCreateDto;
import com.example.Library.Shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String registerUser(@Valid @ModelAttribute UserCreateDto userCreateDto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("userCreateDto", userCreateDto);
            return "user/registerUserForm";
        }

        if (userService.findByEmail(userCreateDto.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.email", "Emailul tău a fost deja înregistrat!");
            model.addAttribute("userCreateDto", userCreateDto);
            return "user/registerUserForm";
        }

        if (!userCreateDto.getPassword().equals(userCreateDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Parolele nu se potrivesc!");
            model.addAttribute("userCreateDto", userCreateDto);
            return "user/registerUserForm";
        }

        userService.createUser(userCreateDto.mapToUser());
        return "redirect:/?success=registered";
    }


    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "user/loginUser";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String username) {
        userService.deleteUserByUsername(username);
        return "redirect:/connectedUsers";
    }
}

