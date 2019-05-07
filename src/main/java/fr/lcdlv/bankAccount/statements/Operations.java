package fr.lcdlv.bankAccount.statements;

import java.util.ArrayList;
import java.util.List;

public class Operations {

    private List<Operation> operations;

    public Operations() {
        this.operations = new ArrayList<>();
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public List<Operation> getOperations() {
        return new ArrayList<>(this.operations);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operations that = (Operations) o;

        if(operations.size() != that.operations.size()){
            return false;
        }

        for(int i = 0; i < operations.size() ; i++){
            if(!operations.get(i).equals(that.operations.get(i))){
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return operations != null ? operations.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Operations{" +
                "\noperations=" + operations +
                '}';
    }
}
