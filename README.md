### SpringBoot Demo Application -> springBootDemo

Building an Application with Spring Boot.

JDK17, Springboot 3.0.3

Open the browser `localhost:8080`.

Branch **addKafka** contains Kafka Message Broker implementation with unit & integration tests.

###########################################

[Spring Initializer](https://start.spring.io/)

###########################################

##### Build jar file -> springBootDemo-0.0.1-SNAPSHOT.jar

```bash
$ mvn clean install -DskipTests=true
```

```bash
$ mvn clean install -DskipTests
```

###########################################

##### Build SpringBoot Demo App from Dockerfile. Please do it before docker-compose up if you make any changes in the backend code

```bash
$ docker build -f Dockerfile -t springbootdemov1 .
```

##### If you do not want to start postgresql from docker-compose

```bash
$ docker run -p 8080:8080 aa -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgresqldb:5432/employeedb -e
```

##### Other

```bash
$ SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres -e SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

###########################################

##### Connect to running postgresql container and operate on it

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

##### Connect to running postgresql as a client from localhost

```bash
$ psql -h localhost -U postgres -d postgres -p 5432
```

###########################################

##### Build SpringBoot App from docker-compose.yml file

```bash
$ docker-compose up
```

```bash
$ docker-compose down
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
