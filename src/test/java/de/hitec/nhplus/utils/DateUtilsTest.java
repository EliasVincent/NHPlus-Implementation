package de.hitec.nhplus.utils;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

class DateUtilsTest {

    /**
     * Diese Methode testet, ob ein angegebenes Datum mindestens zehn Jahre.
     * @param datum angegebenes Datum
     * @param erwartet erwartet Boolean Wert, True wenn das Datum gleich oder größer als 10 Jahre ist
     * TestDatum: 02-06-2024
     */
    @ParameterizedTest
    @CsvSource({
        "2012-06-01, true",
        "2013-06-01, true",
        "2014-06-01, true",
        "2020-01-01, false",
        "2000-01-01, true"
    })
    public void obIstMindestensZehnJahre (String datum,  boolean erwartet) {
        assertEquals(erwartet, DateUtils.istMindestensZehnJahre(LocalDate.parse(datum)));
    }


}