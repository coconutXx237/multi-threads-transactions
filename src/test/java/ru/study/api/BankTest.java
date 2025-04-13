package ru.study.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    Bank bank = new Bank("src/main/resources/test.csv");
    final double initialBalance = bank.getTotalAmount();
    final double delta = 0.001;

    @BeforeEach
    public void setUp() {
        bank = new Bank("src/main/resources/test.csv");
    }


    @RepeatedTest(5)
    void ProcessingTransfersTest_1_10() {
        bank.startProcessingTransfers(1, 10);
        double resultingBalance = bank.getTotalAmount();
        assertEquals(initialBalance, resultingBalance, delta);
    }

    @RepeatedTest(5)
    void ProcessingTransfersTest_10_1() {
        bank.startProcessingTransfers(10, 1);
        double resultingBalance = bank.getTotalAmount();
        assertEquals(initialBalance, resultingBalance, delta);
    }

    @RepeatedTest(5)
    void ProcessingTransfersTest_100_100() {
        bank.startProcessingTransfers(100, 100);
        double resultingBalance = bank.getTotalAmount();
        assertEquals(initialBalance, resultingBalance, delta);
    }

    @RepeatedTest(5)
    void ProcessingTransfersTest_10000_100() {
        bank.startProcessingTransfers(10000, 100);
        double resultingBalance = bank.getTotalAmount();
        assertEquals(initialBalance, resultingBalance, delta);
    }
}
