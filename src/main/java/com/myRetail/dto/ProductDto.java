package com.myRetail.dto;

import lombok.Data;
import lombok.Value;
import org.springframework.stereotype.Component;

@Data
public class ProductDto {
    String productId;
    String productName;
    Double price;
    String currency;

}
