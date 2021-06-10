package com.thoughtworks.tdd.loan.domain;

import com.thoughtworks.tdd.loan.utils.LoanBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LoanTest {

  @Test
  void shouldNotBeAbleToCreateLoanWithNegativeAmount() {
    assertThrows(IllegalArgumentException.class, () -> new LoanBuilder().withAmount(-1).build());
  }

  @Test
  void shouldNotBeAbleToCreateLoanWithNegativeDuration() {
    assertThrows(IllegalArgumentException.class, () -> new LoanBuilder().withDurationInDays(-1).build());
  }

  @Test
  void shouldNotBeAbleToCreateLoanWithoutAccountNumber() {
    assertThrows(NullPointerException.class, () -> new LoanBuilder().withAccount(null).build());
  }

  @Test
  void shouldNotBeAbleToCreateLoanWithoutTakenAt() {
    assertThrows(NullPointerException.class, () ->new LoanBuilder().withTakenOn(null).build());
  }

}