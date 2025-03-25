package org.kurodev.data.entity.server;

import jakarta.persistence.*;

@Entity
@Table(name = "server_roles")
public class ServerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "server_id", nullable = false)
    private long serverId;

    @Column(name = "role_name", nullable = false)
    private String name;

    // Constructors
    public ServerRole() {
    }

    public ServerRole(Long roleId, long serverId, String name) {
        this.roleId = roleId;
        this.serverId = serverId;
        this.name = name;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long event) {
        this.roleId = event;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}