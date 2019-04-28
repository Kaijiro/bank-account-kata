package fr.lcdlv.bankAccount.statements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Operation {

    private LocalDateTime operationDate;
    private OperationType operationType;
    private double amount;
    private double balanceAfterOperation;

    private Operation(LocalDateTime operationDate, OperationType operationType, double amount) {
        this.operationDate = operationDate;
        this.operationType = operationType;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Double.compare(operation.amount, amount) == 0 &&
                Objects.equals(operationDate, operation.operationDate) &&
                operationType == operation.operationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationDate, operationType, amount);
    }

    public double performOperationOnAmount(double amount) {
        double balance = amount;
        if (operationType.equals(OperationType.DEPOSIT)) {
            balance = amount + this.amount;
        } else if (operationType.equals(OperationType.WITHDRAWAL)) {
            balance = amount - this.amount;
        }

        this.balanceAfterOperation = balance;
        return balance;
    }

    private String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return this.operationDate.format(formatter);
    }

    public void addSelfToStatementPrinter(StatementPrinter statementPrinter) {
        statementPrinter.append(getFormattedDate());
        statementPrinter.appendSeparator();

        if(this.operationType == OperationType.DEPOSIT){
            statementPrinter.append("+");
        } else if(this.operationType == OperationType.WITHDRAWAL){
            statementPrinter.append("-");
        }

        statementPrinter.append(String.valueOf(this.amount));
        statementPrinter.appendSeparator();
        statementPrinter.append(String.valueOf(this.balanceAfterOperation));
    }

    public static final class OperationBuilder {
        private LocalDateTime operationDate;
        private OperationType operationType;
        private double amount;

        private OperationBuilder() {
        }

        public static OperationBuilder anOperation() {
            return new OperationBuilder();
        }

        public OperationBuilder withOperationDate(LocalDateTime operationDate) {
            this.operationDate = operationDate;
            return this;
        }

        public OperationBuilder withOperationType(OperationType operationType) {
            this.operationType = operationType;
            return this;
        }

        public OperationBuilder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Operation build() {
            return new Operation(operationDate, operationType, amount);
        }
    }
}
