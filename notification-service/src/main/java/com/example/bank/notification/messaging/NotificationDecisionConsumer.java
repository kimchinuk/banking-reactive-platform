package com.example.bank.notification.messaging;

import com.example.bank.common.event.LoanDecisionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * This service is intentionally separate so user communication is decoupled from business decisioning.
 */
@Slf4j
@Component
public class NotificationDecisionConsumer {

    @KafkaListener(topics = "loan-decisions", groupId = "notification-service-group", properties = {
            "spring.json.value.default.type=com.example.bank.common.event.LoanDecisionEvent"
    })
    public void onDecision(LoanDecisionEvent event) {
        log.info("Notification sent applicationId={} customerId={} decision={} reason={}",
                event.applicationId(), event.customerId(), event.decision(), event.reason());
    }
}
