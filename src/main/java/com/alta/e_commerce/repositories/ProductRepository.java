package com.alta.e_commerce.repositories;

import com.alta.e_commerce.entities.Product;
import com.alta.e_commerce.models.ProductResponse;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT NEW com.alta.e_commerce.models.ProductResponse(p.productId, p.store.storeId, p.title, p.author, p.publisher, p.publicationDate, p.genre, p.language, p.page, p.price, p.stock) FROM Product p WHERE p.store.storeId = :storeId")
    List<ProductResponse> findByStore_StoreId(@Param("storeId") String storeId);
}