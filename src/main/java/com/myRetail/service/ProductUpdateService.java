package com.myRetail.service;

import com.myRetail.dto.ProductDto;
import com.myRetail.dto.ProductUpdateDto;
import com.myRetail.exception.ProductException;

import java.util.concurrent.CompletableFuture;

public interface ProductUpdateService {
    public CompletableFuture<String> updateProductDetails(String productId, ProductUpdateDto product) throws ProductException;

    public CompletableFuture<String> createProductDetails(ProductDto product) throws ProductException;
}
