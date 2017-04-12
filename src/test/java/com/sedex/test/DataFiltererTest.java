package com.sedex.test;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static org.junit.Assert.assertTrue;

public class DataFiltererTest {
    @Test
    public void whenEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
    }

    @Test
    public void testFilterByCountry() throws FileNotFoundException {
        assertTrue(!DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "GB").isEmpty());
    }

    @Test
    public void testFilterByCountryWithResponseTimeAboveLimit() throws FileNotFoundException {
        // given
        String filename = "src/test/resources/multi-lines";
        Reader source = openFile(filename);
        String country = "GB";
        int limit = 56;

        assertTrue(!DataFilterer.filterByCountryWithResponseTimeAboveLimit(source, country, limit).isEmpty());
    }

    @Test
    public void testFilterByCountryWithResponseTimeAboveAverage() throws FileNotFoundException {
        // given
        String filename = "src/test/resources/multi-lines";
        Reader source = openFile(filename);

        assertTrue(!DataFilterer.filterByResponseTimeAboveAverage(source).isEmpty());
    }

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
}
