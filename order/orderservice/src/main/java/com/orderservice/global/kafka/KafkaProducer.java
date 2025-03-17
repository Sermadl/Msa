package com.orderservice.global.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.orderservice.model.entity.OrderItem;
import com.orderservice.model.entity.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()); // ✅ LocalDateTime 지원 추가
    private final String DB_TOPIC = "db-connection-test";

    public OrderItem sendDbUpdateMessage(OrderItem item) {
        String jsonInString = Strings.EMPTY;
        try {
            jsonInString = mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        kafkaTemplate.send(DB_TOPIC, jsonInString);
        log.info("Kafka producer send data from orders microservice: {}", jsonInString);
        return item;
    }
}
