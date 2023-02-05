# springBootDemo
Building an Application with Spring Boot

###########################################

https://start.spring.io/;

mvn clean install -DskipTests=true
mvn clean install -DskipTests

###########################################

docker exec -it <containerId> psql -U postgres employeedb
\dt

select * from employee;

###########################################

psql -h localhost -U postgres -d postgres -p 5432

###########################################

docker-compose up
docker-compose down

###########################################
// Before that run postgressql db, e.g. via docker

docker build -f Dockerfile -t springwithpostgres:v1 .
docker run -p 8080:8080 aa -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgresqldb:5432/employeedb -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres -e SPRING_JPA_HIBERNATE_DDL_AUTO=update
