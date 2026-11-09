package com.ben.termdeposit;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            run(scanner, new TermDepositCalculator());
        }
    }

    static void run(Scanner scanner, DepositCalculation calculator) {
        try {
            System.out.println("=== BEN Term Deposit Calculator ===");

            System.out.print("Start deposit amount (e.g. 10000): ");
            BigDecimal principal = parseAmount(scanner.nextLine());

            System.out.print("Annual interest rate (e.g. 1.10 for 1.10%): ");
            BigDecimal interestRate = parseRate(scanner.nextLine());

            System.out.print("Investment term (whole years, e.g. 3): ");
            int termInYears = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Interest paid (Monthly, Quarterly, Annually, At Maturity): ");
            InterestFrequency frequency = InterestFrequency.fromInput(scanner.nextLine());

            BigDecimal finalBalance = calculator.calculate(
                    principal, interestRate, termInYears, frequency);

            System.out.println("-----------------------------------");
            NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
            System.out.println("Final balance: " + currency.format(finalBalance));
            System.out.println("-----------------------------------");
        } catch (NumberFormatException ex) {
            System.out.println("Error: Please enter valid numeric values.");
        } catch (IllegalArgumentException | ArithmeticException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    static BigDecimal parseAmount(String input) {
        String normalized = input.trim();
        if (normalized.startsWith("$")) {
            normalized = normalized.substring(1).trim();
        }
        if (!normalized.matches("[+-]?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?")) {
            throw new NumberFormatException("Invalid deposit amount");
        }
        normalized = normalized.replace(",", "");
        return new BigDecimal(normalized);
    }

    static BigDecimal parseRate(String input) {
        String normalized = input.trim();
        if (normalized.endsWith("%")) {
            normalized = normalized.substring(0, normalized.length() - 1).trim();
        }
        if (!normalized.matches("[+-]?(?:\\d+(?:\\.\\d+)?|\\.\\d+)")) {
            throw new NumberFormatException("Invalid interest rate");
        }
        return new BigDecimal(normalized);
    }
}
