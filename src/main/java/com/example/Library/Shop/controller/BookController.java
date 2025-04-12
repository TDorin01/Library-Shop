package com.example.Library.Shop.controller;
import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.Orders;
import com.example.Library.Shop.model.Users;
import com.example.Library.Shop.model.dto.BookCreateDto;
import com.example.Library.Shop.model.dto.BookUpdateDto;
import com.example.Library.Shop.repository.BookRepository;
import com.example.Library.Shop.repository.OrderRepository;
import com.example.Library.Shop.repository.UserRepository;
import com.example.Library.Shop.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/admin/listBooks")
    public String getUsersBooks(Model model, Authentication authentication) {
        Users user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("No user find"));
        List<Book> bookList = bookService.findBooksByUser(user);
        model.addAttribute("totalPrice", bookService.calculateTotalBooksPrice(bookList));
        model.addAttribute("bookList", bookList);
        return "listBooks";
    }

    @GetMapping("/admin/adminForm")
    public String getAdminFormView() {
        return "adminForm";
    }

    @GetMapping("/createBookForm")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createBook(Model model) {
        model.addAttribute("book",new Book());
        return "createBook";
    }

    @PostMapping("/saveBook")
    public String savebook(@ModelAttribute Book book, Authentication authentication) {
        Users user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("No user find"));
        bookService.saveBook(book, user.getId());
        return "redirect:/admin/listBooks";
    }

    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute BookUpdateDto bookUpdateDto) {
        bookService.updateBook(bookUpdateDto);
        return "redirect:/admin/listBooks";
    }

    @GetMapping("/updateForm")
    public String getUpdateForm(@RequestParam("id") int id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        model.addAttribute("book", book);
        return "updateBookForm";
    }

    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        List<Orders> orders = orderRepository.findAllByBookListContaining(book);

        for (Orders order : orders) {
            order.getBookList().remove(book);
            orderRepository.save(order);
        }
        bookRepository.delete(book);
        return "redirect:/admin/listBooks";
    }

    @GetMapping("/bookCategory")
    public String getBookByCategory(Model model, @RequestParam String category) {
        List<Book> bookList = bookRepository.findByCategory(category);
        model.addAttribute("bookCategoryList", bookList);
        return "bookCategoryForm";
    }

}