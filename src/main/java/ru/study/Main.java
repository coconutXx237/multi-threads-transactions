package ru.study;

import ru.study.api.Bank;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class Main extends Thread {
    public static void main( String[] args ) {
        Bank bank = new Bank("src/main/resources/accounts.csv");
        System.out.println("init bank totalAmount: " + bank.getTotalAmount());

        var tasks = IntStream.range(0, 10)
                .mapToObj(i -> CompletableFuture.runAsync(() -> {
                    System.out.println("Thread " + i + " STARTED");
                    bank.startProcessingTransfers(10);
                    System.out.println("Thread " + i + " FINISHED");
                }))
                .toList();

        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();

        System.out.println("final bank totalAmount: " + bank.getTotalAmount());
    }
}
