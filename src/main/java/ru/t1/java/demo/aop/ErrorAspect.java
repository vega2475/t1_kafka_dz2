package ru.t1.java.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.kafka.KafkaErrorProducer;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ErrorAspect {

    private final KafkaErrorProducer kafkaErrorProducer;

    @Value("${t1.kafka.topic.demo_error}")
    private String errorTopic;

    public ErrorAspect(KafkaErrorProducer kafkaErrorProducer) {
        this.kafkaErrorProducer = kafkaErrorProducer;
    }

    @AfterThrowing(pointcut = "execution(* ru.t1.java.demo..*(..)) && !execution(* ru.t1.java.demo.util.AuthTokenFilter.*(..))", throwing = "ex")
    public void logAndSendError(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        String args = (methodArgs != null && methodArgs.length > 0) ? Arrays.toString(methodArgs) : "No arguments";

        String stackTrace = Arrays.toString(ex.getStackTrace());

        String errorMessage = String.format(
                "Method: %s, Arguments: %s, Exception: %s, StackTrace: %s",
                methodName, args, ex.getMessage(), stackTrace
        );

        log.error("Ошибка в методе {} с аргументами {}: {}", methodName, args, ex.getMessage());

        if (kafkaErrorProducer != null && errorTopic != null) {
            try {
                kafkaErrorProducer.sendErrorTrace(errorTopic, errorMessage);
            } catch (Exception e) {
                log.error("Ошибка при отправке сообщения в Kafka: {}", e.getMessage());
            }
        } else {
            log.error("Kafka producer или topic не инициализированы");
        }
    }

}

