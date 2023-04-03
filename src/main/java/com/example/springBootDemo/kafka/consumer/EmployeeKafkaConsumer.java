package com.example.springBootDemo.kafka.consumer;

import com.example.springBootDemo.dto.EmployeeRequest;
import com.example.springBootDemo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeKafkaConsumer {

    private final EmployeeService employeeService;

    public EmployeeKafkaConsumer(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            concurrency = "${spring.kafka.consumer.level.concurrency:3}")
    public void logKafkaMessages(@Payload EmployeeRequest employeeRequest,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                                 @Header(KafkaHeaders.OFFSET) Long offset) {
        log.info("Received a message contains a employeeRequest information with name {}, from {} topic, " +
                "{} partition, and {} offset", employeeRequest.getName(), topic, partition, offset);
        employeeService.addEmployee(employeeRequest);
    }
}
