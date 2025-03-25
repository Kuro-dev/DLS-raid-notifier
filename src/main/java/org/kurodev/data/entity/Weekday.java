package org.kurodev.data.entity;

import java.time.DayOfWeek;

/**
 * Weekday wrapper to also allow use of "all weekdays" and "all weekends" as flags
 */
public enum Weekday {
    MONDAY(DayOfWeek.MONDAY),
    TUESDAY(DayOfWeek.TUESDAY),
    WEDNESDAY(DayOfWeek.WEDNESDAY),
    THURSDAY(DayOfWeek.THURSDAY),
    FRIDAY(DayOfWeek.FRIDAY),
    SATURDAY(DayOfWeek.SATURDAY),
    SUNDAY(DayOfWeek.SUNDAY),
    WEEKDAYS(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
    WEEKENDS(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
    ;

    private final DayOfWeek[] days;

    Weekday(DayOfWeek... days) {

        this.days = days;
    }


    public DayOfWeek[] getDays() {
        return days;
    }
}
