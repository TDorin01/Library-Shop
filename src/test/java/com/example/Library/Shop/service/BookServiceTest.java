package com.example.Library.Shop.service;

import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.dto.BookCreateDto;
import com.example.Library.Shop.repository.BookRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class BookServiceTest {

    BookRepository bookRepository;
    @Autowired
    BookService bookService;

    @Test
    void createBookWhenNoDataExpectException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.createBook(null));
        assertEquals("Datele nu pot fi nule!", exception.getMessage());
    }

    @Test
    void createBookGivenOneDataBookExpectBookCreated() {

        BookCreateDto bookCreateDto = new BookCreateDto();
        bookCreateDto.setTitle("Ion");
        bookCreateDto.setAuthor("Liviu Rebreanu");
        bookCreateDto.setPrice(55);
        bookCreateDto.setCategory("Carti de copii");
        bookCreateDto.setImageUrl("http://localhost-image.jpg");

        Book createdBook = bookService.createBook(bookCreateDto);

        Book expectedBook = new Book();
        expectedBook.setTitle("Ion");
        expectedBook.setAuthor("Liviu Rebreanu");
        expectedBook.setPrice(55);
        expectedBook.setCategory("Carti de copii");
        expectedBook.setImageUrl("http://localhost-image.jpg");

        assertEquals(expectedBook , createdBook) ;

    }


}