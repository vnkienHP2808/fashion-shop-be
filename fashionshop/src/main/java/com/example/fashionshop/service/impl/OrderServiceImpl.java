package com.example.fashionshop.service.impl;

import com.example.fashionshop.entity.Order;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.exception.ValidationException;
import com.example.fashionshop.repository.OrderRepository;
import com.example.fashionshop.repository.UserRepository;
import com.example.fashionshop.service.OrderService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Order saveOrder(Order order) {
        validateOrder(order);
        return orderRepository.save(order);
    }

    @Override
    public Page<Order> getAllOrders(int page, int size, String grandTotalRange, String status, LocalDateTime startDate, LocalDateTime endDate) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "orderDate"));
        Integer minPrice = null;
        Integer maxPrice = null;

        if (grandTotalRange != null && !grandTotalRange.isEmpty()) {
            try {
                String[] range = grandTotalRange.split("-");
                minPrice = Integer.parseInt(range[0]);
                maxPrice = Integer.parseInt(range[1]);
            } catch (NumberFormatException e) {
                throw new ValidationException("Invalid price range format");
            }
        }
        Page<Order> orderPage = orderRepository.findByFilters(minPrice, maxPrice, status, startDate, endDate, pageable);
        return orderPage;
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Page<Order> getOrdersByUserId(Long userId, int page, int size, String grandTotalRange, String status, LocalDateTime startDate, LocalDateTime endDate) {
        User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "orderDate"));

        Integer minPrice = null;
        Integer maxPrice = null;

        if (grandTotalRange != null && !grandTotalRange.isEmpty()) {
            try {
                String[] range = grandTotalRange.split("-");
                minPrice = Integer.parseInt(range[0]);
                maxPrice = Integer.parseInt(range[1]);
            } catch (NumberFormatException e) {
                throw new ValidationException("Invalid price range format");
            }
        }
        Page<Order> orderPage = orderRepository.findByFiltersMyOrder(user, minPrice, maxPrice, status, startDate, endDate, pageable);
        return orderPage; 
    }


    @Override
    public Order updateOrderStatus(Integer id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    private void validateOrder(Order order) {
        if (order.getUser() == null) {
            throw new ValidationException("Order must have a user");
        }
        if (order.getOrderDetails() == null || order.getOrderDetails().isEmpty()) {
            throw new ValidationException("Order must have at least one order detail");
        }
        if (order.getGrandTotal() <= 0) {
            throw new ValidationException("Order total must be greater than 0");
        }
    }
}
