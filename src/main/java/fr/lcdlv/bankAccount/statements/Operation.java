package fr.lcdlv.bankAccount.statements;

import fr.lcdlv.bankAccount.Amount;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Operation {

    private LocalDateTime operationDate;
    private OperationType operationType;
    private Amount amount;
    private Amount balanceAfterOperation;

    private Operation(LocalDateTime operationDate, OperationType operationType, Amount amount) {
        this.operationDate = operationDate;
        this.operationType = operationType;
        this.amount = amount;
    }

    public Amount performOperationOnAmount(Amount amount) {
        Amount balance = amount;
        if (operationType.equals(OperationType.DEPOSIT)) {
            balance = amount.add(this.amount);
        } else if (operationType.equals(OperationType.WITHDRAWAL)) {
            balance = amount.subtract(this.amount);
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
        statementPrinter.appendSeparator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (!Objects.equals(operationDate, operation.operationDate))
            return false;
        if (operationType != operation.operationType) return false;
        if (!Objects.equals(amount, operation.amount)) return false;
        return Objects.equals(balanceAfterOperation, operation.balanceAfterOperation);
    }

    @Override
    public int hashCode() {
        int result = operationDate != null ? operationDate.hashCode() : 0;
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (balanceAfterOperation != null ? balanceAfterOperation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "\noperationDate=" + operationDate +
                ", \noperationType=" + operationType +
                ", \namount=" + amount +
                ", \nbalanceAfterOperation=" + balanceAfterOperation +
                '}';
    }

    public static final class OperationBuilder {
        private LocalDateTime operationDate;
        private OperationType operationType;
        private Amount amount;

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

        public OperationBuilder withAmount(Amount amount) {
            this.amount = amount;
            return this;
        }

        public Operation build() {
            return new Operation(operationDate, operationType, amount);
        }
    }
}
