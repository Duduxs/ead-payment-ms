<h4 align="center">
  <p>Ead Payment</p>
  
  <p>Business Project of the ead's microservice group</p>
  
  <p>It was created in order to manages users's payments</p>
  
</h4>

<p align="center">
  <a href="#rocket-technologies">Technologies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#information_source-how-to-use">How To Use</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#thumbsup-how-to-contribute">How To Contribute</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#memo-license">License</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#handshake-ead-ecosystem">Ead Ecosystem</a>
</p>

<p align="center">
<img alt="Collage" src="https://ik.imagekit.io/27ewoxssse/payment-ms_B_hJ44tgL.png?updatedAt=1687612031983"> 
</p>

## :rocket: Technologies

This project was developed with the following technologies:

- API:

  - [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
  - [Kotlin](https://kotlinlang.org/)
  - [JJWT](https://github.com/jwtk/jjwt)
  - [Postgres](https://www.postgresql.org/)
  - [Spring Web](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html)
  - [Spring JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
  - [RabbitMQ](https://www.rabbitmq.com/)
  - [Spring Validation](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html)
  - [Spring AMQP](https://spring.io/projects/spring-amqp)
  - [Spring Security](https://spring.io/projects/spring-security)
  - [Spring Actuator](https://docs.spring.io/spring-boot/docs/2.5.6/reference/html/actuator.html)
  - [Spring Cloud](https://spring.io/projects/spring-cloud)
  - [Spring Cloud Config Server](https://spring.io/projects/spring-cloud-config#overview)
  - [Spring Eureka Client](https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html)

  
- Utils:

  - [Spring Log4j2](https://docs.spring.io/spring-boot/docs/2.1.18.RELEASE/reference/html/boot-features-logging.html)
  - [Docker](https://www.docker.com/)
  - [Docker Compose](https://docs.docker.com/compose/)
  
- Tests:

  - [H2](https://h2database.com/html/main.html)


## :information_source: How to use
To clone and run these configurations, you'll need [Git](https://git-scm.com), From your command line:

```bash
# Clone this repository
$ git clone https://github.com/Duduxs/ead-payment-ms

# Now clone the service-registry repo
$ git clone https://github.com/Duduxs/ead-service-registry-ms

# And config-server repos
$ git clone https://github.com/Duduxs/ead-config-server-ms
$ git clone https://github.com/Duduxs/ead-config-server-repo-ms

```

To run payment:

```bash
# Execute the main class PaymentApplication with dev profile (Be sure to have already service registry and config server up) 
```

## :thumbsup: How To Contribute

-  Make a fork;
-  Create a branch with your feature: `git checkout -b my-feature`;
-  Commit changes: `git commit -m 'feat: My new feature'`;
-  Make a push to your branch: `git push origin my-feature`.

## :memo: License
This project is under the MIT license. See the [LICENSE](https://github.com/Duduxs/ead-payment-ms/blob/main/LICENSE) for more information.

## :handshake: Ead Ecosystem
Take a look into others microservices that were developed for the EAD Plataform.
 
 - Cross Cuting Microservices
    - <a href="https://github.com/Duduxs/ead-service-registry-ms">Service Registry</a>
    - <a href="https://github.com/Duduxs/ead-config-server-repo-ms">Config Server</a>
    - <a href="https://github.com/Duduxs/ead-config-server-ms">Config Server (Impl)</a>
    - <a href="https://github.com/Duduxs/ead-api-gateway-ms">Gateway</a>
- Business Microservices
    - <a href="https://github.com/Duduxs/ead-authuser-ms">Auth User</a>
    - <a href="https://github.com/Duduxs/ead-course-ms">Course</a>
    - <a href="https://github.com/Duduxs/ead-notification-ms">Notification</a>
- Study case Microservices
    - <a href="https://github.com/Duduxs/ead-notification-hex-ms">Notification With Hexagonal Architecture</a>
    
---

<h4 align="center">
    Made by Eduardo José 😆 <a href="https://www.linkedin.com/in/eduarddojose/" target="_blank">Contact me!</a>
</h4>
