package com.taskero.track.repository;

import com.taskero.track.model.Project;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends CouchbaseRepository<Project, String> {
    Optional<Project> findByKey(String key);
    boolean existsByKey(String key);
    boolean existsByName(String name);
}
