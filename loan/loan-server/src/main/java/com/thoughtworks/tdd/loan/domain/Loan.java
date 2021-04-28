package com.thoughtworks.tdd.loan.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

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

  private Loan() {
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
    this.interestRate = 10;
    this.interestBasis = 365;
  }

  private void validateLoan(String account, int amount, LocalDate takenAt, int durationInDays) {
    Objects.requireNonNull(account, "account can not be null");
    Objects.requireNonNull(takenAt, "takenAt date can not be null");
    if(amount < 0 || durationInDays < 0) {
      throw new IllegalArgumentException("amount or duration or interest rate cannot be negative");
    }
  }

  public Long getId() {
    return id;
  }

  public int getInterestRate() {
    return interestRate;
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
}
