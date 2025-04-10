package ru.study.service;

import org.junit.jupiter.api.Test;
import ru.study.model.Account;
import ru.study.util.ParserUtil;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class RandomizerServiceTest {

    ConcurrentHashMap<String, Account> resultMap = ParserUtil.parseCsvToMap("src/main/resources/accounts.csv");
    RandomizerService randomizerService = new RandomizerService(resultMap);
    Account account = new Account("bc97bfdc-c34d-44cc-b428-245971b8943f", 100.50);

    @Test
    void returnsRandomAccount() {
        String[] result = randomizerService.getRandomAccounts();
        assertTrue(result.length > 0);
        assertEquals(2, result.length);
        assertNotEquals(result[0], result[1]);
    }

    @Test
    void returnsRandomAmount() {
        double result = randomizerService.getRandomAmount(account);
        assertTrue(0 <= result && result <= 100.500);
    }
}
