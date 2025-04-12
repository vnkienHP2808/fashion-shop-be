package com.example.fashionshop.controller;

import com.example.fashionshop.entity.Order;
import com.example.fashionshop.entity.OrderDetail;
import com.example.fashionshop.entity.Product;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.service.OrderService;
import com.example.fashionshop.service.UserService;
import com.example.fashionshop.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // Tìm và gán lại user
        User user = userService.getUserById(order.getUser().getId_user());
        order.setUser(user);

        // Thiết lập lại liên kết giữa OrderDetail và Order, cũng như gán Product thực
        for (OrderDetail detail : order.getOrderDetails()) {
            Product product = productRepository.findById(detail.getProduct().getIdProduct())
                                               .orElseThrow(() -> new RuntimeException("Product not found"));
            detail.setProduct(product);
            detail.setOrder(order);
        }

        return orderService.saveOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Integer id, @RequestBody String status) {
        return orderService.updateOrderStatus(id, status);
    }

    @GetMapping("/user/{id}")
    public List<Order> getOrdersByUser(@PathVariable Long id) {
        return orderService.getOrdersByUserId(id);
    }

}