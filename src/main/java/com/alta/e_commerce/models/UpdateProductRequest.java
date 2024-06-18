package com.alta.e_commerce.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest {
    private String productId;
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
