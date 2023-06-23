package com.ead.payment.models;

import com.ead.payment.enums.PaymentControl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Table(name = "`payment`")
@JsonInclude(NON_NULL)
public class PaymentModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentControl paymentControl;
    @Column(nullable = false)
    private LocalDateTime paymentRequestDate;
    @Column(nullable = false)
    private LocalDateTime paymentCompletionDate;
    @Column(nullable = false)
    private LocalDateTime paymentExpirationDate;
    @Column(nullable = false, length = 4)
    private LocalDate lastDigitsCreditCard;
    @Column(nullable = false)
    private BigDecimal valuePaid;
    @Column(length = 150)
    private String paymentMessage;
    @Column
    private boolean recurrence;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserModel user;


    public PaymentModel() {
    }

    public PaymentModel(UUID paymentId, PaymentControl paymentControl, LocalDateTime paymentRequestDate, LocalDateTime paymentCompletionDate, LocalDateTime paymentExpirationDate, LocalDate lastDigitsCreditCard, BigDecimal valuePaid, String paymentMessage, boolean recurrence) {
        this.paymentId = paymentId;
        this.paymentControl = paymentControl;
        this.paymentRequestDate = paymentRequestDate;
        this.paymentCompletionDate = paymentCompletionDate;
        this.paymentExpirationDate = paymentExpirationDate;
        this.lastDigitsCreditCard = lastDigitsCreditCard;
        this.valuePaid = valuePaid;
        this.paymentMessage = paymentMessage;
        this.recurrence = recurrence;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentControl getPaymentControl() {
        return paymentControl;
    }

    public void setPaymentControl(PaymentControl paymentControl) {
        this.paymentControl = paymentControl;
    }

    public LocalDateTime getPaymentRequestDate() {
        return paymentRequestDate;
    }

    public void setPaymentRequestDate(LocalDateTime paymentRequestDate) {
        this.paymentRequestDate = paymentRequestDate;
    }

    public LocalDateTime getPaymentCompletionDate() {
        return paymentCompletionDate;
    }

    public void setPaymentCompletionDate(LocalDateTime paymentCompletionDate) {
        this.paymentCompletionDate = paymentCompletionDate;
    }

    public LocalDateTime getPaymentExpirationDate() {
        return paymentExpirationDate;
    }

    public void setPaymentExpirationDate(LocalDateTime paymentExpirationDate) {
        this.paymentExpirationDate = paymentExpirationDate;
    }

    public LocalDate getLastDigitsCreditCard() {
        return lastDigitsCreditCard;
    }

    public void setLastDigitsCreditCard(LocalDate lastDigitsCreditCard) {
        this.lastDigitsCreditCard = lastDigitsCreditCard;
    }

    public BigDecimal getValuePaid() {
        return valuePaid;
    }

    public void setValuePaid(BigDecimal valuePaid) {
        this.valuePaid = valuePaid;
    }

    public String getPaymentMessage() {
        return paymentMessage;
    }

    public void setPaymentMessage(String paymentMessage) {
        this.paymentMessage = paymentMessage;
    }

    public boolean isRecurrence() {
        return recurrence;
    }

    public void setRecurrence(boolean recurrence) {
        this.recurrence = recurrence;
    }
    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}