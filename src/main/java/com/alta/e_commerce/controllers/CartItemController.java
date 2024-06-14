package com.alta.e_commerce.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.CartItemRequest;
import com.alta.e_commerce.models.CartItemResponse;
import com.alta.e_commerce.models.WebResponse;
import com.alta.e_commerce.services.CartService;
import com.alta.e_commerce.services.CartItemService;

@RestController
public class CartItemController {
    private static final Logger log = LoggerFactory.getLogger(CartItemController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @PostMapping(
            path = "/carts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<CartItemResponse> addItem(
        @RequestParam("productId") String productId,  // get productId from query param
        @RequestBody CartItemRequest request
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        System.out.println("your id: " + genzaiNoShiyousha.getUserId());

        // find cart with current user id! if there is one, then get the cart id!
        String cartId = cartService.findCartId(genzaiNoShiyousha.getUserId());

        System.out.println("id cart: " + cartId);
        System.out.println("id product: " + productId);

        CartItemResponse cartItemResponse = cartItemService.addItem(cartId, productId, request);
        return WebResponse.<CartItemResponse>builder()
                .message("success add item to cart")
                .data(cartItemResponse)
                .build();
    }
}
