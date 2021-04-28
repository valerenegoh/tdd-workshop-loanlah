package com.thoughtworks.tdd.loan.domain;

import com.thoughtworks.tdd.loan.utils.LoanBuilder;
import com.thoughtworks.tdd.loan.utils.Stubs;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoanTest {

  @Test
  void shouldNotBeAbleToCreateLoanWithNegativeAmount() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Loan(Stubs.id(), Stubs.uuid(), -1, LocalDate.now(),  10);
    });
  }

  @Test
  void shouldNotBeAbleToCreateLoanWithNegativeDuration() {
    assertThrows(IllegalArgumentException.class, () -> {
      new Loan(Stubs.id(), Stubs.uuid(), 10, LocalDate.now(),  -1);
    });
  }

  @Test
  void shouldNotBeAbleToCreateLoanWithoutAccountNumber() {
    assertThrows(NullPointerException.class, () -> {
      new Loan(Stubs.id(), null, 10, LocalDate.now(),  10);
    });
  }

  @Test
  void shouldNotBeAbleToCreateLoanWithoutTakenAt() {
    assertThrows(NullPointerException.class, () -> {
      new Loan(Stubs.id(), Stubs.uuid(), 10, null,  10);
    });
  }
}