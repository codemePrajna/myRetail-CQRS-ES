package com.myRetail.service;

import com.myRetail.dto.ProductDto;
import com.myRetail.exception.ProductException;
import org.codehaus.jettison.json.JSONException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ProductFetchService {
    public ProductDto findByProductId(String productId) throws ExecutionException, InterruptedException, JSONException, ProductException;

    public List<Object> listEventsForProduct(String productId);
}
