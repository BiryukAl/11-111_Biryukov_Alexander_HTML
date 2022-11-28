package com.example.semestr.repositories;

import com.example.semestr.entities.FileAccess;
import com.example.semestr.exceptions.DbException;

import java.util.List;

public interface CRUDRepositoryFileAccess {

    void save(Long fileId, Long userId) throws DbException;

    void save(FileAccess fileAccess) throws DbException;

    List<FileAccess> findAll();

    void update(FileAccess fileAccess);

    void delete(FileAccess fileAccess);
}
