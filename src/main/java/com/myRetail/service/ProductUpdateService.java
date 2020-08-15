package com.myRetail.service;

import com.myRetail.dto.ProductCreateDto;
import com.myRetail.dto.ProductDto;
import com.myRetail.dto.ProductUpdateDto;
import com.myRetail.entity.Product;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductUpdateService {
    public CompletableFuture<String> updateProductDetails(String productId, ProductUpdateDto product);
    public CompletableFuture<String> createProductDetails(ProductDto product);
}
