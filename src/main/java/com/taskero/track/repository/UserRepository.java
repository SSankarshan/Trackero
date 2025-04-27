package com.taskero.track.repository;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.taskero.track.model.User;

import java.util.Optional;

public interface UserRepository extends CouchbaseRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
