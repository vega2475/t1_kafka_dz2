package ru.t1.java.demo.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.kafka.KafkaMetricProducer;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class MetricAspect {

    private final KafkaMetricProducer kafkaMetricProducer;

    @Value("${t1.metric.ms}")
    private long hold;

    @Value("${t1.kafka.topic.demo_metric}")
    private String metricTopic;

    @Around("@annotation(Metric)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        if (executionTime > hold) {
            String methodName = joinPoint.getSignature().toShortString();
            String methodArgs = Arrays.toString(joinPoint.getArgs());

            String message = String.format("Method %s executed in %d ms with params: %s",
                    methodName, executionTime, methodArgs);

            kafkaMetricProducer.sendMetric(metricTopic, message);
        }

        return result;
    }
}

