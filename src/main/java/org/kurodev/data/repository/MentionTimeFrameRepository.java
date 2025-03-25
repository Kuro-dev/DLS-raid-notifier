package org.kurodev.data.repository;

import org.kurodev.data.entity.UserDbEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MentionTimeFrameRepository extends CrudRepository<UserDbEntry, Long> {

    List<UserDbEntry> findAllByUserId(long userId);
    List<UserDbEntry> findAllByUserIdAndServerId(long userId, long serverId);
}
