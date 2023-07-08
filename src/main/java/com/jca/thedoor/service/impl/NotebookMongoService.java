package com.jca.thedoor.service.impl;

import com.jca.thedoor.entity.mongodb.Notebook;
import com.jca.thedoor.exception.ServerException;
import com.jca.thedoor.repository.mongodb.NotebookRepository;
import com.jca.thedoor.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotebookMongoService implements NotebookService {
    @Autowired
    private NotebookRepository notebookRepository;


    @Override
    public ResponseEntity<Notebook> createNotebook(Notebook notebook) {
        try {
            Notebook created = notebookRepository.save(notebook);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<Notebook>> findAllNotebooksByUser(String user) {
        try {
            List<Notebook> notebooks = notebookRepository.findAllByUser(user);
            notebooks.forEach(notebook -> notebook.setId(notebook.get_id().toString()));
            return ResponseEntity.ok(notebooks);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }
}
