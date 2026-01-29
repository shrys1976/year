package `in`.shrys.year.util

import java.util.Calendar

/**
 * Utility object for date calculations used by the YEAR widget.
 */
object DateUtils {

    /**
     * Returns whether the given year is a leap year.
     */
    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    /**
     * Returns the total number of days in the given year (365 or 366).
     */
    fun getDaysInYear(year: Int): Int {
        return if (isLeapYear(year)) 366 else 365
    }

    /**
     * Returns the current day of the year (1-based).
     * January 1st = 1, December 31st = 365 or 366.
     */
    fun getDayOfYear(): Int {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
    }

    /**
     * Returns the current year.
     */
    fun getCurrentYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR)
    }
}
