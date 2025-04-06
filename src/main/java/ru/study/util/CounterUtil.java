package ru.study.util;

import ru.study.model.Account;

import java.util.Map;

public class CounterUtil {
    public static double countTotalAmount(Map<String, Account> accounts) {
        double count = 0.0;
        for (Account account : accounts.values()) {
            count += account.getAmount();
        }
        return count;
    }
}
