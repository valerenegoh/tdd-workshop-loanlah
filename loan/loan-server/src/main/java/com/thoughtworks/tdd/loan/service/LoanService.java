package com.thoughtworks.tdd.loan.service;

import com.thoughtworks.tdd.loan.domain.Loan;
import com.thoughtworks.tdd.loan.domain.LoanRepository;
import com.thoughtworks.tdd.loan.infrastructure.http.NewLoanCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {

    private LoanRepository repository;

    public LoanService(LoanRepository loanRepository) {
        repository = loanRepository;
    }

    public Loan createLoan(String accountId, LocalDate dateTakenAt, NewLoanCommand newLoanCommand) {
        var loan = new Loan(accountId,
                newLoanCommand.getAmount(),
                dateTakenAt,
                newLoanCommand.getDurationInDays(),
                interestRateFromDuration(newLoanCommand.getDurationInDays()));

        return repository.save(loan);
    }

    private int interestRateFromDuration(int durationInDays) {
        if (durationInDays <= 30) return 20;
        if (durationInDays <= 180) return 15;
        return 10;
    }
}
