package ru.study.service;

import ru.study.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomizerService {

    private static final Random random = new Random();
    private final Map<String, Account> accounts;
    private final int accountsCount;

    public RandomizerService(Map<String, Account> accounts) {
        this.accounts = accounts;
        accountsCount = accounts.size();
    }

    public String[] getRandomAccounts() {
        List<String> accountIds = new ArrayList<>(accounts.keySet());
        String fromAccountId = accountIds.get(random.nextInt(accountsCount));
        String toAccountId;

        do {
            toAccountId = accountIds.get(random.nextInt(accountsCount));
        } while (fromAccountId.equals(toAccountId));

        return new String[]{fromAccountId, toAccountId};
    }

    public double getRandomAmount(Account account) {
        return random.nextDouble(account.getAmount());
    }
}
