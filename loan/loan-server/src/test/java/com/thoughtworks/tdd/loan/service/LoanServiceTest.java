package com.thoughtworks.tdd.loan.service;

import com.thoughtworks.tdd.loan.domain.Loan;
import com.thoughtworks.tdd.loan.domain.LoanRepository;
import com.thoughtworks.tdd.loan.infrastructure.http.NewLoan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

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
        var newLoanCommand = new NewLoan();
        newLoanCommand.setAmount(10);
        newLoanCommand.setDurationInDays(5);

        var expected = new Loan(1L, accountId, newLoanCommand.getAmount(),
                dateTakenAt, newLoanCommand.getDurationInDays());

        when(repository.save(new Loan(accountId, newLoanCommand.getAmount(), dateTakenAt, newLoanCommand.getDurationInDays()))).thenReturn(expected);

        var loan = loanService.createLoan(accountId, dateTakenAt, newLoanCommand);

        assertThat(loan).isEqualTo(expected);
    }
}
