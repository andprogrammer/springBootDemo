### SpringBoot Demo Application -> springBootDemo

Building an Application with Spring Boot

JDK17, Springboot 3.0.3

Open the browser `localhost:8080`.

###########################################

[Spring Initializer](https://start.spring.io/)

###########################################

```bash
$ mvn clean install -DskipTests=true
```

```bash
$ mvn clean install -DskipTests
```

###########################################

```bash
$ docker exec -it <containerId> psql -U postgres employeedb
```

```bash
$ \dt
```

```bash
$ select * from employee;
```

###########################################

```bash
$ psql -h localhost -U postgres -d postgres -p 5432
```

###########################################

```bash
$ docker-compose up
```

```bash
$ docker-compose down
```

###########################################

##### Postgressql db via docker

```bash
$ docker build -f Dockerfile -t springwithpostgres:v1 .
```

```bash
$ docker run -p 8080:8080 aa -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgresqldb:5432/employeedb -e
```

```bash
$ SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres -e SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

###########################################

##### Rest Api curl(s) examples

```bash
$ curl -H "Content-Type: application/json" -X POST -d {\"name\":\"Skywalker\"} http://localhost:8080/api/employee
```

```bash
$ curl -H "Content-Type: application/json" -X GET http://localhost:8080/api/employee
```

###########################################

##### TODO

- add swagger

- add ELK

- add flyway

###########################################
