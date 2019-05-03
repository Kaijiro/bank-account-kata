package fr.lcdlv.bankAccount;

import fr.lcdlv.bankAccount.statements.Operation;
import fr.lcdlv.bankAccount.statements.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static fr.lcdlv.bankAccount.statements.Operation.OperationBuilder.anOperation;
import static org.assertj.core.api.Assertions.assertThat;

class BankAccountTest {

    private static final String ACCOUNT_1_REFERENCE = "123456789";
    private static final String ACCOUNT_2_REFERENCE = "987654321";
    private static final Amount ACCOUNT_1_INITIAL_BALANCE = Amount.of(123456);
    private static final Amount ACCOUNT_2_INITIAL_BALANCE = Amount.of(250000);

    private Accounts accounts;

    @BeforeEach
    void setup() {
        accounts = new Accounts(
                new BankAccount(ACCOUNT_1_REFERENCE, ACCOUNT_1_INITIAL_BALANCE),
                new BankAccount(ACCOUNT_2_REFERENCE, ACCOUNT_2_INITIAL_BALANCE)
        );
    }

    @Test
    void asAClientIfISaveMoneyMyBankAccountShouldBeIncreasedOfThisAmount() throws AccountNotFoundException {
        BankAccount bankAccount = accounts.retrieveAccountWithReference(ACCOUNT_1_REFERENCE);
        Amount amountToDeposit = Amount.of(10000);
        Operation depositOperation = anOperation()
                .withOperationType(OperationType.DEPOSIT)
                .withOperationDate(LocalDateTime.of(2019, 4, 26, 16, 0))
                .withAmount(Amount.of(10000))
                .build();

        bankAccount.performOperation(depositOperation);

        Amount accountBalanceExpected = ACCOUNT_1_INITIAL_BALANCE.add(amountToDeposit);
        assertThat(bankAccount.getTotalAmount()).isEqualTo(accountBalanceExpected);
    }

    @Test
    void asAClientIfIWithdrawMoneyMyBankAccountShouldBeWithdrawnOfThisAmount() throws AccountNotFoundException {
        BankAccount bankAccount = accounts.retrieveAccountWithReference(ACCOUNT_1_REFERENCE);
        Amount amountToWithdraw = Amount.of(7000);

        Operation operation = anOperation()
                .withOperationType(OperationType.WITHDRAWAL)
                .withOperationDate(LocalDateTime.of(2019, 4, 26, 16, 0))
                .withAmount(amountToWithdraw)
                .build();

        bankAccount.performOperation(operation);

        Amount accountBalanceExpected = ACCOUNT_1_INITIAL_BALANCE.subtract(amountToWithdraw);
        assertThat(bankAccount.getTotalAmount()).isEqualTo(accountBalanceExpected);
    }

    @Test
    void asAClientIWantToBeAbleToSeeMyAccountHistory() throws AccountNotFoundException {
        BankAccount bankAccount = accounts.retrieveAccountWithReference(ACCOUNT_2_REFERENCE);

        Operation firstDeposit = anOperation()
                .withOperationType(OperationType.DEPOSIT)
                .withOperationDate(LocalDateTime.of(2019, 4, 26, 16, 0))
                .withAmount(Amount.of(100000))
                .build();
        Operation firstWithdrawal = anOperation()
                .withOperationType(OperationType.WITHDRAWAL)
                .withOperationDate(LocalDateTime.of(2019, 4, 27, 9, 0))
                .withAmount(Amount.of(40000))
                .build();
        Operation secondWithdrawal = anOperation()
                .withOperationType(OperationType.WITHDRAWAL)
                .withOperationDate(LocalDateTime.of(2019, 4, 27, 16, 0))
                .withAmount(Amount.of(10000))
                .build();
        Operation secondDeposit = anOperation()
                .withOperationType(OperationType.DEPOSIT)
                .withOperationDate(LocalDateTime.of(2019, 4, 28, 14, 0))
                .withAmount(Amount.of(70000))
                .build();

        bankAccount.performOperation(firstDeposit);
        bankAccount.performOperation(firstWithdrawal);
        bankAccount.performOperation(secondWithdrawal);
        bankAccount.performOperation(secondDeposit);

        List<Operation> operations = Arrays.asList(firstDeposit, firstWithdrawal, secondWithdrawal, secondDeposit);

        assertThat(bankAccount.retrieveHistory()).hasSameElementsAs(operations);
    }

    @Test
    void asAClientIWantToPrintMyAccountHistory() throws IOException, AccountNotFoundException {
        BankAccount bankAccount = accounts.retrieveAccountWithReference(ACCOUNT_2_REFERENCE);

        Operation firstDeposit = anOperation()
                .withOperationType(OperationType.DEPOSIT)
                .withOperationDate(LocalDateTime.of(2019, 4, 26, 16, 0))
                .withAmount(Amount.of(100000))
                .build();
        Operation firstWithdrawal = anOperation()
                .withOperationType(OperationType.WITHDRAWAL)
                .withOperationDate(LocalDateTime.of(2019, 4, 27, 9, 0))
                .withAmount(Amount.of(40000))
                .build();
        Operation secondWithdrawal = anOperation()
                .withOperationType(OperationType.WITHDRAWAL)
                .withOperationDate(LocalDateTime.of(2019, 4, 27, 16, 0))
                .withAmount(Amount.of(10000))
                .build();
        Operation secondDeposit = anOperation()
                .withOperationType(OperationType.DEPOSIT)
                .withOperationDate(LocalDateTime.of(2019, 4, 28, 14, 0))
                .withAmount(Amount.of(70000))
                .build();

        bankAccount.performOperation(firstDeposit);
        bankAccount.performOperation(firstWithdrawal);
        bankAccount.performOperation(secondWithdrawal);
        bankAccount.performOperation(secondDeposit);

        String expectedPrinterContent = String.join("\n", Files.readAllLines(Paths.get("src/test/resources/printer_reference.txt")));
        System.out.println(bankAccount.getHistory());
        assertThat(bankAccount.getHistory()).isEqualTo(expectedPrinterContent);
    }
}
