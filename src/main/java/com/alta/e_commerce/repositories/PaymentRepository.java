package com.alta.e_commerce.repositories;

import java.util.List;
import java.util.Optional;

import com.alta.e_commerce.entities.Payment;
import com.alta.e_commerce.models.StockChangeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByOrder_OrderId(String orderId);

    @Query("select new com.alta.e_commerce.models.StockChangeDTO(p.productId, p.stock, ci.cartItemId, ci.quantity) " +
            "from Product p " +
            "join CartItem ci on p.productId = ci.product.productId " +
            "join Cart c on ci.cart.cartId = c.cartId " +
            "join Order o ON o.cart.cartId = c.cartId " +
            "where o.orderId = :orderId")
    List<StockChangeDTO> findStockQuantityByOrderId(@Param("orderId") String orderId);
}
