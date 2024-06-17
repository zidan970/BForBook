package com.alta.e_commerce.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.entities.Product;
import com.alta.e_commerce.models.ProductRequest;
import com.alta.e_commerce.models.ProductResponse;
import com.alta.e_commerce.models.UserResponse;
import com.alta.e_commerce.models.WebResponse;
import com.alta.e_commerce.services.ProductService;

import java.util.List;

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

        ProductResponse productResponse = productService.create(genzaiNoShiyousha.getUserId(), request);
        return WebResponse.<ProductResponse>builder()
                .message("success create product")
                .data(productResponse)
                .build();
    }

    @GetMapping(
            path = "/products/{productId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ProductResponse> getBookById(@PathVariable("productId") String productId){
        ProductResponse product = productService.getSingleProduct(productId);
        return WebResponse.<ProductResponse>builder()
                .message("book's data:")
                .data(product)
                .build();
    }

    @GetMapping("/products/store")
    public WebResponse<List<ProductResponse>> getProductsOfStore() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        System.out.println("your id: " + genzaiNoShiyousha.getUserId());

        List<ProductResponse> productResponse = productService.getMyProducts(genzaiNoShiyousha.getUserId());

        return WebResponse.<List<ProductResponse>>builder()
                .message("books of my store:")
                .data(productResponse)
                .build();
    }

    @GetMapping("/products/all")
    public WebResponse<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.allProducts();

        List<ProductResponse> allProducts = productService.toListProductResponse(products);

        return WebResponse.<List<ProductResponse>>builder()
                .message("all books:")
                .data(allProducts)
                .build();
    }

    @DeleteMapping(
        path = "/products/{productId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("productId") String productId){
    productService.delete(productId);
    return WebResponse.<String>builder()
            .message("success delete product")
            .build();
}

}
