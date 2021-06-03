package com.thoughtworks.tdd.loan.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class LeapYearInterestBasisProviderTest {
    public static final ZoneId UTC = ZoneId.of("UTC");

    @Test
    void shouldReturnInterestBasisForLeapYear() {
        var clock = Clock.fixed(Instant.parse("2000-12-03T10:15:30.00Z"), UTC);
        var basisProvider = new LeapYearInterestBasisProvider(clock);

        var currentInterestBasis = basisProvider.getCurrentInterestBasis();

        assertThat(currentInterestBasis).isEqualTo(366);
    }

    @Test
    void shouldReturnInterestBasisForNonLeapYear() {
        var clock = Clock.fixed(Instant.parse("2001-12-03T10:15:30.00Z"), UTC);
        var basisProvider = new LeapYearInterestBasisProvider(clock);

        var currentInterestBasis = basisProvider.getCurrentInterestBasis();

        assertThat(currentInterestBasis).isEqualTo(365);
    }
}