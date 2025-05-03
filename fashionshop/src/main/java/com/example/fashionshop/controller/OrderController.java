

package com.example.fashionshop.controller;

import com.example.fashionshop.dto.OrderDTO;
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
import org.springframework.http.ResponseEntity;
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
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        // Lấy user từ id_user trong OrderDTO
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
        return DTOMapper.toOrderDTO(savedOrder);
    }

    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return DTOMapper.toOrderDTOList(orders);
    }
//~~~~~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~
    @GetMapping
    public ResponseEntity<Page<OrderDTO>> getAllOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<Order> orderPage = orderService.getAllOrders(page, size);
        Page<OrderDTO> orderDTOPage = orderPage.map(DTOMapper::toOrderDTO);
        return ResponseEntity.ok(orderDTOPage);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrderStatus(@PathVariable Integer id, @RequestBody String status) {
        status = status.replace("\"", "");
        Order order = orderService.updateOrderStatus(id, status);
        return DTOMapper.toOrderDTO(order);
    }
}
