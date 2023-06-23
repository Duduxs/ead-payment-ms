package com.ead.payment.boundaries;

import com.ead.payment.enums.PaymentControl;

import java.util.Random;

/**
 * TODO make a change to a real payment gateway.
 */
public class PaymentBoundaryMock {

    public PaymentControl pay() {
        var random = new Random();
        var number = random.nextInt(3);
        if (number == 0) {
            return PaymentControl.EFFECTED;
        } else if(number == 1) {
            return PaymentControl.REFUSED;
        } else {
            return PaymentControl.ERROR;
        }
    }


}
