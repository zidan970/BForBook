package com.alta.e_commerce.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(nullable = false, length = 50)
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

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false, referencedColumnName = "storeId")
    private Store store;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems;
}
