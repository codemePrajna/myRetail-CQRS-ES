package com.myRetail.dto;

import lombok.Data;

@Data
public class ProductCreateDto {
    String productName;
    Double price;
    String currency;
}
