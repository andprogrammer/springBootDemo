package com.example.springBootDemo.kafka.producer;

import com.example.springBootDemo.dto.EmployeeRequest;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeKafkaProducer {

    private final KafkaTemplate<String, EmployeeRequest> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topic;

    @Value("${spring.kafka.replication.factor:1}")
    private int replicationFactor;

    @Value("${spring.kafka.partition.number:1}")
    private int partitionNumber;

    public EmployeeKafkaProducer(KafkaTemplate<String, EmployeeRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void writeToKafka(EmployeeRequest employeeRequest) {
        kafkaTemplate.send(topic, employeeRequest);
    }

    @Bean
    @Order(-1)
    public NewTopic createNewTopic() {
        return new NewTopic(topic, partitionNumber, (short) replicationFactor);
    }
}
