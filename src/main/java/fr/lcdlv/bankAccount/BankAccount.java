package fr.lcdlv.bankAccount;

import fr.lcdlv.bankAccount.statements.Operation;
import fr.lcdlv.bankAccount.statements.StatementPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BankAccount {

    private String reference;
    private Amount amount;
    private List<Operation> operations;

    BankAccount(String reference, Amount initialAmount) {
        this.reference = reference;
        this.amount = initialAmount;
        this.operations = new ArrayList<>();
    }

    Amount getTotalAmount() {
        return this.amount;
    }

    void performOperation(Operation operation) {
        this.amount = operation.performOperationOnAmount(this.amount);
        this.operations.add(operation);
    }

    boolean referenceIs(String referenceToTest) {
        return Objects.equals(this.reference, referenceToTest);
    }

    List<Operation> retrieveHistory() {
        return this.operations;
    }

    String getHistory() {
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
