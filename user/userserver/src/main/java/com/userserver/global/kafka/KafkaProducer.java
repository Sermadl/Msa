package com.userserver.global.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.userserver.model.entity.User;
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
    private final ObjectMapper mapper = new ObjectMapper();
    private final String DB_TOPIC = "db-connection-test";

    public User sendDbUpdateMessage(User user) {
        String jsonInString = Strings.EMPTY;
        try {
            jsonInString = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        kafkaTemplate.send(DB_TOPIC, jsonInString);
        log.info("Kafka producer send data from user microservice: {}", user);
        return user;
    }
}
