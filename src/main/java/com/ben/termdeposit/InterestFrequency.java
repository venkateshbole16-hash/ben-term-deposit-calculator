package com.ben.termdeposit;

public enum InterestFrequency {
    MONTHLY(12),
    QUARTERLY(4),
    ANNUALLY(1),
    AT_MATURITY(0);

    private final int paymentsPerYear;

    InterestFrequency(int paymentsPerYear) {
        this.paymentsPerYear = paymentsPerYear;
    }

    public int getPaymentsPerYear() {
        return paymentsPerYear;
    }

    public static InterestFrequency fromInput(String input) {
        return switch (input.trim().toUpperCase()) {
            case "MONTHLY" -> MONTHLY;
            case "QUARTERLY" -> QUARTERLY;
            case "ANNUALLY", "ANNUAL", "YEARLY" -> ANNUALLY;
            case "AT MATURITY", "AT_MATURITY", "MATURITY" -> AT_MATURITY;
            default -> throw new IllegalArgumentException(
                    "Invalid frequency. Choose Monthly, Quarterly, Annually, or At Maturity.");
        };
    }
}
