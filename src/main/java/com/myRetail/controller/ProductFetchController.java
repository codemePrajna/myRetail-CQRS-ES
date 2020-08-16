package com.myRetail.controller;

import com.myRetail.dto.ProductDto;
import com.myRetail.exception.ProductException;
import com.myRetail.response.Response;
import com.myRetail.service.impl.ProductFetchServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * QUERY side-REST API endpoints for fetching the product Details
 */
@RestController
@Slf4j
@ApiOperation(value = "Fetch product Details for a given Product")
@RequestMapping("/api/v1/product")
public class ProductFetchController {

    @Autowired
    ProductFetchServiceImpl productFetchService;

    /**
     * Function to fetch the product details
     *
     * @param productId
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/{productId}")
    @ApiOperation(value = "Fetch product Details for a given Product")
    public ResponseEntity<Response<ProductDto>> fetchProduct(@PathVariable(value = "productId") String productId) throws InterruptedException, ExecutionException, JSONException, ProductException {

        return new Response<ProductDto>()
                .setStatus(200)
                .setMessage(productFetchService.findByProductId(productId)).toResponseEntity();
    }

    /**
     * List product Details Event sequence for a given Product
     *
     * @param productId
     * @return
     */
    @ApiOperation(value = "List product Details Event sequence for a given Product")
    @GetMapping("/{productId}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "productId") String productId) {
        return this.productFetchService.listEventsForProduct(productId);
    }
}
