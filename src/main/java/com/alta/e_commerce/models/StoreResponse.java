package com.alta.e_commerce.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse {
    private String storeId;

    private String userId;

    private String storeName;

    private String storeAddress;
}