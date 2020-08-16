package com.myRetail.events;

import lombok.Data;

@Data
public class ProductUpdateEvent {
    private final String productId;
    private final Double price;

}
