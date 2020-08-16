package com.myRetail;

import com.myRetail.dto.ProductDto;
import com.myRetail.entity.Product;
import com.myRetail.exception.ProductException;
import com.myRetail.response.Response;
import com.myRetail.service.impl.ProductFetchServiceImpl;
import com.myRetail.service.impl.ProductUpdateServiceImpl;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.test.saga.FixtureConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
//@Import(SecurityConfig.class)
public class ProductIntegrationTest {

    @InjectMocks
    ProductFetchServiceImpl productFetchService;

    @InjectMocks
    ProductUpdateServiceImpl productUpdateService;

    @Mock
    FixtureConfiguration fixture;

    @Autowired
    private MockMvc mockMvc;


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
    public void contextLoads() {
    }

    @Test
    @WithMockUser
    public void fetch_test() throws Exception, ProductException {
        EventStore eventStore = Mockito.mock(EventStore.class);
        CommandGateway commandGateway = Mockito.mock(CommandGateway.class);
        fixture.registerResource(commandGateway);
        productUpdateService = new ProductUpdateServiceImpl(commandGateway,eventStore);

        QueryGateway queryGateway = Mockito.mock(QueryGateway.class);

        fixture.registerResource(queryGateway);
        productFetchService = new ProductFetchServiceImpl(queryGateway, eventStore);
        //fixture.registerComm
        //CompletableFuture<Object> productIdCf = CompletableFuture.supplyAsync(() -> productId);
        CompletableFuture<Object> productIdCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productIdCf.get()).thenReturn(productId);
        Mockito.when(commandGateway.send(Mockito.any())).thenReturn(productIdCf);

        CompletableFuture<Object> productCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productCf.get()).thenReturn(product);
        Mockito.when(queryGateway.query(Mockito.any(), Mockito.any(ResponseType.class))).thenReturn(productCf);

        Mockito.when(productFetchService.findByProductId(productId)).thenReturn(productDto);
        this.mockMvc.perform(get("/api/v1/product/13860428")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message.productName").value("testPrajna"));
        //System.out.println(mvcResult.getResponse().getContentAsString());
        //System.out.println(mvcResult.getResponse().getContentAsString().contains("$.message"));
        //.andExpect(content().json("{\"productId\":\"13860428\",\"productName\":\"testPrajna\",\"price\":58.0,\"currency\":\"USD\"}"));
        // mvcResult.getResponse().getContentAsString();
    }

    @Test
    @WithMockUser
    public void fetch_when_no_data_found_test() throws Exception, ProductException {
        CommandGateway commandGateway = Mockito.mock(CommandGateway.class);
        EventStore eventStore = Mockito.mock(EventStore.class);
        fixture.registerResource(commandGateway);
        productUpdateService = new ProductUpdateServiceImpl(commandGateway,eventStore);

        QueryGateway queryGateway = Mockito.mock(QueryGateway.class);

        fixture.registerResource(queryGateway);
        productFetchService = new ProductFetchServiceImpl(queryGateway, eventStore);
        //fixture.registerComm
        //CompletableFuture<Object> productIdCf = CompletableFuture.supplyAsync(() -> productId);
        CompletableFuture<Object> productIdCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productIdCf.get()).thenReturn(productId);
        Mockito.when(commandGateway.send(Mockito.any())).thenReturn(productIdCf);

        CompletableFuture<Object> productCf = Mockito.mock(CompletableFuture.class);
        Mockito.when(productCf.get()).thenReturn(product);
        Mockito.when(queryGateway.query(Mockito.any(), Mockito.any(ResponseType.class))).thenReturn(productCf);
        try {
            Mockito.when(productFetchService.findByProductId(productId)).thenReturn(productDto);

            this.mockMvc.perform(get("/api/v1/product/13860426")).andDo(print()).andReturn();
        } catch (ProductException e) {

            Assert.assertEquals(String.format("Product 13860426 does not exists"), e.getMessage());
        }
        //System.out.println(mvcResult.getResponse().getContentAsString());
        //System.out.println(mvcResult.getResponse().getContentAsString().contains("$.message"));
        //.andExpect(content().json("{\"productId\":\"13860428\",\"productName\":\"testPrajna\",\"price\":58.0,\"currency\":\"USD\"}"));
        // mvcResult.getResponse().getContentAsString();
    }

}
