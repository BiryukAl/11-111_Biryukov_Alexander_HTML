package com.example.semestr.repositories;

import com.example.semestr.entities.FileDC;
import com.example.semestr.exeption.DbException;

import java.util.List;

public interface CRUDRepositoryFile {

    void save(FileDC file) throws DbException;

    List<FileDC> findAll();

    FileDC findById(Long id);

    void update(FileDC file);

    void delete(Long id);
}
