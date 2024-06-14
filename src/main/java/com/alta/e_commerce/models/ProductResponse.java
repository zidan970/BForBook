package com.alta.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("store_id")
    private String storeId;

    private String title;

    private String author;

    private String publisher;

    private Date publicationDate;

    private String genre;

    private String language;

    private Integer page;

    private Float price;

    private Integer stock;
}
