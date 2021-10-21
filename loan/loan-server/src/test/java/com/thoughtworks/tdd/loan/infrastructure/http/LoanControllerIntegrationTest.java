package com.thoughtworks.tdd.loan.infrastructure.http;

import com.thoughtworks.tdd.loan.domain.Loan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static com.thoughtworks.tdd.loan.utils.Stubs.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class LoanControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplate restTemplate;

    private String account = uuid();
    private LocalDate takenAt = LocalDate.now();
    private int durationInDays = 10;
    private int amount = 200;
    private int interestRate = 20;

    @Test
    void shouldReturnSuccessfulResultForRequestedLoan() {
        var loanRequest = "{\"amount\": 200, \"durationInDays\": 10}";

        ResponseEntity<LoanStatus> response = testRestTemplate.exchange(
                "/api/v1/accounts/{accountId}/loans/",
                POST,
                new HttpEntity<>(loanRequest, headers()),
                LoanStatus.class,
                account);

        assertThat(response.getStatusCode()).isEqualTo(ACCEPTED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo("ok");
        assertThat(response.getBody().getLocation().getUrl()).startsWith(String.format("/api/v1/accounts/%s/loans/", account));
    }

    @Test
    void shouldReturnAllLoansForAnAccount() {
        var responseType = new ParameterizedTypeReference<List<Loan>>() {
        };
        var loanRequest = "{\"amount\": 200, \"durationInDays\": 10}";

        testRestTemplate.exchange(
                "/api/v1/accounts/{accountId}/loans/",
                POST,
                new HttpEntity<>(loanRequest, headers()),
                LoanStatus.class,
                account);

        ResponseEntity<List<Loan>> response = testRestTemplate.exchange(
                "/api/v1/accounts/{account}/loans/",
                GET,
                EMPTY,
                responseType,
                account);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()).hasOnlyOneElementSatisfying(loan ->
                assertThat(loan).isEqualToIgnoringGivenFields(new Loan(account, amount, takenAt, durationInDays, interestRate), "id"));
    }

    @Test
    void shouldReturnLoanByAccountAndLoanId() {
        var loanRequest = "{\"amount\": 200, \"durationInDays\": 10}";

        ResponseEntity<LoanStatus> response = testRestTemplate.exchange(
                "/api/v1/accounts/{accountId}/loans/",
                POST,
                new HttpEntity<>(loanRequest, headers()),
                LoanStatus.class,
                account);

        assertThat(response.getStatusCode()).isEqualTo(ACCEPTED);
        String url = response.getBody().getLocation().getUrl();

        ResponseEntity<Loan> loanResponse = testRestTemplate.exchange(
                url,
                GET,
                EMPTY,
                Loan.class);

        assertThat(loanResponse.getStatusCode()).isEqualTo(OK);
    }

    @Test
    void shouldReturnLoanUsingExternalInterestRateProvider() {
        var loanRequest = "{\"amount\": 200, \"durationInDays\": 500}";

        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server.expect(once(), requestTo("https://example.com/interest-rate"))
                .andRespond(withSuccess("{ \"digit\": 5 }", APPLICATION_JSON));

        ResponseEntity<LoanStatus> response = testRestTemplate.exchange(
                "/api/v1/accounts/{accountId}/loans/",
                POST,
                new HttpEntity<>(loanRequest, headers()),
                LoanStatus.class,
                account);

        assertThat(response.getStatusCode()).isEqualTo(ACCEPTED);
        String url = response.getBody().getLocation().getUrl();

        ResponseEntity<Loan> loanResponse = testRestTemplate.exchange(
                url,
                GET,
                EMPTY,
                Loan.class);

        assertThat(loanResponse.getBody().getInterestRate()).isEqualTo(5);
    }

    private static HttpHeaders headers() {
        var headers = new HttpHeaders();
        headers.add(ACCEPT, APPLICATION_JSON_VALUE);
        headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return headers;
    }
}