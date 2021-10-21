package com.thoughtworks.tdd.loan.service;

import com.thoughtworks.tdd.loan.domain.*;
import com.thoughtworks.tdd.loan.infrastructure.http.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {
    private LoanRepository loanRepository;
    private InterestRateProviderApi interestRateProviderApi;

    public LoanService(LoanRepository loanRepository, InterestRateProviderApi interestRateProviderApi) {
        this.loanRepository = loanRepository;
        this.interestRateProviderApi = interestRateProviderApi;
    }

    public Loan createLoan(String accountId, NewLoanCommand newLoanCommand, LocalDate takenAt) {
        Loan loan = new Loan(accountId,
                newLoanCommand.getAmount(),
                takenAt,
                newLoanCommand.getDurationInDays(),
                interestRateFromDuration(newLoanCommand.getDurationInDays()));
        return loanRepository.save(loan);
    }

    private int interestRateFromDuration(int durationInDays) {
        if (durationInDays <= 30) return 20;
        if (durationInDays <= 180) return 15;
        if (durationInDays <= 365) return 10;
        return interestRateProviderApi.getRate();
    }
}
