# BEN Term Deposit Calculator

A simple Java 17 command-line application for calculating a term deposit's final balance.

## Requirements
- Java 17+
- Maven 3.8+

## Run the application
From the project root:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.ben.termdeposit.Main"
```

Alternatively, import the project into your IDE as a Maven project and run `com.ben.termdeposit.Main`.

## Run tests

```bash
mvn test
```

## Calculation assumptions
- The annual interest rate is entered as a percentage, e.g. `1.10` means 1.10%.
- Monthly, quarterly and annual interest is reinvested and compounded at the selected payment frequency.
- At maturity, interest is paid once at the end of the term and is calculated using simple interest. This matches the exercise example: $10,000 at 1.10% for 3 years = $10,330.
- The term is entered as a positive whole number of years.
- The final balance is rounded to two decimal places using HALF_UP rounding.

## Example
The program accepts a plain amount or a dollar amount with correctly grouped commas,
and accepts the rate with or without a trailing percent sign.

```text
=== BEN Term Deposit Calculator ===
Start deposit amount (e.g. 10000): $10,000
Annual interest rate (e.g. 1.10 for 1.10%): 1.10%
Investment term (whole years, e.g. 3): 3
Interest paid (Monthly, Quarterly, Annually, At Maturity): At Maturity
-----------------------------------
Final balance: $10,330.00
-----------------------------------
```

Invalid numbers, unsupported payment frequencies, negative rates, non-positive
deposits, and non-positive terms produce an error message.
