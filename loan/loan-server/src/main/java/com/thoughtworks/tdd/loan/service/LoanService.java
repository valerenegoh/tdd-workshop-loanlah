package com.thoughtworks.tdd.loan.service;

import com.thoughtworks.tdd.loan.domain.Loan;
import com.thoughtworks.tdd.loan.domain.LoanRepository;
import com.thoughtworks.tdd.loan.infrastructure.http.InterestRateException;
import com.thoughtworks.tdd.loan.infrastructure.http.InterestRateProviderApi;
import com.thoughtworks.tdd.loan.infrastructure.http.NewLoanCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {

    private LoanRepository repository;
    private InterestRateProviderApi interestRateProviderApi;

    public LoanService(LoanRepository loanRepository, InterestRateProviderApi interestRateProviderApi) {
        repository = loanRepository;
        this.interestRateProviderApi = interestRateProviderApi;
    }

    public Loan createLoan(String accountId, LocalDate dateTakenAt, NewLoanCommand newLoanCommand) throws InterestRateException {
        var loan = new Loan(accountId,
                newLoanCommand.getAmount(),
                dateTakenAt,
                newLoanCommand.getDurationInDays(),
                interestRateFromDuration(newLoanCommand.getDurationInDays()));

        return repository.save(loan);
    }

    private int interestRateFromDuration(int durationInDays) throws InterestRateException {
        if (durationInDays <= 30) return 20;
        if (durationInDays <= 180) return 15;
        if (durationInDays <= 365) return 10;
        return interestRateProviderApi.getRate();
    }
}
