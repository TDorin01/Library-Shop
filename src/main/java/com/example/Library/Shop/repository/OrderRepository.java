package com.example.Library.Shop.repository;


import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Integer> {

    Orders findById(int id);

    List<Orders> findAllByBookListContaining(Book book);
}
