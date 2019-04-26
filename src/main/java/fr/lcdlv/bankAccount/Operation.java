package fr.lcdlv.bankAccount;

import java.time.LocalDateTime;
import java.util.Objects;

class Operation {

    private LocalDateTime operationDate;
    private OperationType operationType;
    private double amount;

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

    double performOperationOnAmount(double amount) {
        if (operationType.equals(OperationType.DEPOSIT)) {
            return amount + this.amount;
        } else if (operationType.equals(OperationType.WITHDRAWAL)) {
            return amount - this.amount;
        }

        return amount;
    }

    static final class OperationBuilder {
        private LocalDateTime operationDate;
        private OperationType operationType;
        private double amount;

        private OperationBuilder() {
        }

        static OperationBuilder anOperation() {
            return new OperationBuilder();
        }

        OperationBuilder withOperationDate(LocalDateTime operationDate) {
            this.operationDate = operationDate;
            return this;
        }

        OperationBuilder withOperationType(OperationType operationType) {
            this.operationType = operationType;
            return this;
        }

        OperationBuilder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        Operation build() {
            return new Operation(operationDate, operationType, amount);
        }
    }
}
