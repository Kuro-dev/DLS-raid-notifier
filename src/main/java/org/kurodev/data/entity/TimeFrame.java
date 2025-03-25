package org.kurodev.data.entity;


import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Embeddable
public class TimeFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "weekday")
    private Weekday weekday;

    @Column(name = "available_from")
    private LocalTime availableFrom;

    @Column(name = "available_to")
    private LocalTime availableTo;

    // Required no-arg constructor
    public TimeFrame() {
    }

    public TimeFrame(Weekday weekday, LocalTime availableFrom, LocalTime availableTo) {
        this.weekday = weekday;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
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
