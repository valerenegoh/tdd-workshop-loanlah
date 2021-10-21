package com.thoughtworks.tdd.loan.infrastructure.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

@Component
public class InterestRateProviderApi {

    private final RestTemplate restTemplate;
    private final String interestRateUrl;

    public InterestRateProviderApi(RestTemplate restTemplate, @Value("${external.interest-rate-url}") String interestRateUrl) {
        this.restTemplate = restTemplate;
        this.interestRateUrl = interestRateUrl;
    }

    public int getRate() throws InterestRateException {
        InterestRateApiResponse interestRate;
        try {
            interestRate = restTemplate.getForObject(interestRateUrl, InterestRateApiResponse.class);
        } catch (RestClientException e) {
            throw new InterestRateException("Problem while calling GET " + interestRateUrl, e);
        }
        if (interestRate == null)
            throw InterestRateException.NotAvailable();
        return interestRate.getDigit();
    }

}
