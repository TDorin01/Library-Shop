package com.example.Library.Shop.service;

import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.Users;
import com.example.Library.Shop.repository.BookRepository;
import com.example.Library.Shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public void saveBook(Book book, int userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        book.setUser(user);
        bookRepository.save(book);
    }

    public void updateBook(int id, String title, String author, double price) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAuthor(author);
        book.setTitle(title);
        book.setPrice(price);
        bookRepository.save(book);
    }

    public List<Book> findBooksByUser(Users user) {
        return bookRepository.findByUser(user);
    }
    public double calculateTotalBooksPrice(List<Book>bookList ){
        if (bookList == null || bookList.isEmpty()) {
            return 0.0;}
        return bookList.stream()
                .mapToDouble(Book::getPrice)
                .sum();
    }
}
