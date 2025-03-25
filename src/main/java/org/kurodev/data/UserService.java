package org.kurodev.data;

import org.kurodev.data.entity.UserDbEntry;
import org.kurodev.data.repository.MentionTimeFrameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final MentionTimeFrameRepository repository;

    @Autowired
    public UserService(MentionTimeFrameRepository repository) {
        this.repository = repository;
    }

    public List<UserDbEntry> getEntriesByUserId(long userId) {
        return repository.findAllByUserId(userId);
    }

    public List<UserDbEntry> getEntriesByUserIdAndServerId(long userId, long serverId) {
        return repository.findAllByUserIdAndServerId(userId, serverId);
    }
}

