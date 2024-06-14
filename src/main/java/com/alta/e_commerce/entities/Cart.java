package com.alta.e_commerce.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart implements Serializable {

    @Id
    @Column(length = 50, nullable = false)
    private String cartId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", unique = true, nullable = false)
    private User user;

    @Column(length = 10, nullable = false)
    private String status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();
}
