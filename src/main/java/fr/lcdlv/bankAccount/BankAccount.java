package fr.lcdlv.bankAccount;

import java.util.Objects;

class BankAccount {

    private String reference;
    private double amount;

    BankAccount(String reference, double initialAmount) {
        this.reference = reference;
        this.amount = initialAmount;
    }

    double getTotalAmount() {
        return this.amount;
    }

    void makeADepositOf(double amount) {
        this.amount += amount;
    }

    void makeAWithdrawalOf(double amount) {
        this.amount -= amount;
    }

    boolean referenceIs(String referenceToTest) {
        return Objects.equals(this.reference, referenceToTest);
    }
}
