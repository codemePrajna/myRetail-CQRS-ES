package com.myRetail;

import com.myRetail.dto.ProductDto;
import com.myRetail.entity.Product;
import com.myRetail.exception.ProductException;
import com.myRetail.response.Response;
import com.myRetail.service.impl.ProductFetchServiceImpl;
import com.myRetail.service.impl.ProductUpdateServiceImpl;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.MetaData;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.test.saga.FixtureConfiguration;
import org.codehaus.jettison.json.JSONException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @InjectMocks
    ProductFetchServiceImpl productFetchService;

    @InjectMocks
    ProductUpdateServiceImpl productUpdateService;

    @Mock
    FixtureConfiguration fixture;

    @Mock
    EventStore eventStore;


    private String productId;
    private Product product = new Product();
    private ResponseEntity<Response<ProductDto>> responseEntity;
    private ProductDto productDto = new ProductDto();
    //private FixtureConfiguration fixture;

    @Before
    public void setUp() {
        // fixture = new AggregateTestFixture<>(CommandGateway.class);
        MockitoAnnotations.initMocks(this);
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
    public void product_create_test_when_data_exists() throws ProductException, InterruptedException, ExecutionException, JSONException, ProductException {
        CommandGateway commandGateway = Mockito.mock(CommandGateway.class);
        DomainEventMessage event1 = new GenericDomainEventMessage<>("type", UUID.randomUUID().toString(), (long) 0,
                "Mock contents", MetaData.emptyInstance());
        DomainEventMessage event2 = new GenericDomainEventMessage<>("type", UUID.randomUUID().toString(), (long) 0,
                "Mock contents", MetaData.emptyInstance());
        DomainEventStream eventStream = DomainEventStream.of(event1, event2);
        //EventStore eventStore = Mockito.mock(EventStore.class);
        fixture.registerResource(commandGateway);
        fixture.registerResource(eventStore);
        productUpdateService = new ProductUpdateServiceImpl(commandGateway,eventStore);

        QueryGateway queryGateway = Mockito.mock(QueryGateway.class);

        fixture.registerResource(queryGateway);
        productFetchService = new ProductFetchServiceImpl(queryGateway, eventStore);
        //fixture.registerComm
        //CompletableFuture<Object> productIdCf = CompletableFuture.supplyAsync(() -> productId);
        CompletableFuture<Object> productIdCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productIdCf.get()).thenReturn(productId);
        Mockito.when(commandGateway.send(Mockito.any())).thenReturn(productIdCf);
        Mockito.when(eventStore.readEvents(productId)).thenReturn(eventStream);

        CompletableFuture<Object> productCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productCf.get()).thenReturn(product);
        Mockito.when(queryGateway.query(Mockito.any(), Mockito.any(ResponseType.class))).thenReturn(productCf);
        try {
            productUpdateService.createProductDetails(productDto);
            Assert.fail("Should have thrown an exception");
        } catch (ProductException e) {

            Assert.assertEquals(String.format("Product 12345 already exists"), e.getMessage());
        }

       /* ProductDto productDtoResponse = productFetchService.findByProductId(productId);
        Assert.assertEquals(productDto, productDtoResponse);*/

    }

    @Test
    public void product_fetch_test_when_data_exists() throws ProductException, InterruptedException, ExecutionException, JSONException, ProductException {
        CommandGateway commandGateway = Mockito.mock(CommandGateway.class);
        //DomainEventMessage event1 = new GenericDomainEventMessage();
        List<? extends DomainEventMessage<?>> testList = Mockito.mock(List.class);
        DomainEventStream eventStream = DomainEventStream.of(testList);
        //EventStore eventStore = Mockito.mock(EventStore.class);
        fixture.registerResource(commandGateway);
        fixture.registerResource(eventStore);
        productUpdateService = new ProductUpdateServiceImpl(commandGateway,eventStore);

        QueryGateway queryGateway = Mockito.mock(QueryGateway.class);

        fixture.registerResource(queryGateway);
        productFetchService = new ProductFetchServiceImpl(queryGateway, eventStore);
        //fixture.registerComm
        //CompletableFuture<Object> productIdCf = CompletableFuture.supplyAsync(() -> productId);
        CompletableFuture<Object> productIdCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productIdCf.get()).thenReturn(productId);
        Mockito.when(commandGateway.send(Mockito.any())).thenReturn(productIdCf);
        Mockito.when(eventStore.readEvents(productId)).thenReturn(eventStream);
        /*Mockito.when(eventStore
                .readEvents("34567")
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList()).size()).thenReturn(0);*/
        CompletableFuture<Object> productCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productCf.get()).thenReturn(product);
        Mockito.when(queryGateway.query(Mockito.any(), Mockito.any(ResponseType.class))).thenReturn(productCf);
        productDto.setProductId(productId);
            productUpdateService.createProductDetails(productDto);


        ProductDto productDtoResponse = productFetchService.findByProductId(productId);
        Assert.assertEquals(productDto, productDtoResponse);

    }

    @Test
    public void product_fetch_when_no_data_exists_test() throws InterruptedException, ExecutionException, JSONException {
        CommandGateway commandGateway = Mockito.mock(CommandGateway.class);
        EventStore eventStore = Mockito.mock(EventStore.class);
        fixture.registerResource(commandGateway);
        productUpdateService = new ProductUpdateServiceImpl(commandGateway,eventStore);

        QueryGateway queryGateway = Mockito.mock(QueryGateway.class);

        fixture.registerResource(queryGateway);
        productFetchService = new ProductFetchServiceImpl(queryGateway, eventStore);

        /*CompletableFuture<Object> productIdCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productIdCf.get()).thenReturn(productId);
        Mockito.when(commandGateway.send(Mockito.any())).thenReturn(productIdCf);*/

        CompletableFuture<Object> productCf = Mockito.mock(CompletableFuture.class);
        product.setProductId("23456");
        Mockito.when(productCf.get()).thenReturn(null);
        Mockito.when(queryGateway.query(Mockito.any(), Mockito.any(ResponseType.class))).thenReturn(productCf);


        //productUpdateService.createProductDetails(productDto);
        // ProductDto productDtoResponse = productFetchService.findByProductId("23456");
        try {
            productFetchService.findByProductId("23456");
            Assert.fail("Should have thrown an exception");
        } catch (ProductException e) {

            Assert.assertEquals(String.format("Product 23456 does not exists"), e.getMessage());
        }

    }
}
