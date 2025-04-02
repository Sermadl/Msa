package com.itemservice.global.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itemservice.ItemserviceApplication;
import com.itemservice.model.entity.Item;
import com.itemservice.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = ItemserviceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EmbeddedKafka(partitions = 1, topics = {"db-connection-test"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaConsumerTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ItemRepository itemRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String topic = "db-connection-test";
    private final Long itemId = 123L;

//    @BeforeEach
//    void setUp() {
//        // 상품 초기화 (수량 100)
//        Item item = new Item(
//                itemId,
//                "테스트",
//                "테스트 상품",
//                100,
//                BigDecimal.valueOf(10000),
//                1L
//        );
//        itemRepository.save(item).block(); // 동기식으로 대기
//    }
//
//    @AfterEach
//    void cleanUp() {
//        itemRepository.deleteById(123L).block(); // 전체 삭제
//    }

    @Test
    void 동시성_테스트_메시지10개_전송하면_정확히_차감된다() throws Exception {
        Item before = itemRepository.findById(itemId).block();

        int messageCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(messageCount);

        for (int i = 0; i < messageCount; i++) {
            executor.submit(() -> {
                try {
                    Map<String, Object> message = Map.of(
                            "itemId", itemId,
                            "quantity", 1
                    );
                    String payload = objectMapper.writeValueAsString(message);
                    kafkaTemplate.send(topic, payload);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Kafka 소비 후 수량이 줄었는지 기다림 (최대 3초 대기)
        Thread.sleep(3000);

        // 검증
        Item result = itemRepository.findById(itemId).block();
        assertNotNull(result);
        assertEquals(before.getQuantity() - 10, result.getQuantity()); // 기존보다 10 작은 값
    }

}