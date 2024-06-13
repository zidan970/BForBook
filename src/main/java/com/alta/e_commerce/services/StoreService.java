package com.alta.e_commerce.services;

import com.alta.e_commerce.entities.Store;
import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.StoreRequest;
import com.alta.e_commerce.models.StoreResponse;
import com.alta.e_commerce.repositories.StoreRepository;
import com.alta.e_commerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class StoreService {
    
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private JwtService jwtService;

    @Transactional
    public StoreResponse create(StoreRequest request){
        System.out.println("id di service layer: " + request.getUserId());

        validationService.validate(request);

        // check user nya ada atau tidak
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found"));

        Store store = new Store();
        store.setStoreId(UUID.randomUUID().toString());
        //store.getUser().setUserId(request.getUserId()); // nggak bisa dipanggil, karena isi dari getUser null
        store.setStoreName(request.getStoreName());
        store.setStoreAddress(request.getStoreAddress());
        //store.setUser(user);
        // store.setStoreId(UUID.randomUUID().toString());
        // store.setStoreName(request.getStoreName());
        // store.setStoreAddress(request.getStoreAddress());
        store.setUser(user);

        storeRepository.save(store);

        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .userId(store.getUser().getUserId())        // bisa dipanggil
                .storeName(store.getStoreName())
                .storeAddress(request.getStoreAddress())
                .build();
    }

    // private StoreResponse toStoreResponse(Store store){
    //     return StoreResponse.builder()
    //         .storeId(store.getStoreId())
    //         .userId(store.getUser().getUserId())
    //         .storeName(store.getStoreName())
    //         .storeAddress(store.getStoreAddress())
    //         .build();
    // }
}
