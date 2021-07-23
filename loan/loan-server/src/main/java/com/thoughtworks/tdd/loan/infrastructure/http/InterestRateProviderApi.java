package com.thoughtworks.tdd.loan.infrastructure.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class InterestRateProviderApi {

    private final RestTemplate restTemplate;
    private final String       URL = "https://random-data-api.com/api/number/random_number";

    public InterestRateProviderApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getRate() throws InterestRateException {
        InterestRateApiResponse interestRate;
        try {
            interestRate = restTemplate.getForObject(URL, InterestRateApiResponse.class);
        } catch (RestClientException e) {
            throw InterestRateException.NotAvailable(e);
        }
        if(interestRate == null)
            throw  InterestRateException.NotAvailable();
       return interestRate.getDigit();
    }

}
