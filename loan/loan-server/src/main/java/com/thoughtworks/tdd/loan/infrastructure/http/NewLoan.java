package com.thoughtworks.tdd.loan.infrastructure.http;

import java.util.Objects;

public class NewLoan {
  private int amount;
  private int durationInDays;

  public NewLoan() {
  }

  public NewLoan(int amount, int durationInDays) {
    this.amount = amount;
    this.durationInDays = durationInDays;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getDurationInDays() {
    return durationInDays;
  }

  public void setDurationInDays(int durationInDays) {
    this.durationInDays = durationInDays;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NewLoan newLoan = (NewLoan) o;
    return amount == newLoan.amount && durationInDays == newLoan.durationInDays;
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, durationInDays);
  }
}
