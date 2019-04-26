package fr.lcdlv.bankAccount;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BankAccountTest {

    @Test
    void envShouldBeSetupAndRunning() {
        assertThat(true).isTrue();
    }

    @Test
    void asAClientIfISaveMoneyMyBankAccountShouldBeCredited() {
        BankAccount bankAccount = new BankAccount(0.0);
        bankAccount.makeADepositOf(100.0);

        assertThat(bankAccount.getTotalAmount()).isEqualTo(100.0);
    }

    @Test
    void asAClientIfISaveMoneyMyBankAccountShouldBeIncreasedOfThisAmount() {
        BankAccount bankAccount = new BankAccount(150.0);
        bankAccount.makeADepositOf(100.0);

        assertThat(bankAccount.getTotalAmount()).isEqualTo(250.0);
    }

    @Test
    void asAClientIfIWithdrawMoneyMyBankAccountShouldBeWithdrawnOfThisAmount() {
        BankAccount bankAccount = new BankAccount(150.0);
        bankAccount.makeAWithdrawalOf(100.0);

        assertThat(bankAccount.getTotalAmount()).isEqualTo(50.0);
    }
}
