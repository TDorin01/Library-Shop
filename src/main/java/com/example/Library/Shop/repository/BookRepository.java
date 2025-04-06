package com.example.Library.Shop.repository;

import com.example.Library.Shop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
