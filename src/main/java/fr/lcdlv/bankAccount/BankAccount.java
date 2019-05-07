package fr.lcdlv.bankAccount;

import fr.lcdlv.bankAccount.statements.Operation;
import fr.lcdlv.bankAccount.statements.Operations;
import fr.lcdlv.bankAccount.statements.StatementPrinter;

class BankAccount {

    private Amount amount;
    private Operations operations;

    BankAccount(Amount initialAmount) {
        this.amount = initialAmount;
        this.operations = new Operations();
    }

    Amount getTotalAmount() {
        return this.amount;
    }

    void performOperation(Operation operation) {
        this.amount = operation.performOperationOnAmount(this.amount);
        this.operations.addOperation(operation);
    }

    Operations retrieveOperationHistory() {
        return this.operations;
    }

    String getHistory() {
        StatementPrinter statementPrinter = new StatementPrinter();

        statementPrinter.createHeader();
        statementPrinter.newLine();

        for(Operation operation : operations.getOperations()){
            operation.addSelfToStatementPrinter(statementPrinter);
            statementPrinter.newLine();
        }

        return statementPrinter.toString();
    }
}
