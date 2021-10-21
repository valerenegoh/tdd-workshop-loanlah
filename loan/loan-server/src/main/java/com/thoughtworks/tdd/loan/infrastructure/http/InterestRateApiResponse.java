package com.thoughtworks.tdd.loan.infrastructure.http;

public class InterestRateApiResponse {
    private Integer digit;

    public InterestRateApiResponse(Integer interestRate) {
        this.digit = interestRate;
    }

    public Integer getDigit() {
        return digit;
    }

}
