package com.example.fashionshop.service;

import com.example.fashionshop.entity.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Integer id);
    List<Order> getOrdersByUserId(Long userId);
    Order updateOrderStatus(Integer id, String status);
}
