package org.kurodev.data.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "server_events")
public class ServerEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "event_name", nullable = false)
    private String event;

    @Column(name = "server_id", nullable = false)
    private long serverId;

    // Constructors
    public ServerEvent() {
    }

    public ServerEvent(String event, long serverId) {
        this.event = event;
        this.serverId = serverId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }
}