package com.thoughtworks.tdd.loan.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class LeapYearInterestRateProviderTest {
    public static final ZoneId UTC = ZoneId.of("UTC");

    @Test
    void shouldProvide10PercentageInterestRageIfYearIsLeap() {
        var clock = Clock.fixed(Instant.parse("2000-12-03T10:15:30.00Z"), UTC);
        var irProvider = new LeapYearInterestRateProvider(clock);

        BigDecimal currentInterestRate = irProvider.getCurrentInterestRate();

        assertThat(currentInterestRate).isEqualTo(new BigDecimal("0.1"));
    }

    @Test
    void shouldProvide9PercentageInterestRageIfYearIsNotLeap() {
        var clock = Clock.fixed(Instant.parse("2001-12-03T10:15:30.00Z"), UTC);
        var irProvider = new LeapYearInterestRateProvider(clock);

        BigDecimal currentInterestRate = irProvider.getCurrentInterestRate();

        assertThat(currentInterestRate).isEqualTo(new BigDecimal("0.09"));
    }
}