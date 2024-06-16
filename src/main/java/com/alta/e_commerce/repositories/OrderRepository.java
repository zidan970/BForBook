package com.alta.e_commerce.repositories;

import java.util.List;
import java.util.Optional;

import com.alta.e_commerce.entities.Order;
import com.alta.e_commerce.models.HistoryRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByCart_CartId(String cartId);

    @Query("SELECT new com.alta.e_commerce.models.HistoryRequest(p.order.orderId, p.totalAmount, p.status, p.createdAt) " +
           "FROM Payment p " +
           "JOIN p.order o " +
           "JOIN o.cart c " +
           "JOIN c.user u " +
           "WHERE u.userId = :userId")
    List<HistoryRequest> findHistories(@Param("userId") String userId);
}