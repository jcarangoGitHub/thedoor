package com.jca.thedoor.service;

import com.jca.thedoor.entity.mongodb.Notebook;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotebookService {
    ResponseEntity<Notebook> createNotebook(Notebook notebook);

    ResponseEntity<List<Notebook>> findAllNotebooksByUser(String idUser);

    ResponseEntity<Notebook> findNotebookByUserAndName(String user, String name);

    void deleteNotebooksByNameAndUser(String[] names, String user);
}
