package com.world.service;

import com.world.entity.User;

public interface TempUserCacheService {

    User cacheUser(User user, Long id);
    User getCachedUser(Long playerId);
}
