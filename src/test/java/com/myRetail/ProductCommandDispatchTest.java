package com.myRetail;

import com.myRetail.commands.ProductCreateCommand;
import com.myRetail.commands.ProductUpdateCommand;
import com.myRetail.entity.ProductAggregate;
import com.myRetail.events.ProductCreateEvent;
import com.myRetail.events.ProductUpdateEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
public class ProductCommandDispatchTest {
    private FixtureConfiguration<ProductAggregate> fixture;
    private String productId;
    private Double price;
    private String currency;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(ProductAggregate.class);
        productId = "13860428";
        price = 15.0;
        currency = "USD";
    }

    @Test
    public void dispatch_productCreateCommand(){
        fixture.givenNoPriorActivity()
                .when(new ProductCreateCommand(
                        productId,
                        price,
                        currency)
                )
                .expectEvents(new ProductCreateEvent(
                        productId,
                        price,
                        currency)
                );
    }

    @Test
    public void dispatch_productUpdateCommand(){
        fixture.givenNoPriorActivity()
                .when(new ProductUpdateCommand(
                        productId,
                        price)
                )
                .expectEvents(new ProductUpdateEvent(
                        productId,
                        price)
                );
    }

    @Test
    public void dispatch_productUpdateCommand_when_product_exists() {
        fixture.given(new ProductCreateEvent(
                productId,
                price,
                currency))
                .when(new ProductUpdateCommand(
                        productId,
                        price)
                )
                .expectEvents(new ProductUpdateEvent(
                        productId,
                        price)
                );
    }

    @Test
    public void do_not_dispatch_productUpdateCommand_when_product_does_not_exists() {
        fixture.given(this.productId == null)
                .when(new ProductUpdateCommand(
                        productId,
                        price)
                )
                .expectNoEvents();
    }

}
