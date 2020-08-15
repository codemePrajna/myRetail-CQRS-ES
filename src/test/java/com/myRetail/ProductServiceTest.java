package com.myRetail;

import com.myRetail.dto.ProductDto;
import com.myRetail.entity.Product;
import com.myRetail.entity.ProductRequest;
import com.myRetail.service.ProductFetchService;
import com.myRetail.service.ProductUpdateService;
import com.myRetail.service.impl.ProductFetchServiceImpl;
import com.myRetail.service.impl.ProductUpdateServiceImpl;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.codehaus.jettison.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @InjectMocks
    ProductFetchServiceImpl productFetchService;

    @InjectMocks
    ProductUpdateServiceImpl productUpdateService;

    @Mock
    QueryGateway queryGateway;

    private String productId;
    private Product product = new Product();
    private ProductDto productDto = new ProductDto();

    @Before
    public void setUp() {
        productId = "12345";
        product.setProductId(productId);
        product.setPrice(15.0);
        product.setCurrency("USD");

        productDto.setProductId(productId);
        productDto.setProductName("abc");
        productDto.setPrice(15.0);
        productDto.setCurrency("USD");
    }

    @Test
    public void product_fetch_service_test() throws ExecutionException, InterruptedException, JSONException {
        CompletableFuture<Product> productCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productCf.get()).thenReturn(product);
        Mockito.when(queryGateway.query(new ProductRequest(productId),
                ResponseTypes.instanceOf(Product.class))).thenReturn(productCf);
        Assert.assertEquals(product,productFetchService.findByProductId(productId));

    }
}
