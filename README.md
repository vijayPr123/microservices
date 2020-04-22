# Microservices with Spring Boot

### Details of our project POC 
*(as on 07 April 2020 1700 hrs)*

This project consists of the below services:
* **services-naming-registry** - this is the master service registry naming server managed by Eureka. All services should register to this one first.
* **api-gateway** - this is the api gateway, proxy and load balancer service managed by Zuul. This is the entrypoint of all user requests, 
      which are then mapped and forwarded to actual services.
* **user-authentication-service** - this is user authnetication service which validates user credentials, and generates JWT tokens if valid.
* **test-getgallery** and **test-getimages** - these are two sample microservices which work in integration and cohesion with each other, along with above services. 
* **common-utility-service** - if any modules are to be used or shared among multiple services, that code is to be written here, without duplicating it in every service.



### Source of this project from GitHub

GitHub project link for future reference
[GitHub](https://github.com/OmarElGabry/microservices-spring-boot)


The series of articles can be found below:
- [Microservices with Spring Boot — Intro to Microservices (Part 1)](https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-intro-to-microservices-part-1-c0d24cd422c3)
- [Microservices with Spring Boot — Creating our Microserivces & Gateway (Part 2)](https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-creating-our-microserivces-gateway-part-2-31f8aa6b215b)
- [Microservices with Spring Boot — Authentication with JWT (Part 3)](https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-authentication-with-jwt-part-3-fafc9d7187e8)
- [Microservices with Spring Boot — Circuit Breaker & Log Tracing (Part 4)](https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-circuit-breaker-log-tracing-part-4-9cdf5e898988)


### See also

- JWT Spring Security Demo [GitHub](https://github.com/szerhusenBC/jwt-spring-security-demo) 
```
Flyway Docker Image :
https://hub.docker.com/r/hubae/jwt-spring-security-demo/
```






