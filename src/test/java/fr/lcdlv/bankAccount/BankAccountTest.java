package fr.lcdlv.bankAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static fr.lcdlv.bankAccount.Operation.OperationBuilder.anOperation;
import static org.assertj.core.api.Assertions.assertThat;

class BankAccountTest {

    private static final String ACCOUNT_1_REFERENCE = "123456789";
    private static final String ACCOUNT_2_REFERENCE = "987654321";
    private static final double ACCOUNT_1_INITIAL_BALANCE = 1234.56;
    private static final double ACCOUNT_2_INITIAL_BALANCE = 2500.00;

    private Accounts accounts;

    @BeforeEach
    void setup() {
        accounts = new Accounts(
                new BankAccount(ACCOUNT_1_REFERENCE, ACCOUNT_1_INITIAL_BALANCE),
                new BankAccount(ACCOUNT_2_REFERENCE, ACCOUNT_2_INITIAL_BALANCE)
        );
    }

    @Test
    void envShouldBeSetupAndRunning() {
        assertThat(true).isTrue();
    }

    @Test
    void asAClientIfISaveMoneyMyBankAccountShouldBeIncreasedOfThisAmount() throws AccountNotFoundException {
        BankAccount bankAccount = accounts.retrieveAccountWithReference(ACCOUNT_1_REFERENCE);
        double amountToDeposit = 100.0;
        bankAccount.makeADepositOf(amountToDeposit);

        assertThat(bankAccount.getTotalAmount()).isEqualTo(ACCOUNT_1_INITIAL_BALANCE + amountToDeposit);
    }

    @Test
    void asAClientIfIWithdrawMoneyMyBankAccountShouldBeWithdrawnOfThisAmount() throws AccountNotFoundException {
        BankAccount bankAccount = accounts.retrieveAccountWithReference(ACCOUNT_1_REFERENCE);
        double amountToWithdraw = 70.0;
        bankAccount.makeAWithdrawalOf(amountToWithdraw);

        assertThat(bankAccount.getTotalAmount()).isEqualTo(ACCOUNT_1_INITIAL_BALANCE - amountToWithdraw);
    }

    @Test
    void asAClientIWantToBeAbleToSeeMyAccountHistory() throws AccountNotFoundException {
        BankAccount bankAccount = accounts.retrieveAccountWithReference(ACCOUNT_2_REFERENCE);

        Operation firstDeposit = anOperation()
                .withOperationType(OperationType.DEPOSIT)
                .withOperationDate(LocalDateTime.of(2019, 4, 26, 16, 0))
                .withAmount(1000)
                .build();
        Operation firstWithdrawal = anOperation()
                .withOperationType(OperationType.WITHDRAWAL)
                .withOperationDate(LocalDateTime.of(2019, 4, 27, 9, 0))
                .withAmount(400)
                .build();
        Operation secondWithdrawal = anOperation()
                .withOperationType(OperationType.WITHDRAWAL)
                .withOperationDate(LocalDateTime.of(2019, 4, 27, 16, 0))
                .withAmount(100)
                .build();
        Operation secondDeposit = anOperation()
                .withOperationType(OperationType.DEPOSIT)
                .withOperationDate(LocalDateTime.of(2019, 4, 28, 14, 0))
                .withAmount(700)
                .build();

        bankAccount.performOperation(firstDeposit);
        bankAccount.performOperation(firstWithdrawal);
        bankAccount.performOperation(secondWithdrawal);
        bankAccount.performOperation(secondDeposit);

        List<Operation> operations = Arrays.asList(firstDeposit, firstWithdrawal, secondWithdrawal, secondDeposit);

        assertThat(bankAccount.retrieveHistory()).hasSameElementsAs(operations);
    }
}
