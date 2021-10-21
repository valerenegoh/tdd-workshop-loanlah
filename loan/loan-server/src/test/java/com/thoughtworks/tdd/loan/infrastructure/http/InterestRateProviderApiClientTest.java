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
    @Mock
    RestTemplate            restTemplate;
    InterestRateProviderApi interestRateProviderApi;

    @BeforeEach
    void setUp() {
        interestRateProviderApi = new InterestRateProviderApi(restTemplate);
    }

    @Test
    void shouldReturnResponseFromExternalApi() throws InterestRateException {
        when(restTemplate.getForObject("https://random-data-api.com/api/number/random_number",
                                       InterestRateApiResponse.class)).thenReturn(new InterestRateApiResponse(3));

        Integer interestRate = interestRateProviderApi.getRate();

        assertThat(interestRate).isEqualTo(3);
    }


    @Test
    void shouldReturnInterestRateExceptionIfResponseIsNotOfSameType() {
        when(restTemplate.getForObject("https://random-data-api.com/api/number/random_number",
                                       InterestRateApiResponse.class)).thenReturn(null);

        assertThatThrownBy(interestRateProviderApi::getRate).isInstanceOf(InterestRateException.class)
                .hasMessageContaining("Interest rate not available!");
    }

    @Test
    void shouldReturnInterestRateExceptionIfThereIsAnyExceptionWithApiCall() {
        when(restTemplate.getForObject("https://random-data-api.com/api/number/random_number",
                                       InterestRateApiResponse.class)).thenThrow(new RestClientException("boom!!!"));

        assertThatThrownBy(interestRateProviderApi::getRate).isInstanceOf(InterestRateException.class)
                .hasMessageContaining("Interest rate not available!");
    }

}
