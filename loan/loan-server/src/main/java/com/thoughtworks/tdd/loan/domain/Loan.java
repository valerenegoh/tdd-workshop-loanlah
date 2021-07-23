package com.thoughtworks.tdd.loan.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static java.math.BigDecimal.ONE;
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

  public Loan(Long id, String account, int amount, LocalDate takenAt, int durationInDays, int interestRate) {
    this(account, amount, takenAt, durationInDays, interestRate);
    this.id = id;
  }

  public Loan(String account, int amount, LocalDate takenAt, int durationInDays, int interestRate) {
    this.account = account;
    this.amount = amount;
    this.takenAt = takenAt;
    this.durationInDays = durationInDays;
    this.interestRate = interestRate;
    this.interestBasis = 365;
  }

  public Long getId() {
    return id;
  }

  public int getInterestRate() {
    return interestRate;
  }

  @JsonGetter("totalOutstanding")
  public BigDecimal totalOutstanding() {
    BigDecimal actualInterestRate = getInterestPercentage()
            .multiply(new BigDecimal(durationInDays)).divide(new BigDecimal(interestBasis), HALF_UP);

    return new BigDecimal(amount).multiply(ONE.add(actualInterestRate)).setScale(2, HALF_UP);
  }

  private BigDecimal getInterestPercentage() {
    return new BigDecimal(interestRate).divide(new BigDecimal(100), 7, HALF_UP);
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
