# My Retail Service

My Retail Service provides set of API endpoints to fetch and update product details

## Use Case:
myRetail RESTful service (Target case Study POC)

myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention. Build an application that performs the following actions:

Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number.
Example product IDs: 15117729, 16483589, 16696652, 16752456, 15643793)
Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail)
Example: http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics
Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response.
BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store

## Tech Stack

- Oracle open JDK 14
- Maven 3.8.1
- Axon 4.4
- Junit 3
- Spring boot 2.2.5
- RestTemplate
- Embedded Mongo DB 3.2.2
- Swagger 2

## Design and Pattern
This codebase describes a way of architecting highly scalable and available applications 
that is based on microservices,event sourcing (ES) and command query responsibility segregation (CQRS). 
Applications consist of loosely coupled components that communicate using events. 
These components can be deployed either as separate services or packaged as a monolithic application for simplified development and testing.

## RoadMap
- Separation of Command and Query services
- Caching
- Pagination
- Resiliency
- kafka for inter service communication
- DB sharding and scaling for distributed approach

## To run the application
Download the project. Navigate to the myRetail project folder and run mvn spring-boot:run (RetailServiceApplication) Read the API documentation in Swagger2 (run http://localhost:8080/swagger-ui.html on the browser)
