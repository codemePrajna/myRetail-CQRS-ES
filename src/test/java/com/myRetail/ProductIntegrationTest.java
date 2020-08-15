package com.myRetail;

import com.myRetail.commands.ProductCreateCommand;
import com.myRetail.controller.ProductFetchController;
import com.myRetail.dto.ProductDto;
import com.myRetail.entity.Product;
import com.myRetail.entity.ProductAggregate;
import com.myRetail.exception.ProductException;
import com.myRetail.response.Response;
import com.myRetail.service.impl.ProductFetchServiceImpl;
import com.myRetail.service.impl.ProductUpdateServiceImpl;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.saga.FixtureConfiguration;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
public class ProductIntegrationTest {

    @InjectMocks
    ProductFetchServiceImpl productFetchService;

    @InjectMocks
    ProductUpdateServiceImpl productUpdateService;

    @Mock
    FixtureConfiguration fixture;


    private String productId;
    private Product product = new Product();
    private ResponseEntity<Response<ProductDto>> responseEntity;
    private ProductDto productDto = new ProductDto();
    //private FixtureConfiguration fixture;

    @Before
    public void setUp() {
       // fixture = new AggregateTestFixture<>(CommandGateway.class);

        productId = "12345";
        product.setProductId(productId);
        product.setPrice(15.0);
        product.setCurrency("USD");

        productDto.setProductId(productId);
        productDto.setProductName("testPrajna");
        productDto.setPrice(15.0);
        productDto.setCurrency("USD");

       // responseEntity =
    }

    @Test
    public void product_fetch_test() throws ProductException, InterruptedException, ExecutionException, JSONException {
        CommandGateway commandGateway = Mockito.mock(CommandGateway.class);
        fixture.registerResource(commandGateway);
        productUpdateService = new ProductUpdateServiceImpl(commandGateway);

        QueryGateway queryGateway = Mockito.mock(QueryGateway.class);
        EventStore eventStore = Mockito.mock(EventStore.class);
        fixture.registerResource(queryGateway);
        productFetchService = new ProductFetchServiceImpl(queryGateway,eventStore);
        //fixture.registerComm
        //CompletableFuture<Object> productIdCf = CompletableFuture.supplyAsync(() -> productId);
        CompletableFuture<Object> productIdCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productIdCf.get()).thenReturn(productId);
        Mockito.when(commandGateway.send(Mockito.any())).thenReturn(productIdCf);

        CompletableFuture<Object> productCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productCf.get()).thenReturn(product);
        Mockito.when(queryGateway.query(Mockito.any(),Mockito.any(ResponseType.class))).thenReturn(productCf);
        productUpdateService.createProductDetails(productDto);
        ProductDto productDtoResponse = productFetchService.findByProductId(productId);
        Assert.assertEquals(productDto, productDtoResponse );

    }

}
