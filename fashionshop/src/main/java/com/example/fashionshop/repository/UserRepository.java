package com.example.fashionshop.repository;

import com.example.fashionshop.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

    @Query("SELECT u from User u where u.name like %:name%")
    Page<User> findUsersByName(
        @Param("name") String name,
        org.springframework.data.domain.Pageable pageable
    );
}
