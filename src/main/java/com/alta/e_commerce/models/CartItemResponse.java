package com.alta.e_commerce.models;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private String cartItemId;
    private String cartId;
    private String productId;
    private Float price;
    private Integer quantity;
    private Float total;
}

