package org.kurodev.data.entity;


import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Embeddable
public class TimeFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "weekday")
    private DayOfWeek weekday;

    @Column(name = "available_from")
    private LocalTime availableFrom;

    @Column(name = "available_to")
    private LocalTime availableTo;

    // Required no-arg constructor
    public TimeFrame() {
    }

    public TimeFrame(DayOfWeek weekday, LocalTime availableFrom, LocalTime availableTo) {
        this.weekday = weekday;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    public DayOfWeek getWeekday() {
        return weekday;
    }

    public void setWeekday(DayOfWeek weekday) {
        this.weekday = weekday;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    public LocalTime getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(LocalTime availableTo) {
        this.availableTo = availableTo;
    }
}
