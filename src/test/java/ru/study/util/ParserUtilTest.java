package ru.study.util;

import org.junit.jupiter.api.Test;
import ru.study.model.Account;

import static org.junit.jupiter.api.Assertions.*;

public class ParserUtilTest {

    @Test
    void properCsvIsParsedToMapSuccessfully() {
        var resultMap = ParserUtil.parseCsvToMap("src/main/resources/accounts.csv");

        assertEquals(20, resultMap.size());
        assertInstanceOf(Account.class, resultMap.get("bc97bfdc-c34d-44cc-b428-245971b8943f"));
    }

    @Test
    void badCsvPathThrowsException() {
        assertThrows(RuntimeException.class, () -> ParserUtil.parseCsvToMap("src/main/resources/wrong.csv"));
    }
}
