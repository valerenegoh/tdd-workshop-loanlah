package com.thoughtworks.tdd.loan.domain;

import com.thoughtworks.tdd.loan.utils.LoanBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static java.math.RoundingMode.HALF_UP;
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

    @ParameterizedTest
    @MethodSource("provideDurationsAndOutstandingAmounts")
    void shouldCalculateOutstandingLoanGivenDuration(int duration, BigDecimal expectedOutstanding) {
        Loan loan = new LoanBuilder()
                .withAmount(1000)
                .withDurationInDays(duration)
                .build();

        BigDecimal actualOutstanding = loan.totalOutstanding();

        assertThat(actualOutstanding).isEqualTo(expectedOutstanding);
    }

    private static Stream<Arguments> provideDurationsAndOutstandingAmounts() {
        return Stream.of(
                Arguments.of(10, BigDecimal.valueOf(1005.48).setScale(2, HALF_UP)),
                Arguments.of(29, BigDecimal.valueOf(1015.89).setScale(2, HALF_UP)),
                Arguments.of(30, BigDecimal.valueOf(1016.44).setScale(2, HALF_UP)),
                Arguments.of(31, BigDecimal.valueOf(1012.74).setScale(2, HALF_UP)),
                Arguments.of(100, BigDecimal.valueOf(1041.10).setScale(2, HALF_UP)),
                Arguments.of(179, BigDecimal.valueOf(1073.56).setScale(2, HALF_UP)),
                Arguments.of(180, BigDecimal.valueOf(1073.97).setScale(2, HALF_UP)),
                Arguments.of(181, BigDecimal.valueOf(1049.59).setScale(2, HALF_UP)),
                Arguments.of(365, BigDecimal.valueOf(1100.00).setScale(2, HALF_UP))
        );
    }
}