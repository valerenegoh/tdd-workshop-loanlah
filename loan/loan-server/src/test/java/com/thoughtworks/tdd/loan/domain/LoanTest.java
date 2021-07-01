package com.thoughtworks.tdd.loan.domain;

import com.thoughtworks.tdd.loan.utils.LoanBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class LoanTest {

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

    @Test
    void shouldCalculateOutstandingAmountForAMonth() {
        Loan loan = new LoanBuilder().withAmount(1000).withDurationInDays(30).build();

        assertThat(loan.totalOutstanding()).isEqualByComparingTo(new BigDecimal("1016.44"));
    }

    @Test
    void shouldCalculateOutstandingAmountFor31Days() {
        Loan loan = new LoanBuilder().withAmount(1000).withDurationInDays(31).build();

        assertThat(loan.totalOutstanding()).isEqualByComparingTo(new BigDecimal("1012.74"));
    }

    @Test
    void shouldCalculateOutstandingAmountFor29Days() {
        Loan loan = new LoanBuilder().withAmount(1000).withDurationInDays(29).build();

        assertThat(loan.totalOutstanding()).isEqualByComparingTo(new BigDecimal("1015.89"));
    }

    @Test
    void shouldCalculateOutstandingAmountFor6Months() {
        Loan loan = new LoanBuilder().withAmount(1000).withDurationInDays(180).build();

        assertThat(loan.totalOutstanding()).isEqualByComparingTo(new BigDecimal("1073.97"));
    }

    @Test
    void shouldCalculateOutstandingAmountFor181Days() {
        Loan loan = new LoanBuilder().withAmount(1000).withDurationInDays(181).build();

        assertThat(loan.totalOutstanding()).isEqualByComparingTo(new BigDecimal("1049.59"));
    }

    @Test
    void shouldCalculateOutstandingAmountFor179Days() {
        Loan loan = new LoanBuilder().withAmount(1000).withDurationInDays(179).build();

        assertThat(loan.totalOutstanding()).isEqualByComparingTo(new BigDecimal("1073.56"));
    }
}