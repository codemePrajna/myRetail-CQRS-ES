package com.myRetail.controller;

import com.myRetail.dto.ProductDto;
import com.myRetail.dto.ProductUpdateDto;
import com.myRetail.exception.ProductException;
import com.myRetail.response.Response;
import com.myRetail.service.impl.ProductUpdateServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/api/v1/product")
public class ProductUpdateController {

    @Autowired
    ProductUpdateServiceImpl productUpdateService;

    /**
     * Create and load product details
     *
     * @param product
     * @return
     */
    @PostMapping("/load")
    @ApiOperation(value = "Create and load product details")
    public ResponseEntity<Response<String>> createProduct(@RequestBody ProductDto product) throws ProductException {
        //return productUpdateService.createProductDetails(product);
        productUpdateService.createProductDetails(product);
        return new Response<String>()
                .setStatus(200)
                .setMessage(String.format("Product %s created successfully", product.getProductId())).toResponseEntity();
    }

    /**
     * Update product price details for a given Product
     *
     * @param product
     * @return
     */
    @ApiOperation(value = "Update product price details for a given Product")
    @PutMapping("/{productId}")
    public ResponseEntity<Response<String>> updateProduct(@PathVariable(value = "productId") String productId, @RequestBody ProductUpdateDto product) throws ProductException {
        //return productUpdateService.updateProductDetails(productId,product);
        productUpdateService.updateProductDetails(productId, product);
        return new Response<String>()
                .setStatus(200)
                .setMessage(String.format("Product %s updated successfully", productId)).toResponseEntity();
    }
}
