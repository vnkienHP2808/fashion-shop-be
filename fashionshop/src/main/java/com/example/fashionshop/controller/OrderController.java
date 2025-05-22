package com.example.fashionshop.controller;

import com.example.fashionshop.dto.entity.OrderDTO;
import com.example.fashionshop.entity.Order;
import com.example.fashionshop.entity.OrderDetail;
import com.example.fashionshop.entity.Product;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.service.OrderService;
import com.example.fashionshop.service.UserService;
import com.example.fashionshop.repository.ProductRepository;
import com.example.fashionshop.util.DTOMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        User user = userService.getUserById(orderDTO.getId_user());
        Order order = new Order();
        order.setUser(user);
        order.setStatus(orderDTO.getStatus());
        order.setAddress(orderDTO.getAddress());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setShippingFee(orderDTO.getShippingFee());
        order.setGrandTotal(orderDTO.getGrandTotal());

        List<OrderDetail> details = orderDTO.getOrderDetails().stream().map(detailDTO -> {
            OrderDetail detail = new OrderDetail();
            Product product = productRepository.findById(detailDTO.getProduct().getIdProduct())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            detail.setProduct(product);
            detail.setQuantity(detailDTO.getQuantity());
            detail.setTotalAmount(detailDTO.getTotalAmount());
            detail.setOrder(order);
            return detail;
        }).toList();

        order.setOrderDetails(details);

        Order savedOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(DTOMapper.toOrderDTO(savedOrder), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<OrderDTO>> getOrdersByUserId(@PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Order> orderPage = orderService.getOrdersByUserId(userId, page, size);
        Page<OrderDTO> orderDTOPage = orderPage.map(DTOMapper::toOrderDTO);
        return orderPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orderDTOPage);
    }
//~~~~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~~
    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String grandTotalRange,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate){
        
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        try {
            if (startDate != null && !startDate.isEmpty()) {
                LocalDate localStartDate = LocalDate.parse(startDate);
                startDateTime = localStartDate.atStartOfDay();
            }
            if (endDate != null && !endDate.isEmpty()) {
                LocalDate localEndDate = LocalDate.parse(endDate);
                endDateTime = localEndDate.atTime(23, 59, 59);
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }

        Page<Order> orderPage = orderService.getAllOrders(page, size, grandTotalRange, status, startDateTime, endDateTime);
        Page<OrderDTO> orderDTOPage = orderPage.map(DTOMapper::toOrderDTO);
        return orderPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orderDTOPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Integer id, @RequestBody String status) {
        status = status.replace("\"", "");
        Order order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(DTOMapper.toOrderDTO(order));
    }
}