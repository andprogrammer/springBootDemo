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

# If it does not work try execute above cmd twice
```

```bash
$ docker-compose down
```

###########################################

##### Alternative run via maven. Please remember about running postgres with proper configuration

```bash
$ spring-boot:run
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

##### Swagger url

http://localhost:8080/swagger-ui/index.html


###########################################

##### Kubernetes
## 1 replica of SpringBoot app as well as Postgresql db

My dockerhub account -> dokcer3

```bash
docker build -f Dockerfile -t dokcer3/springbootdemov1:1.0 .
docker push dokcer3/springbootdemov1:1.0


# Clean up before running -> not required. Only in some specific cases.

kubectl delete pvc --all
kubectl delete all --all

kubectl apply -f postgresql-claim0-persistentvolume.yaml
kubectl apply -f postgresql-claim0-persistentvolumeclaim.yaml
kubectl apply -f postgresql-initial-data-configmap.yaml
kubectl apply -f postgresql-deployment.yaml
kubectl apply -f postgresql-service.yaml


kubectl apply -f springbootdemov1-deployment.yaml
kubectl apply -f springbootdemov1-service.yaml


minikube service springbootdemov1-service


http://192.168.49.2:31995/api/employee

curl -H "Content-Type: application/json" -X POST -d {\"name\":\"Skywalker\"} http://192.168.49.2:31995/api/employee


# Clean up after running -> not required. Only in some specific cases.

kubectl delete -f postgresql-claim0-persistentvolume.yaml
kubectl delete -f postgresql-claim0-persistentvolumeclaim.yaml
kubectl delete -f postgresql-initial-data-configmap.yaml
kubectl delete -f postgresql-deployment.yaml
kubectl delete -f postgresql-service.yaml


kubectl delete -f springbootdemov1-deployment.yaml
kubectl delete -f springbootdemov1-service.yaml

```

###########################################

##### TODO

- add ELK

- add flyway

###########################################
