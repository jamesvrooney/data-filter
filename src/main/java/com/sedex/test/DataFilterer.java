package com.sedex.test;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.*;
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

            logEntries.removeAll(Collections.singleton(null));
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

            logEntries.removeAll(Collections.singleton(null));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logEntries;
    }

    public static Collection<LogEntry> filterByResponseTimeAboveAverage(Reader source) {
        BufferedReader br = new BufferedReader(source);

        List<LogEntry> logEntries = new ArrayList<>();
        List<LogEntry> filteredLogEntries = new ArrayList<>();

        try {
            // skip 1st line
            br.readLine();

            logEntries = br.lines()
                    .map(convertLogEntryStringToObject)
                    .collect(Collectors.toList());

            logEntries.removeAll(Collections.singleton(null));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        OptionalDouble averageResponseTime = logEntries.stream().mapToLong(LogEntry::getResponseTime).average();

        filteredLogEntries
                = logEntries.stream()
                .filter(byResponseTimeAboveAverage(averageResponseTime))
                .collect(Collectors.toList());


        return filteredLogEntries;
    }

    private static Predicate<LogEntry> byCountry(String country) {
        return logEntry -> logEntry.getCountryCode().equals(country);
    }

    private static Predicate<LogEntry> byResponseTimeAboveLimit(long limit) {
        return logEntry -> logEntry.getResponseTime() > limit;
    }

    private static Predicate<LogEntry> byResponseTimeAboveAverage(OptionalDouble average) {

        double averageResponseTime = average.getAsDouble();

        return logEntry -> logEntry.getResponseTime() > averageResponseTime;
    }

    private static Function<String, LogEntry> convertLogEntryStringToObject = line -> {
        String[] logValues = line.split(COMMA_SEPARATOR);

        long requestTimstamp;
        String countryCode;
        long responseTime;

        LogEntry logEntry = null;

        try {
            requestTimstamp = Long.parseLong(logValues[0]);
            countryCode = logValues[1];
            responseTime = Long.parseLong(logValues[2]);

            logEntry = new LogEntry(requestTimstamp, countryCode, responseTime);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logEntry;
    };
}
