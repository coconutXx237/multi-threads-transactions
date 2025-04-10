package ru.study.util;

import org.junit.jupiter.api.Test;
import ru.study.model.Account;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CounterUtilTest {

    ConcurrentHashMap<String, Account> resultMap = ParserUtil.parseCsvToMap("src/main/resources/accounts.csv");

    @Test
    void countsTotalAmountCorrectly() {
        var result = CounterUtil.countTotalAmount(resultMap);
        assertEquals(1319353.04, result);
    }
}
