package com.example.springBootDemo.kafka;

import com.example.springBootDemo.dto.EmployeeRequest;
import com.example.springBootDemo.kafka.producer.EmployeeKafkaProducer;
import com.example.springBootDemo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@Testcontainers
@SpringBootTest
class EmployeeKafkaTestcontainersTest {

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:test");
        registry.add("spring.datasource.driverClassName", () -> "org.h2.Driver");
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "secret");
        registry.add("spring.flyway.enabled", () -> "false");
    }

    @Autowired
    private EmployeeKafkaProducer employeeKafkaProducer;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void testProduceAndConsumeKafkaMessage() {
        final String empName = "John";

        ArgumentCaptor<EmployeeRequest> captor = ArgumentCaptor.forClass(EmployeeRequest.class);
        EmployeeRequest employeeRequest = new EmployeeRequest(empName);

        employeeKafkaProducer.writeToKafka(employeeRequest);

        verify(employeeService, timeout(5000)).addEmployee(captor.capture());
        assertNotNull(captor.getValue());
        assertEquals(empName, captor.getValue().getName());
    }
}
