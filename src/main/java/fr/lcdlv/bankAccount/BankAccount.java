package fr.lcdlv.bankAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BankAccount {

    private String reference;
    private double amount;
    private List<Operation> operations;

    BankAccount(String reference, double initialAmount) {
        this.reference = reference;
        this.amount = initialAmount;
        this.operations = new ArrayList<>();
    }

    double getTotalAmount() {
        return this.amount;
    }

    void makeADepositOf(double amount) {
        this.amount += amount;
    }

    void performOperation(Operation operation) {
        this.amount = operation.performOperationOnAmount(this.amount);
        this.operations.add(operation);
    }

    void makeAWithdrawalOf(double amount) {
        this.amount -= amount;
    }

    boolean referenceIs(String referenceToTest) {
        return Objects.equals(this.reference, referenceToTest);
    }

    List<Operation> retrieveHistory() {
        return this.operations;
    }
}
