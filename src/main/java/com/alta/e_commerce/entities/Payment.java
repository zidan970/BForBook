package com.alta.e_commerce.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(nullable = false, length = 50)
    private String paymentId;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true, referencedColumnName = "orderId")
    private Order order;

    @Column(nullable = false)
    private float totalAmount;

    @Column(nullable = false, length = 50)
    private String bankAccount;

    @Column(nullable = false, length = 50)
    private String vaNumber;

    @Column(nullable = false, length = 10)
    private String status;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
