package com.example.Library.Shop.controller;
import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.User;
import com.example.Library.Shop.model.dto.BookApiDto;
import com.example.Library.Shop.repository.BookRepository;
import com.example.Library.Shop.service.BookApiService;
import com.example.Library.Shop.service.BookService;
import com.example.Library.Shop.service.MyUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final BookApiService bookApiService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        List<Book> bookList = bookService.getAllBooks();
        model.addAttribute("bookList", bookList);

        if (authentication != null) {
            MyUser myUser = (MyUser) authentication.getPrincipal();
            User user = myUser.getUser();
            model.addAttribute("loggedInUser", user.getUsername());
            model.addAttribute("userId", user.getId());
            model.addAttribute("userRole", user.getRole());
        }
        return "homePageForm";
    }

    @GetMapping("/cart")
    public String getCart(HttpSession session, Model model) {
        List<Book> cart = (List<Book>) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", bookService.calculateTotalBooksPrice(cart));
        return "cart/cartForm";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("bookId") int bookId,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

        List<Book> cart = (List<Book>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(book);
        session.setAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("success", "Cartea a fost adăugată în coș.");
        return "redirect:/";
    }

    @GetMapping("/api/search")
    public String searchBooks(@RequestParam String title, Model model) {
        List<BookApiDto> apiList = bookApiService.searchRomanianBooksByTitle(title);
        model.addAttribute("apiList", apiList);
        return "api/response"; // Sau alt view dacă ai redenumit
    }



}
