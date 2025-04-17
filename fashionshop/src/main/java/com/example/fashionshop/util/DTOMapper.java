// package com.example.fashionshop.util;

// import com.example.fashionshop.dto.*;
// import com.example.fashionshop.entity.*;

// import java.util.List;
// import java.util.stream.Collectors;

// public class DTOMapper {
    
//     // User Mapper
//     public static UserDTO toUserDTO(User user) {
//         if (user == null) return null;
        
//         return UserDTO.builder()
//                 .id_user(user.getId_user())
//                 .name(user.getName())
//                 .email(user.getEmail())
//                 .status(user.getStatus())
//                 .role(user.getRole())
//                 .phones(user.getPhones())
//                 .addresses(user.getAddresses())
//                 .build();
//     }
    
//     // Category Mapper
//     public static CategoryDTO toCategoryDTO(Category category) {
//         if (category == null) return null;
        
//         return CategoryDTO.builder()
//                 .id(category.getId())
//                 .name(category.getName())
//                 .status(category.getStatus())
//                 .imageLink(category.getImageLink())
//                 .subCategories(category.getSubCategories() != null 
//                         ? category.getSubCategories().stream()
//                                 .map(DTOMapper::toSubCategoryDTO)
//                                 .collect(Collectors.toList())
//                         : null)
//                 .build();
//     }
    
//     // SubCategory Mapper
//     public static SubCategoryDTO toSubCategoryDTO(SubCategory subCategory) {
//         if (subCategory == null) return null;
        
//         return SubCategoryDTO.builder()
//                 .id(subCategory.getId_subcat())
//                 .name(subCategory.getName())
//                 .categoryId(subCategory.getCategory() != null ? subCategory.getCategory().getId() : null)
//                 .build();
//     }
    
//     // Product Mapper
//     public static ProductDTO toProductDTO(Product product) {
//         if (product == null) return null;
        
//         return ProductDTO.builder()
//                 .idProduct(product.getIdProduct())
//                 .name_product(product.getName_product())
//                 .idCat(product.getIdCat())
//                 .idSubcat(product.getIdSubcat())
//                 .is_new(product.getIs_new())
//                 .is_sale(product.getIs_sale())
//                 .price(product.getPrice())
//                 .sale_price(product.getSale_price())
//                 .occasion(product.getOccasion())
//                 .sold_quantity(product.getSold_quantity())
//                 .in_stock(product.getIn_stock())
//                 .status(product.getStatus())
//                 .images(product.getImages() != null 
//                         ? product.getImages().stream()
//                                 .map(DTOMapper::toImageProductDTO)
//                                 .collect(Collectors.toList())
//                         : null)
//                 .build();
//     }
    
//     // ImageProduct Mapper
//     public static ImageProductDTO toImageProductDTO(ImageProduct imageProduct) {
//         if (imageProduct == null) return null;
        
//         return ImageProductDTO.builder()
//                 .id(imageProduct.getIdImage())
//                 .imageLink(imageProduct.getImageLink())
//                 .build();
//     }
    
//     // CartItem Mapper
//     public static CartItemDTO toCartItemDTO(CartItem cartItem) {
//         if (cartItem == null) return null;
        
//         return CartItemDTO.builder()
//                 .id(cartItem.getIdCartItem())
//                 .cartId(cartItem.getCart() != null ? cartItem.getCart().getIdCart() : null)
//                 .product(toProductDTO(cartItem.getProduct()))
//                 .quantity(cartItem.getQuantity())
//                 .build();
//     }
    
//     // Order Mapper
//     public static OrderDTO toOrderDTO(Order order) {
//         if (order == null) return null;
        
//         return OrderDTO.builder()
//                 .id(order.getIdOrder())
//                 .userId(order.getUser() != null ? order.getUser().getId_user() : null)
//                 .status(order.getStatus())
//                 .orderDate(order.getOrderDate())
//                 .address(order.getAddress())
//                 .phoneNumber(order.getPhoneNumber())
//                 .paymentMethod(order.getPaymentMethod())
//                 .shippingFee(order.getShippingFee())
//                 .grandTotal(order.getGrandTotal())
//                 .orderDetails(order.getOrderDetails() != null 
//                         ? order.getOrderDetails().stream()
//                                 .map(DTOMapper::toOrderDetailDTO)
//                                 .collect(Collectors.toList())
//                         : null)
//                 .build();
//     }
    
