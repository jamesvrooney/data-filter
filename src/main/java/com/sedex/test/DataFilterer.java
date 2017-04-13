package com.sedex.test;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataFilterer {
    private static final String COMMA_SEPARATOR = ",";

    public static Collection<LogEntry> filterByCountry(Reader source, String country) {
        BufferedReader br = new BufferedReader(source);

        List<LogEntry> logEntries = new ArrayList<>();

        try {
            // skip 1st line
            br.readLine();

            logEntries = br.lines()
                    .map(convertLogEntryStringToObject)
                    .filter(byCountry(country))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logEntries;
    }

    public static Collection<LogEntry> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
        BufferedReader br = new BufferedReader(source);

        List<LogEntry> logEntries = new ArrayList<>();

        try {
            // skip 1st line
            br.readLine();

            logEntries = br.lines()
                    .map(convertLogEntryStringToObject)
                    .filter(byCountry(country))
                    .filter(byResponseTimeAboveLimit(limit))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logEntries;
    }

    public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
        return Collections.emptyList();
    }

    private static Predicate<LogEntry> byCountry(String country) {
        return logEntry -> logEntry.getCountryCode().equals(country);
    }

    private static Predicate<LogEntry> byResponseTimeAboveLimit(long limit) {
        return logEntry -> logEntry.getResponseTime() > limit;
    }

    private static Function<String, LogEntry> convertLogEntryStringToObject = line -> {
        String[] logValues = line.split(COMMA_SEPARATOR);

        long requestTimstamp = Long.parseLong(logValues[0]);
        String countryCode = logValues[1];
        long responseTime = Long.parseLong(logValues[2]);

        LogEntry logEntry = new LogEntry(requestTimstamp, countryCode, responseTime);

        return logEntry;
    };
}
