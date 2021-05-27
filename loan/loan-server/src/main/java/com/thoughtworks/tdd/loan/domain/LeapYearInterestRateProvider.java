package com.thoughtworks.tdd.loan.domain;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;

public class LeapYearInterestRateProvider implements InterestRateProvider {
    private static final BigDecimal LEAP_YEAR_IR = new BigDecimal("0.1");
    private static final BigDecimal NOT_LEAP_YEAR_IR = new BigDecimal("0.09");
    private final Clock clock;

    public LeapYearInterestRateProvider(Clock clock) {
        this.clock = clock;
    }

    @Override
    public BigDecimal getCurrentInterestRate() {
        return LocalDate.now(clock).isLeapYear() ? LEAP_YEAR_IR : NOT_LEAP_YEAR_IR;
    }
}
