package com.example.Library.Shop.repository;
import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByUser(User user);

    List<Book> findByCategory(String category);
}
