package com.thoughtworks.tdd.loan.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.RoundingMode.HALF_UP;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column
    @JsonProperty
    private String account;

    @Column
    @JsonProperty
    private int amount;

    @Column
    @JsonProperty
    private LocalDate takenAt;

    @Column
    @JsonProperty
    private int durationInDays;

    @Column
    @JsonProperty
    private int interestRate;

    @Column
    @JsonProperty
    private int interestBasis;

    public Loan() {
    }

    public Loan(Long id, String account, int amount, LocalDate takenAt, int durationInDays) {
        this(account, amount, takenAt, durationInDays);
        this.id = id;
    }

    public Loan(String account, int amount, LocalDate takenAt, int durationInDays) {
        validateLoan(account, amount, takenAt, durationInDays);
        this.account = account;
        this.amount = amount;
        this.takenAt = takenAt;
        this.durationInDays = durationInDays;
        this.interestRate = interestRateFromDuration(durationInDays);
        this.interestBasis = 365;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return amount == loan.amount && durationInDays == loan.durationInDays && interestRate == loan.interestRate && interestBasis == loan.interestBasis && Objects.equals(id, loan.id) && Objects.equals(account, loan.account) && Objects.equals(takenAt, loan.takenAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, amount, takenAt, durationInDays, interestRate, interestBasis);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", amount=" + amount +
                ", takenAt=" + takenAt +
                ", durationInDays=" + durationInDays +
                ", interestRate=" + interestRate +
                ", interestBasis=" + interestBasis +
                '}';
    }

    private int interestRateFromDuration(int durationInDays) {
        if (durationInDays <= 30) return 20;
        if (durationInDays <= 180) return 15;
        return 10;
    }

    private void validateLoan(String account, int amount, LocalDate takenAt, int durationInDays) {
        Objects.requireNonNull(account, "account can not be null");
        Objects.requireNonNull(takenAt, "takenAt date can not be null");
        if (amount < 0 || durationInDays < 0) {
            throw new IllegalArgumentException("amount or duration or interest rate cannot be negative");
        }
    }

    @JsonGetter("totalOutstanding")
    public BigDecimal totalOutstanding() {
        BigDecimal principal = BigDecimal.valueOf(amount);
        BigDecimal duration = BigDecimal.valueOf(durationInDays);
        BigDecimal basis = BigDecimal.valueOf(interestBasis);
        BigDecimal percentageInterest = BigDecimal.valueOf(interestRate).divide(BigDecimal.valueOf(100), 2, HALF_UP);

        BigDecimal dailyInterest = principal.multiply(percentageInterest).divide(basis, 5, HALF_UP);
        BigDecimal totalInterest = dailyInterest.multiply(duration);
        return principal.add(totalInterest).setScale(2, HALF_UP);
    }
}
