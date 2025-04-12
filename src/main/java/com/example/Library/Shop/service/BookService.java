package com.example.Library.Shop.service;
import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.Users;
import com.example.Library.Shop.model.dto.BookCreateDto;
import com.example.Library.Shop.model.dto.BookUpdateDto;
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

    public void updateBook(BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.getId())
            .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setAuthor(bookUpdateDto.getAuthor());
        book.setTitle(bookUpdateDto.getTitle());
        book.setPrice(bookUpdateDto.getPrice());
        book.setCategory(bookUpdateDto.getCategory());
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

    public Book createBook(BookCreateDto bookCreateDto) {
        if (bookCreateDto == null) {
            throw new RuntimeException("Datele nu pot fi nule!");
        }
        Book book = new Book();
        book.setAuthor(bookCreateDto.getAuthor());
        book.setTitle(bookCreateDto.getTitle());
        book.setPrice(bookCreateDto.getPrice());
        book.setCategory(bookCreateDto.getCategory());
        book.setImageUrl(bookCreateDto.getImageUrl());
        bookRepository.save(book);

        return book;
    }

    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }
}
