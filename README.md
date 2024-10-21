# SpringBoot-Ecommerce-Eureka-Microservice

This project contains microservice architecture under diagrams/micro-services.drawio

Contains below Microservices:

  1) config-server : Contains centralized configuration under classpath. Runs on port 8888
  1) eureka-server : Used for service discovery. Microservices, both providers and consumers, can query the Eureka registry to locate and communicate with other services seamlessly. Runs on port 8761
  2) cusotmer: Customer Microservice running on port 8090
  3) product: Product Microservice running on port 8050
