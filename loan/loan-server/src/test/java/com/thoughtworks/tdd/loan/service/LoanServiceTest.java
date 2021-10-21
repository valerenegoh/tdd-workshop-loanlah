package com.thoughtworks.tdd.loan.service;

import com.thoughtworks.tdd.loan.domain.*;
import com.thoughtworks.tdd.loan.infrastructure.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.stream.Stream;

import static com.thoughtworks.tdd.loan.utils.Stubs.uuid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {
    private final String accountId = uuid();
    private final LocalDate takenAt = LocalDate.now();
    private final int amount = 200;

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private InterestRateProviderApi interestRateProviderApi;

    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanService = new LoanService(loanRepository, interestRateProviderApi);
    }

    @Test
    void shouldCreateNewLoanWithCorrectParameters() {
        int duration = 10;
        int interestRate = 20;
        NewLoanCommand newLoanCommand = new NewLoanCommand(amount, duration);

        Loan expectedLoan = new Loan(accountId, amount, takenAt, duration, interestRate);

        when(loanRepository.save(
                new Loan(accountId,
                        newLoanCommand.getAmount(),
                        takenAt,
                        newLoanCommand.getDurationInDays(),
                        interestRate))
        ).thenReturn(expectedLoan);
        Loan actualLoan = loanService.createLoan(accountId, newLoanCommand, takenAt);

        assertThat(actualLoan).isEqualTo(expectedLoan);
    }

    @ParameterizedTest
    @MethodSource("provideDurationsAndInterestRates")
    void shouldCreateLoanWithCorrectInterestRateForDurationsWithin365Days(int duration, int interestRate) {
        NewLoanCommand newLoanCommand = new NewLoanCommand(amount, duration);

        Loan expectedLoan = new Loan(accountId, amount, takenAt, duration, interestRate);

        when(loanRepository.save(
                new Loan(accountId,
                        newLoanCommand.getAmount(),
                        takenAt,
                        newLoanCommand.getDurationInDays(),
                        interestRate))
        ).thenReturn(expectedLoan);
        Loan actualLoan = loanService.createLoan(accountId, newLoanCommand, takenAt);

        assertThat(actualLoan).isEqualTo(expectedLoan);
    }

    private static Stream<Arguments> provideDurationsAndInterestRates() {
        return Stream.of(
                Arguments.of(10, 20),
                Arguments.of(29, 20),
                Arguments.of(30, 20),
                Arguments.of(31, 15),
                Arguments.of(100, 15),
                Arguments.of(179, 15),
                Arguments.of(180, 15),
                Arguments.of(181, 10),
                Arguments.of(365, 10)
        );
    }

    @Test
    void shouldCreateLoanWithExternalApiInterestRateForDurationsMoreThan365Days() {
        int duration = 500;
        int interestRate = 5;
        NewLoanCommand newLoanCommand = new NewLoanCommand(amount, duration);

        Loan expectedLoan = new Loan(accountId, amount, takenAt, duration, interestRate);

        when(loanRepository.save(
                new Loan(accountId,
                        newLoanCommand.getAmount(),
                        takenAt,
                        newLoanCommand.getDurationInDays(),
                        interestRate))
        ).thenReturn(expectedLoan);
        when(interestRateProviderApi.getRate()).thenReturn(interestRate);
        Loan actualLoan = loanService.createLoan(accountId, newLoanCommand, takenAt);

        assertThat(actualLoan).isEqualTo(expectedLoan);
    }
}