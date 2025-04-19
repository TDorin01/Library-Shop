package com.example.Library.Shop.repository;
import com.example.Library.Shop.model.Book;
import com.example.Library.Shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order,Integer> {


    List<Order> findAllByBookListContaining(Book book);

    @Query("SELECT EXTRACT(MONTH FROM o.localDateTime) AS month, SUM(o.totalPrice), COUNT(o.id) " +
            "FROM Order o GROUP BY month ORDER BY month ASC")
    List<Object[]> getMonthlySalesStats();
}

