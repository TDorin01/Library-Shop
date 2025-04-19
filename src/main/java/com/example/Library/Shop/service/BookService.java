package com.example.Library.Shop.service;
import com.example.Library.Shop.exception.BookNotFoundException;
import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.Order;
import com.example.Library.Shop.model.User;
import com.example.Library.Shop.model.dto.BookCreateDto;
import com.example.Library.Shop.model.dto.BookUpdateDto;
import com.example.Library.Shop.repository.BookRepository;
import com.example.Library.Shop.repository.OrderRepository;
import com.example.Library.Shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Book createBook(BookCreateDto bookCreateDto) {
        if (bookCreateDto == null) {
            throw new RuntimeException("Datele nu pot fi nule!");
        }
        Book book = bookCreateDto.toBook();
        return bookRepository.save(book);
    }

    public void saveBook(Book book, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        book.setUser(user);
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

    public List<Book> findBooksByUser(User user) {
        return bookRepository.findByUser(user);
    }

    public double calculateTotalBooksPrice(List<Book> bookList) {
        if (bookList == null || bookList.isEmpty()) {
            return 0.0;
        }
        return bookList.stream()
                .mapToDouble(Book::getPrice)
                .sum();
    }


    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }



    public void updateBook(BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(bookUpdateDto.getId())
                .orElseThrow(() -> new BookNotFoundException("Cartea cu ID-ul " + bookUpdateDto.getId() + " nu a fost găsită"));

        book.setTitle(bookUpdateDto.getTitle());
        book.setAuthor(bookUpdateDto.getAuthor());
        book.setPrice(bookUpdateDto.getPrice());
        book.setCategory(bookUpdateDto.getCategory());
        book.setImageUrl(bookUpdateDto.getImageUrl());

        bookRepository.save(book);
    }


    public void deleteBook(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Cartea cu ID-ul " + id + " nu există."));
        List<Order> orders = orderRepository.findAllByBookListContaining(book);

        for (Order order : orders) {
            order.getBookList().remove(book);
            orderRepository.save(order);
        }

        bookRepository.delete(book);
    }
    public Page<Book> getBooksPage(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


}
