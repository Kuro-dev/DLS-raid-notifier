package org.kurodev.data;

import org.kurodev.data.entity.server.ServerRole;
import org.kurodev.data.repository.ServerMentionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentionService {
    private final ServerMentionRepository repository;

    @Autowired
    public MentionService(ServerMentionRepository repository) {
        this.repository = repository;
    }

    public List<ServerRole> getServerRoleOptions(long serverId) {
        return repository.findByServerId(serverId);
    }

    public Iterable<ServerRole> save(List<ServerRole> rollen) {
        return repository.saveAll(rollen);
    }

    public void delete(List<Integer> dbIdsToDelete) {
        repository.deleteAllById(dbIdsToDelete);
    }
}
