package com.alta.e_commerce.repositories;

import com.alta.e_commerce.entities.Store;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
    Optional<Store> findByUserUserId(String userId);
}

