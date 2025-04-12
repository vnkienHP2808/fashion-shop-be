package com.example.fashionshop.service.impl;

import com.example.fashionshop.entity.Order;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.repository.OrderRepository;
import com.example.fashionshop.repository.UserRepository;
import com.example.fashionshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Order saveOrder(Order order) {
        // Đảm bảo các OrderDetail đã set order trong controller nếu cần
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user); 
    }


    @Override
    public Order updateOrderStatus(Integer id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
