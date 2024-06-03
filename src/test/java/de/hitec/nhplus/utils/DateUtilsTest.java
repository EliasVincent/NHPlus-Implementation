package de.hitec.nhplus.utils;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class DateUtilsTest {

    /**
     * This method tests whether a given date is at least ten years old.
     * @param date given date
     * @param expected expected Boolean value, True if the date is equal to or greater than 10 years
     * TestDate: 02-06-2024
     */
    @ParameterizedTest
    @CsvSource({
        "2012-06-01, true",
        "2013-06-01, true",
        "2014-06-01, true",
        "2020-01-01, false",
        "2000-01-01, true"
    })
    public void ifAtLeastTenYears(String date, boolean expected) {
        assertEquals(expected, DateUtils.isAtLeastTenYears(LocalDate.parse(date)));
    }


}