package com.ben.termdeposit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public class TermDepositCalculator {
    private static final MathContext CALCULATION_CONTEXT = MathContext.DECIMAL128;
    private static final int MONEY_SCALE = 2;

    /**
     * Calculates the final balance.
     * The annual interest rate must be supplied as a percentage (for example, 1.10).
     * For payments before maturity, interest is reinvested and compounded.
     * At maturity, interest is paid once, so simple interest is applied.
     */
    public BigDecimal calculate(
            BigDecimal principal,
            BigDecimal annualInterestRatePercent,
            int termInYears,
            InterestFrequency frequency) {

        validateInputs(principal, annualInterestRatePercent, termInYears, frequency);

        BigDecimal annualRate = annualInterestRatePercent
                .divide(BigDecimal.valueOf(100), CALCULATION_CONTEXT);

        BigDecimal balance;

        if (frequency == InterestFrequency.AT_MATURITY) {
            // Example: 10000 * (1 + 0.011 * 3) = 10330.00
            BigDecimal totalInterestRate = annualRate
                    .multiply(BigDecimal.valueOf(termInYears), CALCULATION_CONTEXT);
            balance = principal.multiply(
                    BigDecimal.ONE.add(totalInterestRate, CALCULATION_CONTEXT),
                    CALCULATION_CONTEXT);
        } else {
            int periodsPerYear = frequency.getPaymentsPerYear();
            int totalPeriods = Math.multiplyExact(termInYears, periodsPerYear);

            BigDecimal ratePerPeriod = annualRate.divide(
                    BigDecimal.valueOf(periodsPerYear), CALCULATION_CONTEXT);

            BigDecimal periodicMultiplier = BigDecimal.ONE.add(
                    ratePerPeriod, CALCULATION_CONTEXT);

            BigDecimal compoundFactor = periodicMultiplier.pow(
                    totalPeriods, CALCULATION_CONTEXT);

            balance = principal.multiply(compoundFactor, CALCULATION_CONTEXT);
        }

        return balance.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    private void validateInputs(
            BigDecimal principal,
            BigDecimal annualInterestRatePercent,
            int termInYears,
            InterestFrequency frequency) {

        Objects.requireNonNull(principal, "Deposit amount is required.");
        Objects.requireNonNull(annualInterestRatePercent, "Interest rate is required.");
        Objects.requireNonNull(frequency, "Interest frequency is required.");

        if (principal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        if (annualInterestRatePercent.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative.");
        }
        if (termInYears <= 0) {
            throw new IllegalArgumentException("Investment term must be greater than zero.");
        }
    }
}
