package com.myRetail;

import com.myRetail.entity.Product;
import com.myRetail.entity.ProductRequest;
import com.myRetail.exception.ProductException;
import com.myRetail.projection.ProductProjection;
import com.myRetail.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
public class ProductProjectionTest {

    @InjectMocks
    ProductProjection productProjection;

    @Mock
    ProductRepository repository;

    private String productId;
    private Product product = new Product();

    @Before
    public void setUp() {
        productId = "12345";
        product.setProductId(productId);
        product.setPrice(15.0);
        product.setCurrency("USD");
    }

    @Test
    public void product_projection_test(){
        ProductRequest productRequest = new ProductRequest("12345");
        Mockito.when(repository.findByProductId(productId)).thenReturn(product);
        Assert.assertEquals(product, productProjection.handle(productRequest));
    }

    @Test
    public void product_projection_return_null_when_product_not_exists_test() {
        ProductRequest productRequest = new ProductRequest("12345");
        Mockito.when(repository.findByProductId(productId)).thenReturn(null);
        Assert.assertEquals(null, productProjection.handle(productRequest));
    }
}
