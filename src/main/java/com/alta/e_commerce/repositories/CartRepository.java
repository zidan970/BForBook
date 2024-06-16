package com.alta.e_commerce.repositories;

import java.util.Optional;

import com.alta.e_commerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByCartId(String cartId);

    @Query(
        "select c from Cart c where c.user.userId = :userId and c.status = 'On Going'"
    )
    Optional<Cart> findByUserIdAndStatusOnGoing(@Param("userId") String userId);
}
