package com.myRetail.service.impl;

import com.myRetail.dto.ProductDto;
import com.myRetail.entity.Product;
import com.myRetail.entity.ProductRequest;
import com.myRetail.service.ProductFetchService;
import com.myRetail.util.ProductUtil;
//import com.myRetail.util.TrackTimeUtil;
//import com.myRetail.util.TrackTimeUtil;
import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

//@AllArgsConstructor
@Service
@Slf4j
public class ProductFetchServiceImpl implements ProductFetchService {
    private final QueryGateway queryGateway;
    private final EventStore eventStore;

    public ProductFetchServiceImpl(QueryGateway queryGateway,EventStore eventStore){
        this.queryGateway = queryGateway;
        this.eventStore = eventStore;
    }

    @Autowired
    RestTemplate restTemplate;
    @Value("${target.product.url}")
    String targetUrl;

    /**
     * Function that consolidates the product information from repo and event to construct a complete requested information of a product
     * @param productId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws JSONException
     */
    @Override
   // @TrackTimeUtil
    public ProductDto findByProductId(String productId) throws ExecutionException, InterruptedException, JSONException {
        CompletableFuture<Product> productCf = this.queryGateway.query(
                new ProductRequest(productId),
                ResponseTypes.instanceOf(Product.class)
        );

        Product product = productCf.get();
        return constructProduct(product);

    }

    /**
     * List the sequence of event on the product update/create
     * @param productId
     * @return
     */
   // @TrackTimeUtil
    public List<Object> listEventsForProduct(String productId) {
        return this.eventStore
                .readEvents(productId)
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }

    //@TrackTimeUtil
    private String fetchProductTitle(String productId) throws JSONException {
        String productName = "testPrajna";
        /*ResponseEntity<String> productResponse = restTemplate.getForEntity(getTargetURL(productId), String.class);
        if (productResponse != null) {
            JSONObject jsonObject = new JSONObject(productResponse.getBody());
            if (jsonObject.getJSONObject("product").getJSONObject("item").getJSONObject("product_description") != null) {
                JSONObject productDescription = jsonObject.getJSONObject("product").getJSONObject("item").getJSONObject("product_description");
                productName = productDescription.getString("title");
            }

        }*/
        return productName;
    }
    private String getTargetURL(String productId) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(targetUrl + productId);
        return uriComponentsBuilder.toUriString();
    }

    private ProductDto constructProduct(Product product) throws JSONException {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setPrice(product.getPrice());
        productDto.setCurrency(product.getCurrency());
        productDto.setProductName(fetchProductTitle(product.getProductId()));
        return productDto;
    }
}
