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

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.ProductRequest;
import com.alta.e_commerce.models.ProductResponse;
import com.alta.e_commerce.models.WebResponse;
import com.alta.e_commerce.services.ProductService;

@RestController
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private ProductService productService;

    @PostMapping(
            path = "/books",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public WebResponse<ProductResponse> create(@RequestBody ProductRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        System.out.println("your id: " + genzaiNoShiyousha.getUserId());

        // request.setUserId(genzaiNoShiyousha.getUserId()); // Menggunakan setter untuk mengatur userId
        // System.out.println("id request: " + request.getUserId());

        ProductResponse productResponse = productService.create(genzaiNoShiyousha.getUserId(), request);
        return WebResponse.<ProductResponse>builder()
                .message("success create product")
                .data(productResponse)
                .build();
    }
}
