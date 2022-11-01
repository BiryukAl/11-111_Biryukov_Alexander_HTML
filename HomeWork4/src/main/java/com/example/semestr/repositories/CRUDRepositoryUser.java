package com.example.semestr.repositories;

import com.example.semestr.entities.User;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;

public interface CRUDRepositoryUser {

    void save(User user);

    List<User> findAll();

    User findById(Long id);

    void update(User user);

    void delete(Long id);

}
