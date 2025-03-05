package com.orderservice.global.kafka;

import com.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final OrderRepository orderRepository;

    @Transactional
    @KafkaListener(topics = "db-connection-test")
    public void processMessage(String kafkaMessage) {
        log.info("Kafka Message: ======> {}", kafkaMessage);

//        Map<Object, Object> map = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            map = mapper.readValue(kafkaMessage, new TypeReference<>() {});
//        } catch (JsonProcessingException e) {
//            log.error(e.getMessage());
//        }

//        Long targetItemId = (Long) Optional.of(map.get("itemId"))
//                .orElseThrow(() -> new IllegalStateException("Not found itemId"));
//        Orders order = orderRepository.findById(targetItemId)
//                .orElseThrow(() -> new IllegalStateException("Item Not Found"));
//        int soldQuantity = (int) Optional.of(map.get("quantity"))
//                .orElseThrow(() -> new IllegalStateException("Not found quantity"));

    }
}
