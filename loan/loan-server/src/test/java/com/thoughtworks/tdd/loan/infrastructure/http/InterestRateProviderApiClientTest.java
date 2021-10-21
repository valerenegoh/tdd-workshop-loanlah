package com.thoughtworks.tdd.loan.infrastructure.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class InterestRateProviderApiClientTest {
    public static final String INTEREST_RATE_URL = "https://random-data-api.com/api/number/random_number";

    @Mock
    RestTemplate            restTemplate;

    InterestRateProviderApi interestRateProviderApi;

    @BeforeEach
    void setUp() {
        interestRateProviderApi = new InterestRateProviderApi(restTemplate, INTEREST_RATE_URL);
    }

    @Test
    void shouldReturnResponseFromExternalApi() throws InterestRateException {
        when(restTemplate.getForObject(INTEREST_RATE_URL, InterestRateApiResponse.class))
                .thenReturn(new InterestRateApiResponse(3));

        Integer interestRate = interestRateProviderApi.getRate();

        assertThat(interestRate).isEqualTo(3);
    }

    @Test
    void shouldReturnInterestRateExceptionIfResponseIsNotOfCorrectType() {
        when(restTemplate.getForObject(INTEREST_RATE_URL, InterestRateApiResponse.class)).thenReturn(null);

        assertThatThrownBy(interestRateProviderApi::getRate).isInstanceOf(InterestRateException.class)
                .hasMessageContaining("Interest rate not available!");
    }

    @Test
    void shouldReturnInterestRateExceptionIfThereIsAnyExceptionWithApiCall() {
        when(restTemplate.getForObject(INTEREST_RATE_URL, InterestRateApiResponse.class))
                .thenThrow(new RestClientException("boom!!!"));

        assertThatThrownBy(interestRateProviderApi::getRate).isInstanceOf(InterestRateException.class)
                .hasMessageContaining("Problem while calling GET " + INTEREST_RATE_URL);
    }

    @Test
    void shouldUseInterestRateUrlProvided() {
        var interestRestUrl = "https://example.com/api/number/random_number";
        var interestRateProviderApi = new InterestRateProviderApi(restTemplate, interestRestUrl);

        when(restTemplate.getForObject(interestRestUrl, InterestRateApiResponse.class))
                .thenReturn(new InterestRateApiResponse(3));
        Integer interestRate = interestRateProviderApi.getRate();
        assertThat(interestRate).isEqualTo(3);

        when(restTemplate.getForObject(interestRestUrl, InterestRateApiResponse.class))
                .thenThrow(new RestClientException("boom!!!"));
        assertThatThrownBy(interestRateProviderApi::getRate).isInstanceOf(InterestRateException.class)
                .hasMessageContaining("Problem while calling GET " + INTEREST_RATE_URL);
    }
}
