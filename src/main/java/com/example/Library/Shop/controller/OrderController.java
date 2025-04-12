package com.example.Library.Shop.controller;

import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.Orders;
import com.example.Library.Shop.model.Users;
import com.example.Library.Shop.repository.OrderRepository;
import com.example.Library.Shop.repository.UserRepository;
import com.example.Library.Shop.service.BookService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class OrderController {
 private final OrderRepository orderRepository;
 private final UserRepository userRepository;
 private final BookService bookService;

    @GetMapping("/orders/{id}")
    public String showOrderDetails(@PathVariable int id, Model model) {
        Orders order = orderRepository.findById(id);
        model.addAttribute("order", order);
        return "order/orderDetailsForm";
    }

    @PostMapping("/order/checkout")
    public String placeOrder(HttpSession session, Model model, Authentication authentication) {
        List<Book> cart = (List<Book>) session.getAttribute("cart");
        Users user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Orders order = new Orders();
        if (order.getBookList() == null) {
            order.setBookList(new ArrayList<>());
        }
        order.setBookList(
                order.getBookList()
                        .stream()
                        .distinct()
                        .collect(Collectors.toList())
        );
        order.setOrderUsername(user.getName());
        order.setLocalDateTime(LocalDateTime.now());
        order.setTotalPrice(bookService.calculateTotalBooksPrice(cart));
        order.setEmail(user.getEmail());
        order.setAddress(user.getAddress());
        Orders savedOrder = orderRepository.save(order);
        session.removeAttribute("cart");
        return "redirect:/orders/" + savedOrder.getId();
    }

    @GetMapping("/orderSucced")
    public String orderSucceded (){

        return "order/orderSuccedForm";
    }
    @PostMapping("/cart/delete")
    public String removeFromCart(@RequestParam("bookId") Long bookId, HttpSession session) {
        List<Book> cart = (List<Book>) session.getAttribute("cart");

        if (cart != null) {
            cart.removeIf(book -> book.getId()==bookId);
        }
        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

}
