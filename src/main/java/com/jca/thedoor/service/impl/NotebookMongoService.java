package com.jca.thedoor.service.impl;

import com.jca.thedoor.entity.mongodb.Notebook;
import com.jca.thedoor.exception.NotFoundException;
import com.jca.thedoor.exception.ServerException;
import com.jca.thedoor.repository.mongodb.NotebookRepository;
import com.jca.thedoor.service.NotebookService;
import org.mockito.internal.matchers.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Override
    public ResponseEntity<Notebook> findNotebookByUserAndName(String user, String name) {
        try {
            Optional<Notebook> result = notebookRepository.findByUserAndName(user, name);
            Notebook notebook = result.get();
            notebook.assignStringId();
            return ResponseEntity.ok(notebook);
        } catch (NoSuchElementException e) {
            throw new NotFoundException("Notebook with name " + name + " doesn't exist");
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    @Override
    public void deleteNotebooksByNameAndUser(String[] names, String user) {
        for (String name : names) {
            notebookRepository.deleteAllByNameAndUser(name, user);
        }
    }
}
