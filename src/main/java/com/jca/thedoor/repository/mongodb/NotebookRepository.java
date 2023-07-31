package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.Notebook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotebookRepository extends MongoRepository<Notebook, String> {
    boolean existsByNameAndUser(String name, String user);

    List<Notebook> findAllByUser(String user);

    Optional<Notebook> findByUserAndName(String user, String name);

    void deleteAllByNameAndUser(String name, String user);
}
