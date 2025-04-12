package com.example.Library.Shop.repository;
import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Integer> {

    Orders findById(int id);

    List<Orders> findAllByBookListContaining(Book book);

    @Query("SELECT EXTRACT(MONTH FROM o.localDateTime) as month, SUM(o.totalPrice), COUNT(o.id) " +
            "FROM Orders o GROUP BY EXTRACT(MONTH FROM o.localDateTime)")
    List<Object[]> getMonthlySalesStats();
}

