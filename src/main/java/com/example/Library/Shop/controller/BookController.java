package com.example.Library.Shop.controller;
import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.User;
import com.example.Library.Shop.model.dto.BookUpdateDto;
import com.example.Library.Shop.repository.BookRepository;
import com.example.Library.Shop.repository.UserRepository;
import com.example.Library.Shop.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @GetMapping("/admin/listBooks")
    public String getUsersBooks(Model model, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("No user find"));
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
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        return "createBook";
    }

    @PostMapping("/saveBook")
    public String saveBook(@ModelAttribute Book book,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        bookService.saveBook(book, user.getId());

        redirectAttributes.addFlashAttribute("successMessage", " Cartea a fost salvată cu succes!");
        return "redirect:/admin/listBooks";
    }


    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute BookUpdateDto bookUpdateDto,
                             RedirectAttributes redirectAttributes) {
        try {
            bookService.updateBook(bookUpdateDto);
            redirectAttributes.addFlashAttribute("successMessage", " Cartea a fost actualizată cu succes!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", " Eroare la actualizarea cărții.");
        }

        return "redirect:/admin/listBooks";
    }


    @GetMapping("/updateForm")
    public String getUpdateForm(@RequestParam("id") int id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(book.getId());
        bookUpdateDto.setAuthor(book.getAuthor());
        bookUpdateDto.setTitle(book.getTitle());
        bookUpdateDto.setPrice(book.getPrice());
        bookUpdateDto.setCategory(book.getCategory());
        bookUpdateDto.setImageUrl(book.getImageUrl());

        model.addAttribute("book", bookUpdateDto);
        return "updateBookForm";
    }


    @GetMapping("/bookCategory")
    public String getBookByCategory(Model model, @RequestParam String category, Authentication authentication) {
        List<Book> bookList = bookRepository.findByCategory(category);
        model.addAttribute("bookCategoryList", bookList);

        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("loggedInUser", user.getUsername());
        }

        return "bookCategoryForm";
    }
    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("successMessage", " Cartea a fost ștearsă cu succes!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", " Nu s-a putut șterge cartea.");
        }

        return "redirect:/admin/listBooks";
    }


}