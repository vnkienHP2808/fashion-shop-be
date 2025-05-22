package com.example.fashionshop.service;

import com.example.fashionshop.entity.Order;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

public interface OrderService {
    Order saveOrder(Order order);
    Page<Order> getAllOrders(int page, int size, String priceRange, String status, LocalDateTime startDate, LocalDateTime endDate);
    Order getOrderById(Integer id);
    Page<Order> getOrdersByUserId(Long userId, int page, int size);
    Order updateOrderStatus(Integer id, String status);
}
