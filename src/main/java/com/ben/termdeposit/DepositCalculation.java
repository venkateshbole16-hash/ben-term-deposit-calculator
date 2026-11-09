package com.ben.termdeposit;

import java.math.BigDecimal;

/** Calculates the final balance for a term deposit. */
public interface DepositCalculation {
    BigDecimal calculate(BigDecimal principal, BigDecimal annualInterestRatePercent,
                         int termInYears, InterestFrequency frequency);
}
