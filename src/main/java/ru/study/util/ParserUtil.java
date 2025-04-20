package ru.study.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.study.model.Account;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParserUtil {

    public static ConcurrentHashMap<String, Account> parseCsvToMap(String filename) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .build()
                     .parse(reader)
        ) {
            return parser.getRecords().stream()
                    .map(ParserUtil::parseCsvRecordToAccount)
                    .collect(Collectors.toMap(
                            Account::getAccountId,
                            Function.identity(),
                            (a, b) -> a,
                            ConcurrentHashMap::new
                    ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Account parseCsvRecordToAccount(CSVRecord record) {
        return new Account(
                record.get("accountId"),
                Double.parseDouble(record.get("amount"))
        );
    }
}
