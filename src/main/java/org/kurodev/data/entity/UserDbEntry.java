package org.kurodev.data.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_availability")
public class UserDbEntry {
    @Id
    @Column(name = "user_id")
    private long id;

    @ElementCollection
    @CollectionTable(name = "user_events", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "event_name")
    private List<String> eventNames;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<TimeFrame> timeFrames;

    // Constructors, getters, and setters
    public UserDbEntry() {
    }

    public UserDbEntry(long id, List<String> eventNames, List<TimeFrame> timeFrames) {
        this.id = id;
        this.eventNames = eventNames;
        this.timeFrames = timeFrames;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getEventNames() {
        return eventNames;
    }

    public void setEventNames(List<String> eventNames) {
        this.eventNames = eventNames;
    }

    public List<TimeFrame> getTimeFrames() {
        return timeFrames;
    }

    public void setTimeFrames(List<TimeFrame> timeFrames) {
        this.timeFrames = timeFrames;
    }
}