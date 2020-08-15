package com.myRetail.service.impl;

import com.myRetail.commands.ProductCreateCommand;
import com.myRetail.commands.ProductUpdateCommand;
import com.myRetail.dto.ProductCreateDto;
import com.myRetail.dto.ProductDto;
import com.myRetail.dto.ProductUpdateDto;
import com.myRetail.entity.Product;
import com.myRetail.events.ProductCreateEvent;
import com.myRetail.service.ProductUpdateService;
//import com.myRetail.util.TrackTimeUtil;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductUpdateServiceImpl implements ProductUpdateService {
    private final CommandGateway commandGateway;
    public ProductUpdateServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    /**
     * function to construct update command trigger event
     * @param product
     * @return
     */
    @Override
   // @TrackTimeUtil
    public CompletableFuture<String> updateProductDetails(String productId, ProductUpdateDto product) {
        return commandGateway.send(new ProductUpdateCommand(productId,
                product.getPrice()));
    }

    /**
     * function to construct create command trigger event
     * @param product
     * @return
     */
    @Override
    //@TrackTimeUtil
    public CompletableFuture<String> createProductDetails(ProductDto product) {
        return commandGateway.send(new ProductCreateCommand(product.getProductId(),product.getPrice(),
                product.getCurrency()));
    }
}
