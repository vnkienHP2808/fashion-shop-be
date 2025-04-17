package com.example.fashionshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long idProduct;
    private String name_product;
    private Long idCat;
    private Long idSubcat;
    private Boolean is_new;
    private Boolean is_sale;
    private Integer price;
    private Integer sale_price;
    private String occasion;
    private Integer sold_quantity;
    private Integer in_stock;
    private String status;
    private List<ImageProductDTO> images;
}