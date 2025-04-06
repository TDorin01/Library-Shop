package com.example.Library.Shop.controller;

import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.repository.BookRepository;
import com.example.Library.Shop.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;

    @GetMapping

    public String getAllBooks(Model model) {
        List<Book> bookList = bookService.getAllBooks();
        model.addAttribute("bookList", bookList);
        return "home";
    }

    @GetMapping("/createBookForm")
    public String createBook(Model model) {
        model.addAttribute("book", new Book());
        return "createBook";
    }

    @PostMapping("/saveBook")
    public String savebook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/";
    }

    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute Book book ,@RequestParam("id")int id){
        bookService.updateBook(id ,book.getTitle(),book.getAuthor(),book.getPrice());
        return "redirect:/";
    }
    @PostMapping("/deleteBook")
    public String deleteBook(@RequestParam("id") int id){
        bookService.deleteBook(id);
        return "redirect:/";
    }
    @GetMapping("/updateForm")
    public String getUpdateForm(@RequestParam("id") int id, Model model) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
        model.addAttribute("book", book);
        return "updateBookForm";
    }

}

