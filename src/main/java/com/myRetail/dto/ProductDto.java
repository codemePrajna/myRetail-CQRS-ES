package com.myRetail.dto;

import lombok.Data;

@Data
public class ProductDto {
    String productId;
    String productName;
    Double price;
    String currency;

}
