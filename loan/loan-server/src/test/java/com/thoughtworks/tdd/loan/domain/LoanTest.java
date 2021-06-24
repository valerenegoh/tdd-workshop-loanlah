package com.thoughtworks.tdd.loan.domain;

import com.thoughtworks.tdd.loan.utils.LoanBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThrows(NullPointerException.class, () -> new LoanBuilder().withTakenOn(null).build());
    }

    @Test
    void shouldCalculateOutstandingAmountForOneYear() {
        Loan loan = new LoanBuilder().withAmount(1000).withDurationInDays(365).build();

        assertThat(loan.totalOutstanding()).isEqualByComparingTo(new BigDecimal("1100.00"));
    }

    @Test
    void shouldCalculateOutstandingAmountFor10Days() {
        Loan loan = new LoanBuilder().withAmount(1000).withDurationInDays(10).build();

        assertThat(loan.totalOutstanding()).isEqualByComparingTo(new BigDecimal("1005.48"));
    }

    @Test
    void shouldCalculateOutstandingAmountFor100Days() {
        Loan loan = new LoanBuilder().withAmount(1000).withDurationInDays(100).build();

        assertThat(loan.totalOutstanding()).isEqualByComparingTo(new BigDecimal("1041.10"));
    }

}