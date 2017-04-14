package com.sedex.test;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Collection;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DataFiltererTest {
    @Test
    public void whenEmpty() throws FileNotFoundException {
        String filename = "src/test/resources/empty";
        String country = "GB";

        assertTrue(DataFilterer.filterByCountry(openFile(filename), country).isEmpty());
    }

    @Test
    public void testFilterByCountry() throws FileNotFoundException {
        String filename = "src/test/resources/multi-lines";
        String country = "US";

        Collection<LogEntry> logEntries = DataFilterer.filterByCountry(openFile(filename), country);

        int expectedSize = 3;

        assertThat(logEntries.size(), is(expectedSize));

        for (LogEntry logEntry: logEntries) {
            assertTrue(logEntry.getCountryCode().equals(country));
        }
    }

    @Test
    public void testFilterByCountryWithResponseTimeAboveLimit() throws FileNotFoundException {
        // given
        String filename = "src/test/resources/multi-lines";
        Reader source = openFile(filename);
        String country = "US";
        int limit = 750;    // milliseconds

        Collection<LogEntry> logEntries = DataFilterer.filterByCountryWithResponseTimeAboveLimit(source, country, limit);

        int expectedSize = 2;

        assertThat(logEntries.size(), is(expectedSize));

        for (LogEntry logEntry: logEntries) {
            assertTrue(logEntry.getCountryCode().equals(country));
            assertTrue(logEntry.getResponseTime() > limit);
        }
    }

    @Test
    public void testFilterByResponseTimeAboveAverage() throws FileNotFoundException {
        // given
        String filename = "src/test/resources/multi-lines";
        Reader source = openFile(filename);
        int averageResponseTime = 526;      // milliseconds

        Collection<LogEntry> logEntries = DataFilterer.filterByResponseTimeAboveAverage(source);

        int expectedSize = 3;

        assertThat(logEntries.size(), is(expectedSize));

        for (LogEntry logEntry: logEntries) {
            assertTrue(logEntry.getResponseTime() > averageResponseTime);
        }
    }

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
}
