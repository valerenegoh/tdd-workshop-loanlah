package com.thoughtworks.tdd.loan.domain;

import java.math.BigDecimal;

public interface InterestRateProvider {
    BigDecimal getCurrentInterestRate();
}