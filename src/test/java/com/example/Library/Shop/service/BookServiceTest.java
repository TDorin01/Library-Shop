package com.example.Library.Shop.service;
import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.Order;
import com.example.Library.Shop.model.User;
import com.example.Library.Shop.model.dto.BookCreateDto;
import com.example.Library.Shop.model.dto.BookUpdateDto;
import com.example.Library.Shop.repository.BookRepository;
import com.example.Library.Shop.repository.OrderRepository;
import com.example.Library.Shop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void cleanDatabaseBeforeEachTest() {
        orderRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void createBookWhenDataIsNullShouldThrowException() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.createBook(null));
        assertEquals("Datele nu pot fi nule!", exception.getMessage());
    }

    @Test
    void createBookWhenValidDataShouldCreateBookSuccessfully() {
        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setTitle("Ion");
        bookCreateDto.setAuthor("Liviu Rebreanu");
        bookCreateDto.setPrice(55);
        bookCreateDto.setCategory("copii");
        bookCreateDto.setImageUrl("http://localhost-image.jpg");

        Book createdBook = bookService.createBook(bookCreateDto);

        assertNotNull(createdBook.getId());
        assertEquals("Ion", createdBook.getTitle());
        assertEquals("Liviu Rebreanu", createdBook.getAuthor());
        assertEquals(55, createdBook.getPrice());
        assertEquals("copii", createdBook.getCategory());
        assertEquals("http://localhost-image.jpg", createdBook.getImageUrl());

        bookRepository.deleteById(createdBook.getId());
    }

    @Test
    void getAllBooksShouldReturnAllSavedBooks() {

        BookCreateDto book1 = new BookCreateDto("Book 1", "Mark Twain", 22, "copii", "https://image.jpg");
        BookCreateDto book2 = new BookCreateDto("Book 2", "Hector Malot", 20, "muzica", "https://image2.jpg");

        bookService.createBook(book1);
        bookService.createBook(book2);

        List<Book> allBooks = bookService.getAllBooks();

        assertEquals(2, allBooks.size());
        assertTrue(allBooks.stream().anyMatch(book -> book.getTitle().equals("Book 1")));
        assertTrue(allBooks.stream().anyMatch(book -> book.getTitle().equals("Book 2")));
    }

    @Test
    void saveBookShouldSaveBookWithUser() {
        User user = new User();
        user.setUsername("user2");
        user.setPassword("12345678");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        Book book = new Book();
        book.setTitle("Amintiri din copilărie");
        book.setAuthor("Ion Creangă");
        book.setPrice(40);
        book.setCategory("copii");
        book.setImageUrl("https://imagine.jpg");

        bookService.saveBook(book, user.getId());

        List<Book> savedBooks = bookRepository.findByUser(user);
        assertEquals(1, savedBooks.size());
        Book savedBook = savedBooks.get(0);

        assertEquals("Amintiri din copilărie", savedBook.getTitle());
        assertEquals("Ion Creangă", savedBook.getAuthor());
        assertEquals(40, savedBook.getPrice());
        assertEquals("copii", savedBook.getCategory());
        assertEquals("https://imagine.jpg", savedBook.getImageUrl());
        assertEquals(user.getId(), savedBook.getUser().getId());
    }

    @Test
    void findBooksByUserShouldReturnCorrectBooks() {

        User user = new User();
        user.setUsername("user3");
        user.setPassword("12345678");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Marin Preda");
        book1.setPrice(35);
        book1.setCategory("copii");
        book1.setImageUrl("https://image1.jpg");
        book1.setUser(user);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Mihail Sadoveanu");
        book2.setPrice(30);
        book2.setCategory("copii");
        book2.setImageUrl("https://image2.jpg");
        book2.setUser(user);

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> foundBooks = bookService.findBooksByUser(user);

        assertEquals(2, foundBooks.size());
        assertTrue(foundBooks.stream().anyMatch(b -> b.getTitle().equals("Book 1")));
        assertTrue(foundBooks.stream().anyMatch(b -> b.getTitle().equals("Book 2")));
    }

    @Test
    void calculateTotalBooksPriceShouldReturnCorrectSumForMultipleBooks() {
        Book book1 = new Book();
        book1.setPrice(40);

        Book book2 = new Book();
        book2.setPrice(60);

        Book book3 = new Book();
        book3.setPrice(50);

        List<Book> bookList = List.of(book1, book2, book3);
        double result = bookService.calculateTotalBooksPrice(bookList);

        assertEquals(150, result, 0.001);
    }

    @Test
    void calculateTotalBooksPriceShouldReturnZeroForEmptyList() {
        List<Book> emptyList = List.of();
        double result = bookService.calculateTotalBooksPrice(emptyList);
        assertEquals(0, result, 0.001);
    }

    @Test
    void calculateTotalBooksPriceShouldReturnZeroForNullList() {
        double result = bookService.calculateTotalBooksPrice(null);
        assertEquals(0, result, 0.001);
    }

    @Test
    void calculateTotalBooksPriceShouldReturnOneSinglePriceWhenOnlyOneBook() {
        Book book = new Book();
        book.setPrice(10);
        List<Book> bookList = List.of(book);

        double result = bookService.calculateTotalBooksPrice(bookList);
        assertEquals(10, result, 0.001);
    }

    @Test
    void getBooksByCategoryShouldReturnBooksInThatCategory() {

        User user = new User();
        user.setUsername("user4");
        user.setPassword("12345678");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor("Ion Creangă");
        book1.setPrice(40.0);
        book1.setCategory("copii");
        book1.setImageUrl("https://imagine.jpg");
        book1.setUser(user);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor("Mihail Sadoveanu");
        book2.setCategory("copii");
        book2.setPrice(35.0);
        book2.setImageUrl("https://image2.jpg");
        book2.setUser(user);
        bookRepository.save(book2);

        List<Book> books = bookService.getBooksByCategory("copii");

        assertEquals(2, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Book 1")));
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Book 2")));
    }

    @Test
    void findById_shouldReturnBookIfExists() {

        User user = new User();
        user.setUsername("user1");
        user.setPassword("12345678");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        Book book = new Book();
        book.setTitle("Amintiri din copilărie");
        book.setAuthor("Ion Creangă");
        book.setPrice(40);
        book.setCategory("copii");
        book.setImageUrl("https://imagine.jpg");
        book.setUser(user);
        book = bookRepository.save(book);

        Optional<Book> found = bookService.findById(book.getId());

        assertTrue(found.isPresent(), "Book find by ID.");
        assertEquals(book.getTitle(), found.get().getTitle());
        assertEquals(book.getAuthor(), found.get().getAuthor());
    }

    @Test
    void updateBookShouldUpdateBookFields() {

        User user = new User();
        user.setUsername("user");
        user.setPassword("123456");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        Book book = new Book();
        book.setTitle("Colt Alb");
        book.setAuthor("Jack London");
        book.setPrice(15);
        book.setCategory("copii");
        book.setImageUrl("https://img1.jpg");
        book.setUser(user);

        book = bookRepository.save(book);

        BookUpdateDto updateDto = new BookUpdateDto();
        updateDto.setId(book.getId());
        updateDto.setTitle("Sambo se intoarce");
        updateDto.setAuthor("Paul Maar");
        updateDto.setPrice(28);
        updateDto.setCategory("promotii");
        updateDto.setImageUrl("https://img2.jpg");

        bookService.updateBook(updateDto);
        Book updatedBook = bookRepository.findById(book.getId()).orElseThrow();

        assertEquals("Sambo se intoarce", updatedBook.getTitle());
        assertEquals("Paul Maar", updatedBook.getAuthor());
        assertEquals(28, updatedBook.getPrice());
        assertEquals("promotii", updatedBook.getCategory());
        assertEquals("https://img2.jpg", updatedBook.getImageUrl());
    }
    @Transactional
    @Test
    void deleteBookShouldRemoveBookFromOrders() {

        User user = new User();
        user.setUsername("use1");
        user.setPassword("user123");
        user.setEmail("user1@email.com");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        Book book = new Book();
        book.setTitle("Book 1");
        book.setAuthor("Author");
        book.setPrice(20);
        book.setCategory("copii");
        book.setUser(user);
        book = bookRepository.save(book);

        Order order = new Order();
        order.setEmail("order1@email.com");
        order.setAddress("Cluj ,strada 1");
        order.setTotalPrice(20);
        order.setLocalDateTime(java.time.LocalDateTime.now());
        order.setBookList(new ArrayList<>(List.of(book)));
        order = orderRepository.save(order);

        bookService.deleteBook(book.getId());

        Optional<Book> deletedBook = bookRepository.findById(book.getId());
        assertTrue(deletedBook.isEmpty(), "Book deleted from repository.");

        Order updatedOrder = orderRepository.findById(order.getId()).orElseThrow();
        assertFalse(updatedOrder.getBookList().contains(book), "Book deleted from order.");
    }
}
