package org.kurodev.data.entity.user;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_availability")
public class UserDbEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "server_id")
    private long serverId;

    @ElementCollection
    @CollectionTable(name = "user_events", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "event_name")
    private List<Long> roleIds;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<TimeFrame> timeFrames;

    // Required no-arg constructor
    public UserDbEntry() {
    }

    public UserDbEntry(long userId, long serverId, List<Long> roleIds, List<TimeFrame> timeFrames) {
        this.userId = userId;
        this.serverId = serverId;
        this.roleIds = roleIds;
        this.timeFrames = timeFrames;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> eventNames) {
        this.roleIds = eventNames;
    }

    public List<TimeFrame> getTimeFrames() {
        return timeFrames;
    }

    public void setTimeFrames(List<TimeFrame> timeFrames) {
        this.timeFrames = timeFrames;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}