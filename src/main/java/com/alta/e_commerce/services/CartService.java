package com.alta.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alta.e_commerce.entities.Cart;
import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.repositories.CartRepository;
import com.alta.e_commerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public String findCartId(String userId){
        // check whether the cart exists or not. 
        // If it doesn't exist, it will be created directly in the create method
        Cart cart = cartRepository.findByUserIdAndStatusOnGoing(userId)
            .orElseGet(() -> create(userId));

        return cart.getCartId();
    }

    @Transactional
    public Cart create(String userId){
        // check whether the user exists or not
        User user = userRepository.findById(userId)
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user is not found"));

        Cart cart = new Cart();
        cart.setCartId(UUID.randomUUID().toString());
        cart.setUser(user);
        cart.setStatus("On Going");

        cartRepository.save(cart);

        return cart;
    }
}
