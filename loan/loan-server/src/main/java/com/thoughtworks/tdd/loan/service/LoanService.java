package com.thoughtworks.tdd.loan.service;

import com.thoughtworks.tdd.loan.domain.Loan;
import com.thoughtworks.tdd.loan.domain.LoanRepository;
import com.thoughtworks.tdd.loan.infrastructure.http.NewLoan;

import java.time.LocalDate;

public class LoanService {

    private LoanRepository repository;

    public LoanService(LoanRepository loanRepository) {
        repository = loanRepository;
    }

    public Loan createLoan(String accountId, LocalDate dateTakenAt, NewLoan newLoanCommand) {
        var loan = new Loan(accountId, newLoanCommand.getAmount(), dateTakenAt, newLoanCommand.getDurationInDays());

        return repository.save(loan);
    }
}
