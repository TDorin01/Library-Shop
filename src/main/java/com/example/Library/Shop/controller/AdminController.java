package com.example.Library.Shop.controller;
import com.example.Library.Shop.model.Users;
import com.example.Library.Shop.model.dto.SalesStatisticDto;
import com.example.Library.Shop.repository.UserRepository;
import com.example.Library.Shop.service.OrderService;
import com.example.Library.Shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderService orderService;

    @GetMapping("/connectedUsers")
    public String getConnectedUsers(Model model) {
        Users user = userRepository.findByUsername("admin").orElseThrow(() -> new RuntimeException("Admin not found."));
        List<Users> connectedUsers = userRepository.findAll();
        if (connectedUsers.contains(user)) {
            connectedUsers.remove(user);
        }
        model.addAttribute("connectedUsers", connectedUsers);
        return "adminViewAllUsers";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam String username) {
        userService.deleteUserByUsername(username);
        return "redirect:/connectedUsers";
    }
    @GetMapping("/admin/salesStats")
    public String viewSalesStats(Model model) {
        List<SalesStatisticDto> stats = orderService.getMonthlyStats();
        model.addAttribute("stats", stats);
        return "salesStatsForm";
    }

}
