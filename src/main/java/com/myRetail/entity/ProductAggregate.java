package com.myRetail.entity;

import com.myRetail.commands.ProductCreateCommand;
import com.myRetail.commands.ProductUpdateCommand;
import com.myRetail.events.ProductCreateEvent;
import com.myRetail.events.ProductUpdateEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;


/*@AllArgsConstructor*/
@NoArgsConstructor
@Getter
@Aggregate
public class ProductAggregate {
    /* @AggregateIdentifier
     private UUID id;
     private String status;*/
    //@Column(name = "productId")
    @AggregateIdentifier
    String productId;
    //@Column(name= "name")
    //@Column(name = "currency")
    Double price;
    String currency;
    //@Column(name = "price")


    @CommandHandler
    public ProductAggregate(ProductCreateCommand productCreateCommand) {
        AggregateLifecycle.apply(new ProductCreateEvent(productCreateCommand.getProductId(), productCreateCommand.getPrice(), productCreateCommand.getCurrency()));
    }

    @EventSourcingHandler
    protected void on(ProductCreateEvent productCreateEvent) {
        this.productId = productCreateEvent.getProductId();
        this.price = productCreateEvent.getPrice();
        this.currency = productCreateEvent.getCurrency();
        //this.status = String.valueOf(ProductEnum.CREATED);
        //AggregateLifecycle.apply(new ProductUpdateEvent(this.id, ProductEnum.STARTED));
    }

    @CommandHandler
    public void handle(ProductUpdateCommand productUpdateCommand) {
        AggregateLifecycle.apply(new ProductUpdateEvent(productUpdateCommand.getProductId(), productUpdateCommand.getPrice()));
    }

    @EventSourcingHandler
    protected void on(ProductUpdateEvent productUpdateEvent) {
      /* if(this.productId == null) {
           throw new ProductException("Product you are trying to update do not exists");
       }*/
        this.productId = productUpdateEvent.getProductId();
        this.price = productUpdateEvent.getPrice();
    }

}