//     // OrderDetail Mapper
//     public static OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail) {
//         if (orderDetail == null) return null;
        
//         return OrderDetailDTO.builder()
//                 .id(orderDetail.getIdOrderDetail())
//                 .orderId(orderDetail.getOrder() != null ? orderDetail.getOrder().getIdOrder() : null)
//                 .product(toProductDTO(orderDetail.getProduct()))
//                 .quantity(orderDetail.getQuantity())
//                 .totalAmount(orderDetail.getTotalAmount())
//                 .build();
//     }
    
    
//     // Convert lists
//     public static List<UserDTO> toUserDTOList(List<User> users) {
//         return users.stream().map(DTOMapper::toUserDTO).collect(Collectors.toList());
//     }
    
//     public static List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
//         return categories.stream().map(DTOMapper::toCategoryDTO).collect(Collectors.toList());
//     }
    
//     public static List<SubCategoryDTO> toSubCategoryDTOList(List<SubCategory> subCategories) {
//         return subCategories.stream().map(DTOMapper::toSubCategoryDTO).collect(Collectors.toList());
//     }
    
//     public static List<ProductDTO> toProductDTOList(List<Product> products) {
//         return products.stream().map(DTOMapper::toProductDTO).collect(Collectors.toList());
//     }
    
//     public static List<CartItemDTO> toCartItemDTOList(List<CartItem> cartItems) {
//         return cartItems.stream().map(DTOMapper::toCartItemDTO).collect(Collectors.toList());
//     }
    
//     public static List<OrderDTO> toOrderDTOList(List<Order> orders) {
//         return orders.stream().map(DTOMapper::toOrderDTO).collect(Collectors.toList());
//     }
    
//     // Login Response Mapper
//     public static LoginResponseDTO toLoginResponseDTO(User user) {
//         if (user == null) return null;
        
//         return LoginResponseDTO.builder()
//                 .id(user.getId_user())
//                 .name(user.getName())
//                 .email(user.getEmail())
//                 .role(user.getRole())
//                 .status(user.getStatus())
//                 .build();
//     }
// }
package com.example.fashionshop.util;

