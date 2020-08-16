package com.myRetail;

import com.myRetail.controller.ProductFetchController;
import com.myRetail.dto.ProductDto;
import com.myRetail.entity.Product;
import com.myRetail.exception.ProductException;
import com.myRetail.response.Response;
import com.myRetail.service.impl.ProductFetchServiceImpl;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
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

@RunWith(SpringRunner.class)
public class ProductControllerTest {

    @Mock
    ProductFetchServiceImpl productFetchService;

    @InjectMocks
    ProductFetchController productFetchController;
    @Mock
    FixtureConfiguration fixture;
    private ProductDto productDto = new ProductDto();
    private String productId;
    private Product product = new Product();

    @Before
    public void setUp() {
        productId = "12345";

        product.setProductId(productId);
        product.setPrice(15.0);
        product.setCurrency("USD");

        productDto.setProductId(productId);
        productDto.setProductName("testPrajna");
        productDto.setPrice(15.0);
        productDto.setCurrency("USD");
    }

    @Test
    public void fetch_controller_test() throws InterruptedException, ExecutionException, ProductException, JSONException {
        QueryGateway queryGateway = Mockito.mock(QueryGateway.class);
        EventStore eventStore = Mockito.mock(EventStore.class);

        fixture.registerResource(queryGateway);
        productFetchService = new ProductFetchServiceImpl(queryGateway, eventStore);

        CompletableFuture<Object> productCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productCf.get()).thenReturn(product);
        Mockito.when(queryGateway.query(Mockito.any(), Mockito.any(ResponseType.class))).thenReturn(productCf);

        Mockito.when(productFetchService.findByProductId(productId))
                .thenAnswer(i -> {
                    CompletableFuture<Product> response = new CompletableFuture<Product>();
                    response.complete(product);
                    return response;
                });

        // Act
        ResponseEntity<Response<ProductDto>> answer = productFetchController.fetchProduct(productId);

        // Assert
        //Mockito.verify(productFetchService, Mockito.times(1)).findByProductId(Mockito.any(String.class));
        //Mockito.verifyNoMoreInteractions(productFetchService);
        Assert.assertEquals(200, answer.getBody().getStatus());
    }
}
