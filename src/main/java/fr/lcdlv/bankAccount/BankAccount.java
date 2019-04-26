package fr.lcdlv.bankAccount;

class BankAccount {

    private double amount;

    BankAccount(double initialAmount) {
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
}
