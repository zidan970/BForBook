package com.alta.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alta.e_commerce.entities.Cart;
import com.alta.e_commerce.entities.CartItem;
import com.alta.e_commerce.entities.Product;
import com.alta.e_commerce.repositories.CartRepository;
import com.alta.e_commerce.repositories.ProductRepository;
import com.alta.e_commerce.repositories.CartItemRepository;
import com.alta.e_commerce.models.CartItemRequest;
import com.alta.e_commerce.models.CartItemResponse;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartItemService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public CartItemResponse addItem(String cartId, String productId, CartItemRequest request){
        // check whether the cart exists or not
        Cart cart = cartRepository.findByCartId(cartId)
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"cart is not found"));

        // check whether the product exists or not
        Product product = productRepository.findById(productId)
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"product is not found"));

        Float totalAmount = product.getPrice() * request.getQuantity();

        System.out.println("total amount: " + totalAmount);

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(UUID.randomUUID().toString());
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(request.getQuantity());
        cartItem.setTotal(totalAmount);

        cartItemRepository.save(cartItem);

        return CartItemResponse.builder()
            .cartItemId(cartItem.getCartItemId())
            .cartId(cartItem.getCart().getCartId())
            .productId(cartItem.getProduct().getProductId())
            .price(cartItem.getPrice())
            .quantity(cartItem.getQuantity())
            .total(cartItem.getTotal())
            .build();
    }
}
