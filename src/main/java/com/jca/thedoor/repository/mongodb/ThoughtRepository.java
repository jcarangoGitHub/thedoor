package com.jca.thedoor.repository.mongodb;

import com.jca.thedoor.entity.mongodb.Thought;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ThoughtRepository extends MongoRepository<Thought, String> {
    List<Thought> findByUserAndNotebook(String user, String notebook);
    void deleteAllByUserAndNotebookAndTitle(String user, String notebook, String title);

}
