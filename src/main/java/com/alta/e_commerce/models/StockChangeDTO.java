package com.alta.e_commerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockChangeDTO {
    private String productId;
    private int stock;
    private String cartItemId;
    private int quantity;
}
