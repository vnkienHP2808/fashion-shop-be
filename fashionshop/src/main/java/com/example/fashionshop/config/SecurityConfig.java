package com.example.fashionshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // cấu hình cors thì fe chạy trên 5173, be chạy trên 8080 khác cổng nên phải cấu hình
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // public các api này để hiện sp dù không đăng nhập
                        .requestMatchers("/auth/sign-up", "/auth/sign-in", "/auth/refresh", "/images/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/products",
                                "/api/products/new",
                                "/api/products/sale",
                                "/api/products/search",
                                "/api/products/{id_product}",
                                "/api/products/category/**",
                                "/api/products/category/{id_cat}/subcategory/**",
                                "/api/categories"
                        ).permitAll()

                        // đổi mật khảu thì phải đăng nhập trước 
                        .requestMatchers(HttpMethod.POST, "/auth/change-password").hasAnyRole("Customer", "Admin")

                        // các api liên quan đến đơn hàng, giỏ hàng hay thông tin người dùng thì phải có role
                        .requestMatchers(HttpMethod.PUT, "/api/products/update-quantity-and-sold").hasAnyRole("Customer")
                        .requestMatchers(HttpMethod.GET, "/api/orders/user/{userId}").hasAnyRole("Customer")
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole("Customer", "Admin")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}/phones").hasAnyRole("Customer", "Admin")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}/addresses").hasAnyRole("Customer", "Admin")
                        .requestMatchers(HttpMethod.POST, "/api/orders").hasAnyRole("Customer")
                        .requestMatchers(HttpMethod.GET, "/api/cart/{userId}").hasAnyRole("Customer")
                        .requestMatchers(HttpMethod.POST, "/api/cart/add").hasAnyRole("Customer")
                        .requestMatchers(HttpMethod.PUT, "/api/cart/update").hasAnyRole("Customer")
                        .requestMatchers(HttpMethod.DELETE, "/api/cart/{userId}/remove/{productId}").hasAnyRole("Customer")
                        .requestMatchers(HttpMethod.DELETE, "/api/cart/{userId}/clear").hasAnyRole("Customer")

                        // các api thao tác với sp hoặc đơn hàng thì phải là admin sửa đổi
                        .requestMatchers(HttpMethod.POST, "/api/products/create").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/api/products/{id}").hasRole("Admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/products/{id}").hasRole("Admin")
                        .requestMatchers(HttpMethod.GET, "/api/orders").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/api/orders/{id}").hasRole("Admin")
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}/status").hasRole("Admin")


                        .anyRequest().authenticated())
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // cho phép đường dẫn của link fe
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); // cho phép gửi credentials (cần thiết)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}