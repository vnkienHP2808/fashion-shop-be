package com.example.fashionshop.repository;

import com.example.fashionshop.entity.Order;
import com.example.fashionshop.entity.User;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o " +
            "WHERE (:minPrice IS NULL OR o.grandTotal >= :minPrice) " +
            "AND (:maxPrice IS NULL OR o.grandTotal <= :maxPrice) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:startDate IS NULL OR o.orderDate >= :startDate)" +
            "AND (:endDate IS NULL OR o.orderDate <= :endDate)")

    Page<Order> findByFilters(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT o FROM Order o " +
            "WHERE (:minPrice IS NULL OR o.grandTotal >= :minPrice) " +
            "AND (:maxPrice IS NULL OR o.grandTotal <= :maxPrice) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:startDate IS NULL OR o.orderDate >= :startDate)" +
            "AND (:endDate IS NULL OR o.orderDate <= :endDate)")

    Page<Order> findByFiltersMyOrder(
            User user,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);           
}
