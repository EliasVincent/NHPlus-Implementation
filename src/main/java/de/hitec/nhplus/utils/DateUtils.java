package de.hitec.nhplus.utils;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {

    /**
     * This method checks if the time between the creation date and today is more than 10 years.
     * @param creationDate The creation date of the item.
     * @return True if the time between the creation date and today is more than 10 years, false otherwise.
     */
    public static boolean isAtLeastTenYears(LocalDate creationDate) {
        LocalDate today = LocalDate.now();
        Period period = Period.between(creationDate, today);
        return period.getYears() >= 10;
    }

}
