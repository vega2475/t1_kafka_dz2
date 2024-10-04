package ru.t1.java.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaErrorProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaErrorProducer(@Qualifier("defaultStringKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendErrorTrace(String topic, String message) {
        kafkaTemplate.send(topic, message);
        log.info("Отправлено сообщение об ошибке {} в топик {}", topic, message);
    }
}

