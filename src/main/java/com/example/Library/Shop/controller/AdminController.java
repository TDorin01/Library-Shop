package com.example.Library.Shop.controller;
import com.example.Library.Shop.model.User;
import com.example.Library.Shop.model.dto.SalesStatisticDto;
import com.example.Library.Shop.repository.UserRepository;
import com.example.Library.Shop.service.OrderService;
import com.example.Library.Shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderService orderService;

    @GetMapping("/connectedUsers")
    public String getConnectedUsers(Model model) {
        User user = userRepository.findByUsername("admin").orElseThrow(() -> new RuntimeException("Admin not found."));
        List<User> connectedUsers = userRepository.findAll();
        if (connectedUsers.contains(user)) {
            connectedUsers.remove(user);
        }
        model.addAttribute("connectedUsers", connectedUsers);
        return "adminViewAllUsers";
    }

    @GetMapping("/admin/salesStats")
    public String viewSalesStats(Model model) {
        List<SalesStatisticDto> stats = orderService.getMonthlyStats();
        model.addAttribute("stats", stats);
        return "salesStatsForm";
    }

}
