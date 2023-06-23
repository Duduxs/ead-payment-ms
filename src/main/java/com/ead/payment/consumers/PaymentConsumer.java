package com.ead.payment.consumers;

import com.ead.payment.dtos.PaymentCommandDTO;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

@Component
public class PaymentConsumer {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            value = "${ead.broker.queue.paymentCommand.name}",
                            durable = "true"
                    ),
                    exchange = @Exchange(
                            value = "${ead.broker.exchange.paymentCommandExchange.name}",
                            type = TOPIC,
                            ignoreDeclarationExceptions = "true"
                    ),
                    key = "${ead.broker.queue.key.paymentCommand}"
            )
    )
    public void listenPaymentCommand(@Payload PaymentCommandDTO event) {

    }
}
