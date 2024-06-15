package com.alta.e_commerce.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alta.e_commerce.entities.Order;
import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.PaymentRequest;
import com.alta.e_commerce.models.PaymentResponse;
import com.alta.e_commerce.models.WebResponse;
import com.alta.e_commerce.services.CartService;
import com.alta.e_commerce.services.OrderService;
import com.alta.e_commerce.services.PaymentService;

@RestController
public class PaymentController {
    //private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping(
            path = "/payments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<PaymentResponse> create(@RequestBody PaymentRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        System.out.println("your id: " + genzaiNoShiyousha.getUserId());

        // find cart with current user id! if there is one, then get the cart id!
        String cartId = cartService.findCartId(genzaiNoShiyousha.getUserId());
        System.out.println("your cart id: " + cartId);

        // find order with current cart id! if there is one, then get the order id!
        Order order = orderService.findOrderId(cartId);
        System.out.println("your order id: " + order.getOrderId());

        request.setOrderId(order.getOrderId());
        request.setTotalAmount(200000); //temporary
        request.setVaNumber("754957710063396131"); //temporary

        PaymentResponse paymentResponse = paymentService.create(request, genzaiNoShiyousha.getUserId());
        return WebResponse.<PaymentResponse>builder()
                .message("this is your payment detail:")
                .data(paymentResponse)
                .build();
    }
}
