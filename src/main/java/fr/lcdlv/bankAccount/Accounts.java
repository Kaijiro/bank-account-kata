package fr.lcdlv.bankAccount;

import java.util.Arrays;
import java.util.List;

class Accounts {

    private List<BankAccount> accountList;

    Accounts(BankAccount... accountList) {
        this.accountList = Arrays.asList(accountList);
    }

    BankAccount retrieveAccountWithReference(String searchedReference) throws AccountNotFoundException {
        return this.accountList.stream()
                .filter(acc -> acc.referenceIs(searchedReference))
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
    }
}
