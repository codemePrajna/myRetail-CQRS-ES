package com.myRetail.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

//@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
    @Override
    public String getDatabaseName() {
        return "retailDb";
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        ServerAddress address = new ServerAddress("localhost", 27017);
        MongoCredential credential = MongoCredential.createCredential("prajnah", getDatabaseName(), "password".toCharArray());
        MongoClientOptions options = new MongoClientOptions.Builder().build();

        MongoClient client = new MongoClient(address, credential, options);
        return client;
    }
}
