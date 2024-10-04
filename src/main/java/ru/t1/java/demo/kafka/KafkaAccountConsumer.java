package ru.t1.java.demo.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.aop.Metric;
import ru.t1.java.demo.model.Account;
import ru.t1.java.demo.model.dto.AccountDto;
import ru.t1.java.demo.service.AccountService;
import ru.t1.java.demo.util.AccountMapper;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaAccountConsumer {
    private final AccountService accountService;
    @Metric
    @KafkaListener(id = "accountConsumerListener",
                   topics = "${t1.kafka.topic.demo_accounts}",
                   containerFactory = "accountKafkaListenerContainerFactory")
    public void listenAccounts(@Payload List<AccountDto> accountListDto, Acknowledgment ack) {
        log.debug("Account consumer: Обработка новых сообщений");
        try {
            List<Account> accounts = accountListDto.stream().map(AccountMapper::toEntity).toList();
            accountService.saveAccounts(accounts);
        } finally {
            ack.acknowledge();
        }
        log.debug("Account consumer: записи обработаны");
    }
}

