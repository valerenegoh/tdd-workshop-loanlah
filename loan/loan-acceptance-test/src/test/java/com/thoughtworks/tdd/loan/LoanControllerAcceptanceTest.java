package com.thoughtworks.tdd.loan;

import static com.thoughtworks.tdd.loan.utils.Stubs.uuid;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.path.json.config.JsonPathConfig.jsonPathConfig;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

class LoanControllerAcceptanceTest {

    private String accountId = uuid();

    @AcceptanceTest
    void shouldRequestNewLoanAndSeeAmountAndInterestRate() {
        var response =
                given().
                        contentType(JSON).
                        body("{\"amount\": 200, \"durationInDays\": 365}").
                        when().
                        post("/api/v1/accounts/{accountId}/loans/", accountId).
                        then().
                        statusCode(202).
                        body("status", equalTo("ok")).
                        body("location.url", notNullValue()).
                        extract();

        String newLoanUrl = response.body().jsonPath(jsonPathConfig()).getString("location.url");

        given().
                when().
                get(newLoanUrl).
                then().
                statusCode(200).
                body("amount", equalTo(200)).
                body("interestRate", equalTo(10)).
                body("interestBasis", equalTo(365)).
                body("durationInDays", equalTo(365)).
                body("totalOutstanding", equalTo(220.0F));
    }

    @AcceptanceTest
    void shouldGetNewLoanWithInterestRateFromExternalService() {
        var response =
                given().
                        contentType(JSON).
                        body("{\"amount\": 200, \"durationInDays\": 550}").
                        when().
                        post("/api/v1/accounts/{accountId}/loans/", accountId).
                        then().
                        statusCode(202).
                        body("status", equalTo("ok")).
                        body("location.url", notNullValue()).
                        extract();

        String newLoanUrl = response.body().jsonPath(jsonPathConfig()).getString("location.url");

        given().
                when().
                get(newLoanUrl).
                then().
                statusCode(200).
                body("amount", equalTo(200)).
                body("interestRate", equalTo(5)).
                body("interestBasis", equalTo(365)).
                body("durationInDays", equalTo(550)).
                body("totalOutstanding", equalTo(215.07F));
    }
}