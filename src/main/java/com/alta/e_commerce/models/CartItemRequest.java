package com.alta.e_commerce.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemRequest {
    private String cartId;
    private String productId;
    private Float price;
    private Integer quantity;
    private Float total;
}

