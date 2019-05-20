package fr.lcdlv.bankAccount;

import fr.lcdlv.bankAccount.statements.Operation;
import fr.lcdlv.bankAccount.statements.OperationType;
import fr.lcdlv.bankAccount.statements.Operations;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static fr.lcdlv.bankAccount.statements.Operation.OperationBuilder.anOperation;
import static org.assertj.core.api.Assertions.assertThat;

class BankAccountTest {

    private static final Amount ACCOUNT_INITIAL_BALANCE = Amount.of(100000);

    @Test
    void asAClientIfISaveMoneyMyBankAccountShouldBeIncreasedOfThisAmount(){
        BankAccount bankAccount = new BankAccount(ACCOUNT_INITIAL_BALANCE);
        Amount amountToDeposit = Amount.of(10000);
        Operation depositOperation = anOperation()
                .withOperationType(OperationType.DEPOSIT)
                .withOperationDate(LocalDateTime.of(2019, 4, 26, 16, 0))
                .withAmount(Amount.of(10000))
                .build();

        bankAccount.performOperation(depositOperation);

        Amount accountBalanceExpected = ACCOUNT_INITIAL_BALANCE.add(amountToDeposit);
        assertThat(bankAccount.getTotalAmount()).isEqualTo(accountBalanceExpected);
    }

    @Test
    void asAClientIfIWithdrawMoneyMyBankAccountShouldBeWithdrawnOfThisAmount(){
        BankAccount bankAccount = new BankAccount(ACCOUNT_INITIAL_BALANCE);
        Amount amountToWithdraw = Amount.of(7000);

        Operation operation = anOperation()
                .withOperationType(OperationType.WITHDRAWAL)
                .withOperationDate(LocalDateTime.of(2019, 4, 26, 16, 0))
                .withAmount(amountToWithdraw)
                .build();

        bankAccount.performOperation(operation);

        Amount accountBalanceExpected = ACCOUNT_INITIAL_BALANCE.subtract(amountToWithdraw);
        assertThat(bankAccount.getTotalAmount()).isEqualTo(accountBalanceExpected);
    }

    @Test
    void asAClientIWantToBeAbleToSeeMyAccountHistory(){
        BankAccount bankAccount = new BankAccount(ACCOUNT_INITIAL_BALANCE);

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

        Operations operations = new Operations();
        operations.addOperation(firstDeposit);
        operations.addOperation(firstWithdrawal);
        operations.addOperation(secondWithdrawal);
        operations.addOperation(secondDeposit);

        assertThat(bankAccount.retrieveOperationHistory()).isEqualTo(operations);
    }

    @Test
    void asAClientIWantToPrintMyAccountHistory() throws IOException {
        BankAccount bankAccount = new BankAccount(Amount.of(250000));

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
        assertThat(bankAccount.getHistory()).isEqualTo(expectedPrinterContent);
    }
}
