package com.example.semestr.repositories;

import com.example.semestr.entities.User;
import com.example.semestr.exeption.DbException;

import java.util.List;

public interface CRUDRepositoryUser {

    void save(User user) throws DbException;

    List<User> findAll();

    User findById(Long id);

    void update(User user);

    void delete(Long id);

}
