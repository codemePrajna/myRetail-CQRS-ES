package com.myRetail.service.impl;

import com.myRetail.commands.ProductCreateCommand;
import com.myRetail.commands.ProductUpdateCommand;
import com.myRetail.dto.ProductDto;
import com.myRetail.dto.ProductUpdateDto;
import com.myRetail.exception.ProductException;
import com.myRetail.service.ProductUpdateService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ProductUpdateServiceImpl implements ProductUpdateService {
    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public ProductUpdateServiceImpl(CommandGateway commandGateway,EventStore eventStore) {

        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    /**
     * function to construct update command trigger event
     *
     * @param product
     * @return
     */
    @Override
    // @TrackTimeUtil
    public CompletableFuture<String> updateProductDetails(String productId, ProductUpdateDto product) throws ProductException {
        if(!checkIfProductExists(productId)) {
            throw new ProductException(String.format("Product %s does not exist", productId));
        }
        return commandGateway.send(new ProductUpdateCommand(productId,
                product.getPrice()));
    }

    /**
     * function to construct create command trigger event
     *
     * @param product
     * @return
     */
    @Override
    //@TrackTimeUtil
    public CompletableFuture<String> createProductDetails(ProductDto product) throws ProductException {
        if(checkIfProductExists(product.getProductId())) {
            throw new ProductException(String.format("Product %s already exists", product.getProductId()));
        }
        return commandGateway.send(new ProductCreateCommand(product.getProductId(), product.getPrice(),
                product.getCurrency()));
    }

    private boolean checkIfProductExists(String productId){
        if(this.eventStore
                .readEvents(productId)
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList()).size() == 0){
            return false;
        }
        return true;

    }
}
