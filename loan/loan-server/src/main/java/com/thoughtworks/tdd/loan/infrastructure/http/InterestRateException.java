package com.thoughtworks.tdd.loan.infrastructure.http;

public class InterestRateException extends RuntimeException {
    public InterestRateException(String message, Throwable cause) {
        super(message, cause    );
    }

    public static InterestRateException NotAvailable() {
        return new InterestRateException("Interest rate not available!", null);
    }

    public static InterestRateException NotAvailable(Throwable cause) {
        return new InterestRateException("Interest rate not available!", cause);
    }
}
