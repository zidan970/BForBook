package com.alta.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alta.e_commerce.entities.Product;
import com.alta.e_commerce.entities.Store;
import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.ProductRequest;
import com.alta.e_commerce.models.ProductResponse;
import com.alta.e_commerce.models.UserResponse;
import com.alta.e_commerce.repositories.StoreRepository;
import com.alta.e_commerce.repositories.ProductRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponse create(String userId, ProductRequest productRequest) {

        System.out.println("id pemilik toko: " + userId);

        Store store = storeRepository.findByUserUserId(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found. Cannot create product."));

        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setStore(store);
        product.setTitle(productRequest.getTitle());
        product.setAuthor(productRequest.getAuthor());
        product.setPublisher(productRequest.getPublisher());
        product.setPublicationDate(productRequest.getPublicationDate());
        product.setGenre(productRequest.getGenre());
        product.setLanguage(productRequest.getLanguage());
        product.setPage(productRequest.getPage());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());

        productRepository.save(product);

        return ProductResponse.builder()
            .productId(product.getProductId())
            .storeId(product.getStore().getStoreId()) //pemanggilan ini tidak bisa dilakukan ketika save
            .title(product.getTitle())
            .author(product.getAuthor())
            .publisher(product.getPublisher())
            .publicationDate(product.getPublicationDate())
            .genre(product.getGenre())
            .language(product.getLanguage())
            .page(product.getPage())
            .price(product.getPrice())
            .stock(product.getStock())
            .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
            product.getProductId(),
            product.getStore().getStoreId(),
            product.getTitle(),
            product.getAuthor(),
            product.getPublisher(),
            product.getPublicationDate(),
            product.getGenre(),
            product.getLanguage(),
            product.getPage(),
            product.getPrice(),
            product.getStock()
        );
    }

    public List<ProductResponse> toListProductResponse(List<Product> products) {
        return products.stream()
            .map(this::toProductResponse)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse getSingleProduct(String productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));

        return toProductResponse(product);
    }

    @Transactional
    public List<ProductResponse> getMyProducts(String userId){
        Store store = storeRepository.findByUserUserId(userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "You do not have a store."));

        List<ProductResponse> products = productRepository.findByStore_StoreId(store.getStoreId());

        return products;
    }

    public List<Product> allProducts() {
        List<Product> products = new ArrayList<>();

        productRepository.findAll().forEach(products::add);

        return products;
    }

    @Transactional
    public void delete(String productId){
        // pastikan book nya ada
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "book's not found"));

        productRepository.delete(product);
    }
}