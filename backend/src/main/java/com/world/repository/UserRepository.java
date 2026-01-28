package com.world.repository;

import com.world.entity.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    Optional<User> getUserByUsername(String username);
}
