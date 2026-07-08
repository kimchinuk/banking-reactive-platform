package com.example.bank.risk.messaging;

import com.example.bank.common.event.LoanDecisionEvent;
import com.example.bank.common.event.LoanEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka is the asynchronous boundary, so the API caller is not waiting during this work.
 *
 * For demo purposes we use simple threshold-based decisioning. In real banks this might call
 * credit engines, bureau data, income validation, fraud engines, or policy services.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RiskDecisionConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "loan-events", groupId = "risk-service-group", properties = {
            "spring.json.value.default.type=com.example.bank.common.event.LoanEvent"
    })
    public void onLoanEvent(LoanEvent event) {
        String decision = event.amount() <= 50000 ? "APPROVED" : "REFERRED";
        String reason = event.amount() <= 50000 ? "Auto-approved under threshold" : "Manual review required";
        kafkaTemplate.send("loan-decisions", event.applicationId(), new LoanDecisionEvent(event.applicationId(), event.customerId(), decision, reason));
        log.info("Decision published for applicationId={} decision={}", event.applicationId(), decision);
    }
}
