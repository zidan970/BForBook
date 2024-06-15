package com.alta.e_commerce.entities;

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
@Table(name = "orders")
public class Order {
    @Id
    @Column(nullable = false, length = 50)
    private String orderId;

    @OneToOne
    @JoinColumn(name = "cart_id", unique = true, referencedColumnName = "cartId")
    private Cart cart;

    @Column(nullable = false, length = 10)
    private String status;
}
