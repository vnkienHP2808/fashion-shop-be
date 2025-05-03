package com.example.fashionshop.service;

import com.example.fashionshop.entity.Order;

import java.util.List;

import org.springframework.data.domain.Page;

public interface OrderService {
    Order saveOrder(Order order);
    Page<Order> getAllOrders(int page, int size);
    Order getOrderById(Integer id);
    List<Order> getOrdersByUserId(Long userId);
    Order updateOrderStatus(Integer id, String status);
}
