package com.myRetail.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateCommand {
    @TargetAggregateIdentifier
    private String productId;
    private Double price;
    private String currency;
}
