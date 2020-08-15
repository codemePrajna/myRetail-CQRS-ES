package com.myRetail.repository;

import com.myRetail.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByProductId(String productId);
}
