package com.myRetail.service;

import com.myRetail.dto.ProductDto;
import com.myRetail.entity.Product;
import org.codehaus.jettison.json.JSONException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ProductFetchService {
    public ProductDto findByProductId(String productId) throws ExecutionException, InterruptedException, JSONException;
    public List<Object> listEventsForProduct(String productId);
}
