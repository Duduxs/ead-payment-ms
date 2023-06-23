package com.ead.payment.producers;

import com.ead.payment.dtos.PaymentCommandDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentCommandPublisher {

    @Autowired
    RabbitTemplate template;

    @Value("${ead.broker.exchange.paymentCommandExchange.name}")
    private String exchange;

    @Value("${ead.broker.queue.key.paymentCommand}")
    private String key;

    public void publishPaymentCommand(PaymentCommandDTO dto) {
        template.convertAndSend(exchange, key, dto);
    }

}
