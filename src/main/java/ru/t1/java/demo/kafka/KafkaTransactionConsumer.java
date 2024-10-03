package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.model.dto.TransactionDto;
import ru.t1.java.demo.service.TransactionService;
import ru.t1.java.demo.util.TransactionMapper;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaTransactionConsumer {
    //TODO - сделать так что бы этот лисенер был виден

    private final TransactionService transactionService;


    @KafkaListener(id = "transactionConsumerListener",
            topics = "${t1.kafka.topic.demo_transactions}",
            containerFactory = "transactionKafkaListenerContainerFactory")
    public void listenTransactions(@Payload List<TransactionDto> transactionListDto, Acknowledgment ack) {
        log.debug("Transaction consumer: Обработка новых сообщений");
        try {
            List<Transaction> transactions = transactionListDto.stream()
                    .map(TransactionMapper::toEntity)
                    .toList();
            transactionService.saveTransactions(transactions);
        } finally {
            ack.acknowledge();
        }
        log.debug("Transaction consumer: записи обработаны");
    }
}

