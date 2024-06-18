package com.alta.e_commerce.controllers;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.HistoryRequest;
import com.alta.e_commerce.models.OrderRequest;
import com.alta.e_commerce.models.OrderResponse;
import com.alta.e_commerce.models.WebResponse;
import com.alta.e_commerce.services.OrderService;
import com.alta.e_commerce.services.CartService;

import java.util.List;

@RestController
public class OrderController {
    // private static final Logger log = LoggerFactory.getLogger(CartItemController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @PostMapping(
            path = "/orders",
            //consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<OrderResponse> checkout(@RequestBody OrderRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        System.out.println("your id: " + genzaiNoShiyousha.getUserId());

        // find cart with current user id! if there is one, then get the cart id!
        String cartId = cartService.findOrCreateCart(genzaiNoShiyousha.getUserId());

        request.setCartId(cartId);
        System.out.println("id cart: " + request.getCartId());

        OrderResponse orderResponse = orderService.checkout(request);
        return WebResponse.<OrderResponse>builder()
                .message("success order")
                .data(orderResponse)
                .build();
    }

    @GetMapping(
        path = "/order/history",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<HistoryRequest>> checkHistory(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        System.out.println("your id: " + genzaiNoShiyousha.getUserId());

        List<HistoryRequest> orders = orderService.listHistory(genzaiNoShiyousha.getUserId());
        return WebResponse.<List<HistoryRequest>>builder()
                .message("this is your order history:")
                .data(orders)
                .build();
    }
}
