package com.thoughtworks.tdd.loan.domain;

import java.time.Clock;
import java.time.LocalDate;

public class LeapYearInterestBasisProvider implements InterestBasisProvider {
    private final Clock clock;

    public LeapYearInterestBasisProvider(Clock clock) {
        this.clock = clock;
    }

    @Override
    public int getCurrentInterestBasis() {
        return LocalDate.now(clock).lengthOfYear();
    }
}
