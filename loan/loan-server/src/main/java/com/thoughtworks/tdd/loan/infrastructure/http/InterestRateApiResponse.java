package com.thoughtworks.tdd.loan.infrastructure.http;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestRateApiResponse {
    private Integer digit;

    @JsonCreator
    public InterestRateApiResponse(@JsonProperty("digit") Integer interestRate) {
        this.digit = interestRate;
    }

    public Integer getDigit() {
        return digit;
    }

}
