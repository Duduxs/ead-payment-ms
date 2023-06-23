package com.ead.payment.models;

import com.ead.payment.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Table(name = "`user`")
@JsonInclude(NON_NULL)
public class UserModel implements Serializable {

    @Id
    private UUID userId;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;
    @Column(nullable = false, length = 100)
    private String fullName;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String type;
    @Column(length = 20)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Column
    private LocalDateTime paymentExpirationDate;
    @Column
    private LocalDateTime firstPaymentDate;
    @Column
    private LocalDateTime lastPaymentDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<PaymentModel> payments;

    public UserModel() { }

    public UserModel(UUID userId, String email, String cpf, String fullName, String status, String type, String phone) {
        this.userId = userId;
        this.email = email;
        this.cpf = cpf;
        this.fullName = fullName;
        this.status = status;
        this.type = type;
        this.phone = phone;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentExpirationDate() {
        return paymentExpirationDate;
    }

    public void setPaymentExpirationDate(LocalDateTime paymentExpirationDate) {
        this.paymentExpirationDate = paymentExpirationDate;
    }

    public LocalDateTime getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public void setFirstPaymentDate(LocalDateTime firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
    }

    public LocalDateTime getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDateTime lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public Set<PaymentModel> getPayments() {
        return payments;
    }

    public void setPayments(Set<PaymentModel> payments) {
        this.payments = payments;
    }
}
