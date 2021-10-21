package com.thoughtworks.tdd.loan.service;

import com.thoughtworks.tdd.loan.domain.*;
import com.thoughtworks.tdd.loan.infrastructure.http.NewLoanCommand;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

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

    private LoanService loanService;

    @BeforeEach
    void setUp() {
        loanService = new LoanService(loanRepository);
    }

    @Test
    void shouldCreateNewLoanWithCorrectParameters() {
        int duration = 10;
        NewLoanCommand newLoanCommand = new NewLoanCommand(amount, duration);

        Loan expectedLoan = new Loan(accountId, amount, takenAt, duration);

        when(loanRepository.save(
                new Loan(accountId,
                        newLoanCommand.getAmount(),
                        takenAt,
                        newLoanCommand.getDurationInDays()))
        ).thenReturn(expectedLoan);
        Loan actualLoan = loanService.createLoan(accountId, newLoanCommand, takenAt);

        assertThat(actualLoan).isEqualTo(expectedLoan);
    }
}