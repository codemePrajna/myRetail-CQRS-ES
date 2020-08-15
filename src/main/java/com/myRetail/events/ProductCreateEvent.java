package com.myRetail.events;

import lombok.Data;

@Data
public class ProductCreateEvent {
    private final String productId;
    private final Double price;
    private final String currency;
}
