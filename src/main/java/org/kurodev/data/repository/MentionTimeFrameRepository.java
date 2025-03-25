package org.kurodev.data.repository;

import org.kurodev.data.entity.user.UserDbEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MentionTimeFrameRepository extends CrudRepository<UserDbEntry, Integer> {

    List<UserDbEntry> findAllByUserId(long userId);
    List<UserDbEntry> findAllByUserIdAndServerId(long userId, long serverId);
}
