package org.kurodev.data.repository;

import org.kurodev.data.entity.server.ServerRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServerMentionRepository extends CrudRepository<ServerRole, Integer> {
    List<ServerRole> findByServerId(Long serverId);
    void deleteByServerIdAndRoleId(Long serverId, Long roleId);
}
