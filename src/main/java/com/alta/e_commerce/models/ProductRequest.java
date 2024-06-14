package com.alta.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @JsonProperty("store_id")
    @NotBlank
    @Size(max = 50)
    private String storeId;
    
    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 50)
    private String author;

    @NotBlank
    @Size(max = 50)
    private String publisher;

    @NotNull
    private Date publicationDate;

    @NotBlank
    @Size(max = 50)
    private String genre;

    @NotBlank
    @Size(max = 50)
    private String language;

    @NotNull
    private Integer page;

    @NotNull
    private Float price;

    @NotNull
    private Integer stock;
}
