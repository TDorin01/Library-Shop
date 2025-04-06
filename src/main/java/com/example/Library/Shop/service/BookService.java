package com.example.Library.Shop.service;

import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(int id, String title, String author, double price) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAuthor(author);
        book.setTitle(title);
        book.setPrice(price);
        bookRepository.save(book);
    }
}
