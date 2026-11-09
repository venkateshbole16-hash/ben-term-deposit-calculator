package com.ben.termdeposit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TermDepositCalculatorTest {
    private TermDepositCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new TermDepositCalculator();
    }

    @Test
    void calculatesInterestPaidAtMaturityUsingSimpleInterest() {
        BigDecimal result = calculator.calculate(
                new BigDecimal("10000"),
                new BigDecimal("1.10"),
                3,
                InterestFrequency.AT_MATURITY);

        assertEquals(new BigDecimal("10330.00"), result);
    }

    @Test
    void calculatesMonthlyCompounding() {
        BigDecimal result = calculator.calculate(
                new BigDecimal("10000"),
                new BigDecimal("1.10"),
                3,
                InterestFrequency.MONTHLY);

        assertEquals(new BigDecimal("10335.35"), result);
    }

    @Test
    void calculatesQuarterlyCompounding() {
        BigDecimal result = calculator.calculate(
                new BigDecimal("10000"),
                new BigDecimal("1.10"),
                3,
                InterestFrequency.QUARTERLY);

        assertEquals(new BigDecimal("10335.04"), result);
    }

    @Test
    void calculatesAnnualCompounding() {
        BigDecimal result = calculator.calculate(
                new BigDecimal("10000"),
                new BigDecimal("1.10"),
                3,
                InterestFrequency.ANNUALLY);

        assertEquals(new BigDecimal("10333.64"), result);
    }

    @Test
    void rejectsZeroOrNegativeDeposit() {
        assertThrows(IllegalArgumentException.class, () ->
                calculator.calculate(new BigDecimal("0"), new BigDecimal("1.10"),
                        3, InterestFrequency.AT_MATURITY));
    }

    @Test
    void rejectsNegativeInterestRate() {
        assertThrows(IllegalArgumentException.class, () ->
                calculator.calculate(new BigDecimal("10000"), new BigDecimal("-1"),
                        3, InterestFrequency.MONTHLY));
    }

    @Test
    void rejectsNonPositiveTerm() {
        assertThrows(IllegalArgumentException.class, () ->
                calculator.calculate(new BigDecimal("10000"), new BigDecimal("1.10"),
                        0, InterestFrequency.ANNUALLY));
    }
}
