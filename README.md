# movie-catalog-services

This is the microservice which is main part for movie-catalog-service.
Movie Catalog service call all other services.

Discovery Server - "https://github.com/coderboi88/Discovery-server"

Movie-info-Service - "https://github.com/coderboi88/microservices-springboot"

Rating data Service - "https://github.com/coderboi88/rating-data-service"

we use discovery server (eureka server) for the interconnection of the microservices.
All other repo except discovery server are eureka clients and register at eureka server(Discovery Server)

So to use this service we have to import all the above project also.
