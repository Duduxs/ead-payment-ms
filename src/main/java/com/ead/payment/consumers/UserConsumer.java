package com.ead.payment.consumers;

import com.ead.payment.dtos.UserEventDTO;
import com.ead.payment.enums.ActionType;
import com.ead.payment.enums.PaymentStatus;
import com.ead.payment.models.UserModel;
import com.ead.payment.services.UserService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

@Component
public class UserConsumer {

    @Autowired
    private UserService service;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            value = "${ead.broker.queue.userEvent.name}",
                            durable = "true"
                    ),
                    exchange = @Exchange(
                            value = "${ead.broker.exchange.userEvent.name}",
                            type = FANOUT,
                            ignoreDeclarationExceptions = "true"
                    )
            )
    )
    public void listenUserEvent(@Payload UserEventDTO event) {

        switch (ActionType.valueOf(event.actionType())) {
            case CREATE -> {
                var user = new UserModel(event.id(), event.email(), event.cpf(), event.fullName(), event.status(), event.type(), event.phone());
                user.setPaymentStatus(PaymentStatus.NOT_STARTED);
                service.save(user);
            }
            case UPDATE -> {
                var user = service.findById(event.id()).get();
                var newUser = new UserModel(user.getUserId(), event.email(), event.cpf(), event.fullName(), event.status(), event.type(), event.phone());
                newUser.setPaymentStatus(user.getPaymentStatus());
                newUser.setPaymentExpirationDate(user.getPaymentExpirationDate());
                newUser.setFirstPaymentDate(user.getFirstPaymentDate());
                newUser.setLastPaymentDate(user.getLastPaymentDate());
                service.save(newUser);
            }
            case DELETE -> service.delete(event.id());
        }
    }
}
