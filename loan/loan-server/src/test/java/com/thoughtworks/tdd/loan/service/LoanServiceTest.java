package com.thoughtworks.tdd.loan.service;

import com.thoughtworks.tdd.loan.domain.Loan;
import com.thoughtworks.tdd.loan.domain.LoanRepository;
import com.thoughtworks.tdd.loan.infrastructure.http.NewLoanCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository repository;
    private LoanService loanService;

    @BeforeEach
    void setup() {
        loanService = new LoanService(repository);
    }

    @Test
    void shouldCreateNewLoanWithCorrectParameters() {
        var accountId = "1100";
        var dateTakenAt = LocalDate.now();
        var newLoanCommand = new NewLoanCommand(10, 5);

        var expected = new Loan(1L, accountId, newLoanCommand.getAmount(),
                dateTakenAt, newLoanCommand.getDurationInDays(), 20);

        when(repository.save(new Loan(accountId, newLoanCommand.getAmount(), dateTakenAt, newLoanCommand.getDurationInDays(), 20))).thenReturn(expected);

        var loan = loanService.createLoan(accountId, dateTakenAt, newLoanCommand);

        assertThat(loan).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getInterestRateParameters")
    void shouldCreateLoanWithCorrectInterestRateWhenLessThanOneYear(int duration, int expectedInterestRate) {
        var accountId = "1100";
        var dateTakenAt = LocalDate.now();
        var newLoanCommand = new NewLoanCommand(10, duration);

        var expected = new Loan(1L, accountId, newLoanCommand.getAmount(),
                dateTakenAt, newLoanCommand.getDurationInDays(), expectedInterestRate);

        when(repository.save(new Loan(accountId, newLoanCommand.getAmount(), dateTakenAt, newLoanCommand.getDurationInDays(), expectedInterestRate))).thenReturn(expected);

        var loan = loanService.createLoan(accountId, dateTakenAt, newLoanCommand);

        assertThat(loan.getInterestRate()).isEqualTo(expectedInterestRate);
    }

    private static Stream<Arguments> getInterestRateParameters() {
        return Stream.of(
                Arguments.of(5, 20),
                Arguments.of(30, 20),
                Arguments.of(45, 15),
                Arguments.of(180, 15),
                Arguments.of(200, 10)
            );
    }

    @Test
    void shouldCreateLoanWithInterestRateFromProviderWhenDurationIsMoreThan1Year() {}
}
