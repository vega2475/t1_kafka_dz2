package ru.t1.java.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@Slf4j
public class KafkaMetricProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMetricProducer(@Qualifier("defaultStringKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMetric(String topic, String message) {
        kafkaTemplate.send(topic, message);
        log.info("Сообщение {} отправлено в топик {}", message, topic);
    }
}

