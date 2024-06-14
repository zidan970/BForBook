package com.alta.e_commerce.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreRequest {

    @JsonProperty("user_id")
    //@Size(max = 30)
    private String userId;

    @NotBlank
    @Size(max = 50)
    private String storeName;

    @NotBlank
    @Size(max = 50)
    private String storeAddress;

    // Getter methods
    public String getUserId() {
        return userId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    // Setter methods
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
}