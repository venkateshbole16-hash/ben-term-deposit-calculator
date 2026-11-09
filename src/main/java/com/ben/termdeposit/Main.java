package com.ben.termdeposit;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TermDepositCalculator calculator = new TermDepositCalculator();

            try {
                System.out.println("=== BEN Term Deposit Calculator ===");

                System.out.print("Enter deposit amount: ");
                BigDecimal principal = new BigDecimal(scanner.nextLine().trim());

                System.out.print("Enter annual interest rate (%): ");
                BigDecimal interestRate = new BigDecimal(scanner.nextLine().trim());

                System.out.print("Enter investment term (whole years): ");
                int termInYears = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Enter interest frequency (Monthly, Quarterly, Annually, At Maturity): ");
                InterestFrequency frequency = InterestFrequency.fromInput(scanner.nextLine());

                BigDecimal finalBalance = calculator.calculate(
                        principal, interestRate, termInYears, frequency);

                System.out.println("-----------------------------------");
                System.out.println("Final balance: " + finalBalance.toPlainString());
                System.out.println("-----------------------------------");
            } catch (NumberFormatException ex) {
                System.out.println("Error: Please enter valid numeric values.");
            } catch (IllegalArgumentException | ArithmeticException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
