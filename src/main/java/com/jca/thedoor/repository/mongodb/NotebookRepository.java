package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.Notebook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotebookRepository extends MongoRepository<Notebook, String> {
    boolean existsByNameAndUser(String name, String user);

    List<Notebook> findAllByUser(String user);

    void deleteAllByNameAndUser(String name, String user);
}
