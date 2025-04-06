package com.example.Library.Shop.repository;


import com.example.Library.Shop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Integer> {

}
