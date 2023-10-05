# Redcare Pharmacy Software Engineering, Java: Practical Task

---
### _**Gothub**_ platform is similar to Github, it manages repositories and ratings

### Author: Tamir Myblat ( tamirmayb@gmail.com )

### The services use Java 17 Spring Boot 2.7.5, Open API 3, Maven 3 and h2 as local db

---
# Build guide

### Testing
First check that you are able to compile and pass the tests:
```
mvn clean test
```

### Application Start

To run the backend API locally:

```
mvn spring-boot:run
```

Otherwise, you can build a jar here:

```
mvn clean install 
java -jar target/gothub-1.0-SNAPSHOT.jar
```

# Server check

To access to the H2 database in dev mode:

Go to http://localhost:8080/h2-console 

If you cannot access the console try to set JDBC URL to `jdbc:h2:mem:testdb`

API Documentation (Swagger) can be accessed here:

http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui.html

## Fetching Repositories
* The H2 db should already have a few default repositories.
* I Assume that this service does not create or changes repositories, it's only purpose is to fetch repositories as described on the task details. 
  * You can change / add repositories directly to the db as needed.
* Run http://localhost:8080/api/search/repositories to fetch without any searchable parameters.
* Run http://localhost:8080/api/search/repositories?limit=100&from=2000-01-01&lang=Java
  * This will fetch repositories filtered by search params ```limit, from, lang```
  * Please note that:
    * Search params are all non-mandatory  
    * Limit has default value of 10
    * From should be formatted ```yyyy-MM-dd```
    * Lang parameter (plain string for now) searches by used language of the repository the default languages are:
      * ```Java```
      * ```TS```
      * ```Golang```

