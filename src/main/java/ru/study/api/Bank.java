package ru.study.api;

import lombok.Data;
import ru.study.model.Account;
import ru.study.service.RandomizerService;
import ru.study.util.CounterUtil;
import ru.study.util.ParserUtil;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Data
public class Bank {

    private Map<String, Account> accountsMap;
    private final RandomizerService randomizerService;

    public Bank(String fileName) {
        this.accountsMap = ParserUtil.parseCsvToMap(fileName);
        randomizerService = new RandomizerService(accountsMap);
    }

    public void startProcessingTransfers(int threadsQuantity, int iterationsQuantity) {
        ExecutorService executor = Executors.newFixedThreadPool(threadsQuantity);
        for (int i = 0; i < threadsQuantity; i++) {
            executor.submit(() -> this.processTransfers(iterationsQuantity));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {}
    }

    public void processTransfers(int iterations) {
        for (int i = 0; i < iterations; i++) {
            String[] randomAccountsArr = randomizerService.getRandomAccounts();
            Account from = accountsMap.get(randomAccountsArr[0]);
            Account to = accountsMap.get(randomAccountsArr[1]);
            if (from.getAmount() <= 1) {
                System.out.println("ERROR > > > Мало средств на счёте " + from.getAmount() + " : " + from.getAmount());
                continue;
            }
            double randomAmount = randomizerService.getRandomAmount(from);
            if (from.getAmount() > randomAmount) {
                transfer(from, to, randomAmount);
            } else {
                System.out.println("ERROR > > > Cannot transfer amount of " + randomAmount + " from " + from + " to " + to + ", as fromAmount is " + from.getAmount());
            }
        }
    }

    private void transfer(Account from, Account to, double amount) {
        Account firstLock;
        Account secondLock;

        if (from.getAccountId().compareTo(to.getAccountId()) < 0) {
            firstLock = from;
            secondLock = to;
        } else {
            firstLock = to;
            secondLock = from;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                double currentAmountFrom = from.getAmount();
                double currentAmountTo = to.getAmount();
                double newAmountFrom = currentAmountFrom - amount;
                double newAmountTo = currentAmountTo + amount;

                System.out.println("BEFORE transfer >>> from " + accountsMap.get(from.getAccountId())
                        + ", to: " + accountsMap.get(to.getAccountId()));

                accountsMap.get(from.getAccountId()).setAmount(newAmountFrom);
                accountsMap.get(to.getAccountId()).setAmount(newAmountTo);

                System.out.println("AFTER transfer >>> from " + accountsMap.get(from.getAccountId())
                        + ", to: " + accountsMap.get(to.getAccountId()));
            }
        }
    }

    public double getTotalAmount() {
        return CounterUtil.countTotalAmount(accountsMap);
    }
}
