package com.thoughtworks.tdd.loan.service;

import com.thoughtworks.tdd.loan.domain.*;
import com.thoughtworks.tdd.loan.infrastructure.http.NewLoanCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {
    private LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(String accountId, NewLoanCommand newLoanCommand, LocalDate takenAt) {
        Loan loan = new Loan(accountId,
                newLoanCommand.getAmount(),
                takenAt,
                newLoanCommand.getDurationInDays());
        return loanRepository.save(loan);
    }
}
