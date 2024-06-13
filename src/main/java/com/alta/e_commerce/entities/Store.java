package com.alta.e_commerce.entities;

import jakarta.persistence.*;

@Table(name = "stores")
@Entity
public class Store {
    @Id
    @Column(nullable = false, length = 50)
    private String storeId;

    @Column(nullable = false, unique = true, length = 50)
    private String storeName;

    @Column(nullable = false, length = 50)
    private String storeAddress;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, referencedColumnName = "userId")
    private User user;

    public String getStoreId() {
        return storeId;
    }

    public Store setStoreId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    public String getStoreName() {
        return storeName;
    }

    public Store setStoreName(String storeName) {
        this.storeName = storeName;
        return this;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public Store setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Store setUser(User user) {
        this.user = user;
        return this;
    }
}
