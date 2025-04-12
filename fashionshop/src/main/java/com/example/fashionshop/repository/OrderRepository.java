package com.example.fashionshop.repository;

import com.example.fashionshop.entity.Order;
import com.example.fashionshop.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
}
