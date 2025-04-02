package com.itemservice.global.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itemservice.model.entity.Item;
import com.itemservice.repository.ItemRepository;
import com.itemservice.service.error.ItemNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.xml.catalog.Catalog;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ItemRepository itemRepository;

    @Transactional
    @KafkaListener(topics = "db-connection-test")
    public Mono<Void> processMessage(String kafkaMessage) {
        log.info("Kafka Message: ======> \n{}", kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(kafkaMessage, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        Long targetItemId = Long.parseLong(String.valueOf(Optional.of(map.get("itemId"))
                .orElseThrow(ItemNotFoundException::new)));
        Integer soldQuantity = (Integer) Optional.of(map.get("quantity"))
                .orElseThrow(() -> new IllegalStateException("Not found quantity"));

        return itemRepository.findByIdForUpdate(targetItemId)
                .switchIfEmpty(Mono.error(new ItemNotFoundException()))
                .flatMap(item -> {
                    item.changeQuantity(item.getQuantity() - soldQuantity);
                    return itemRepository.save(item);
                })
                .doOnSuccess(item -> log.info("item({}) process completed", targetItemId))
                .then();
    }
}
