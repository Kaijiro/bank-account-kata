package fr.lcdlv.bankAccount;

import fr.lcdlv.bankAccount.statements.Operation;
import fr.lcdlv.bankAccount.statements.StatementPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankAccount {

    private String reference;
    private double amount;
    private List<Operation> operations;

    BankAccount(String reference, double initialAmount) {
        this.reference = reference;
        this.amount = initialAmount;
        this.operations = new ArrayList<>();
    }

    public double getTotalAmount() {
        return this.amount;
    }

    public void performOperation(Operation operation) {
        this.amount = operation.performOperationOnAmount(this.amount);
        this.operations.add(operation);
    }

    boolean referenceIs(String referenceToTest) {
        return Objects.equals(this.reference, referenceToTest);
    }

    public List<Operation> retrieveHistory() {
        return this.operations;
    }

    public String getHistory(){
        StatementPrinter statementPrinter = new StatementPrinter();

        statementPrinter.createHeader();
        statementPrinter.newLine();

        for(Operation operation : operations){
            operation.addSelfToStatementPrinter(statementPrinter);
            statementPrinter.newLine();
        }

        return statementPrinter.toString();
    }
}
