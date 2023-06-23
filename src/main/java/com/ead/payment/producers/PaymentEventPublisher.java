package com.ead.payment.producers;

import com.ead.payment.dtos.PaymentEventDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    @Autowired
    RabbitTemplate template;

    @Value("${ead.broker.exchange.paymentEventExchange.name}")
    private String exchange;

    public void publishEventCommand(PaymentEventDTO dto) {
        template.convertAndSend(exchange, "", dto);
    }


}
