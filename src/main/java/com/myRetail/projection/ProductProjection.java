package com.myRetail.projection;

import com.myRetail.entity.Product;
import com.myRetail.entity.ProductRequest;
import com.myRetail.events.ProductCreateEvent;
import com.myRetail.events.ProductUpdateEvent;
import com.myRetail.exception.ProductException;
import com.myRetail.repository.ProductRepository;
//import com.myRetail.util.TrackTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;

import java.util.Optional;

@Slf4j
@Component
public class ProductProjection {

    @Autowired
    ProductRepository repository;

    @EventHandler
    //@TrackTimeUtil
    public void on(ProductCreateEvent event) {
        log.debug("Handling a Product create command {}", event.getProductId());
       Product product = new Product(
             event.getProductId(),
             event.getPrice(),
             event.getCurrency()
       );
            this.repository.save(product);
    }

    @EventHandler
    //@TrackTimeUtil
    public void on(ProductUpdateEvent event) throws ProductException {
        log.debug("Handling a Product price update command {}", event.getProductId());
        Optional<Product> optionalProduct = Optional.ofNullable(this.repository.findByProductId(event.getProductId()));
        if (optionalProduct.isPresent()) {
            Product updatedProduct = optionalProduct.get();
            updatedProduct.setPrice(event.getPrice());
            this.repository.save(updatedProduct);
        } else {
            throw new ProductException("Product details for the given product {} not available "+event.getProductId());
        }
    }

    @QueryHandler
    //@TrackTimeUtil
    public Product handle(ProductRequest query) {
        log.debug("Handling Product Request query: {}", query);
        return this.repository.findByProductId(query.getProductId());
    }

}