import com.example.fashionshop.dto.*;
import com.example.fashionshop.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class DTOMapper {

    // User Mapper
    public static UserDTO toUserDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id_user(user.getId_user())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .role(user.getRole())
                .phones(user.getPhones())
                .addresses(user.getAddresses())
                .orders(user.getOrders() != null ? toOrderDTOList(user.getOrders()) : null)
                .cart(user.getCart() != null ? toCartDTO(user.getCart()) : null)
                .build();
    }

    // Cart Mapper
    public static CartDTO toCartDTO(Cart cart) {
        if (cart == null) return null;

        return CartDTO.builder()
                .idCart(cart.getIdCart())
                .id_user(cart.getUser() != null ? cart.getUser().getId_user() : null)
                .cartItems(cart.getCartItems() != null ? toCartItemDTOList(cart.getCartItems()) : null)
                .build();
    }

    // Category Mapper
    public static CategoryDTO toCategoryDTO(Category category) {
        if (category == null) return null;

        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .status(category.getStatus())
                .imageLink(category.getImageLink())
                .subCategories(category.getSubCategories() != null
                        ? category.getSubCategories().stream()
                        .map(DTOMapper::toSubCategoryDTO)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    // SubCategory Mapper
    public static SubCategoryDTO toSubCategoryDTO(SubCategory subCategory) {
        if (subCategory == null) return null;

        return SubCategoryDTO.builder()
                .id_subcat(subCategory.getId_subcat())
                .name(subCategory.getName())
                .categoryId(subCategory.getCategory() != null ? subCategory.getCategory().getId() : null)
                .build();
    }

    // Product Mapper
    public static ProductDTO toProductDTO(Product product) {
        if (product == null) return null;

        return ProductDTO.builder()
                .idProduct(product.getIdProduct())
                .name_product(product.getName_product())
                .idCat(product.getIdCat())
                .idSubcat(product.getIdSubcat())
                .is_new(product.getIs_new())
                .is_sale(product.getIs_sale())
                .price(product.getPrice())
                .sale_price(product.getSale_price())
                .occasion(product.getOccasion())
                .sold_quantity(product.getSold_quantity())
                .in_stock(product.getIn_stock())
                .status(product.getStatus())
                .images(product.getImages() != null
                        ? product.getImages().stream()
                        .map(DTOMapper::toImageProductDTO)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    // ImageProduct Mapper
    public static ImageProductDTO toImageProductDTO(ImageProduct imageProduct) {
        if (imageProduct == null) return null;

        return ImageProductDTO.builder()
                .idImage(imageProduct.getIdImage())
                .imageLink(imageProduct.getImageLink())
                .build();
    }

    // CartItem Mapper
    public static CartItemDTO toCartItemDTO(CartItem cartItem) {
        if (cartItem == null) return null;

        return CartItemDTO.builder()
                .idCartItem(cartItem.getIdCartItem())
                .idCart(cartItem.getCart() != null ? cartItem.getCart().getIdCart() : null)
                .product(toProductDTO(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    // Order Mapper
    public static OrderDTO toOrderDTO(Order order) {
        if (order == null) return null;

        return OrderDTO.builder()
                .idOrder(order.getIdOrder())
                .id_user(order.getUser() != null ? order.getUser().getId_user() : null)
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .paymentMethod(order.getPaymentMethod())
                .shippingFee(order.getShippingFee())
                .grandTotal(order.getGrandTotal())
                .orderDetails(order.getOrderDetails() != null
                        ? order.getOrderDetails().stream()
                        .map(DTOMapper::toOrderDetailDTO)
                        .collect(Collectors.toList())
                        : null)
                .build();
    }

    // OrderDetail Mapper
    public static OrderDetailDTO toOrderDetailDTO(OrderDetail orderDetail) {
        if (orderDetail == null) return null;

        return OrderDetailDTO.builder()
                .idOrderDetail(orderDetail.getIdOrderDetail())
                .orderId(orderDetail.getOrder() != null ? orderDetail.getOrder().getIdOrder() : null)
                .product(toProductDTO(orderDetail.getProduct()))
                .quantity(orderDetail.getQuantity())
                .totalAmount(orderDetail.getTotalAmount())
                .build();
    }

    // Convert lists
    public static List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream().map(DTOMapper::toUserDTO).collect(Collectors.toList());
    }

    public static List<CategoryDTO> toCategoryDTOList(List<Category> categories) {
        return categories.stream().map(DTOMapper::toCategoryDTO).collect(Collectors.toList());
    }

    public static List<SubCategoryDTO> toSubCategoryDTOList(List<SubCategory> subCategories) {
        return subCategories.stream().map(DTOMapper::toSubCategoryDTO).collect(Collectors.toList());
    }

    public static List<ProductDTO> toProductDTOList(List<Product> products) {
        return products.stream().map(DTOMapper::toProductDTO).collect(Collectors.toList());
    }

    public static List<CartItemDTO> toCartItemDTOList(List<CartItem> cartItems) {
        return cartItems.stream().map(DTOMapper::toCartItemDTO).collect(Collectors.toList());
    }

    public static List<OrderDTO> toOrderDTOList(List<Order> orders) {
        return orders.stream().map(DTOMapper::toOrderDTO).collect(Collectors.toList());
    }

    // Login Response Mapper
    public static LoginResponseDTO toLoginResponseDTO(User user) {
        if (user == null) return null;
    
        return LoginResponseDTO.builder()
                .id_user(user.getId_user())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .phones(user.getPhones())
                .addresses(user.getAddresses())
                .orders(user.getOrders() != null ? toOrderDTOList(user.getOrders()) : null)
                .cart(user.getCart() != null ? toCartDTO(user.getCart()) : null)
                .build();
    }
    
}
