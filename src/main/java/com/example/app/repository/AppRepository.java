package com.example.app.repository;

import java.util.List;
import java.util.Optional;

public interface AppRepository<T> {

    boolean create(T obj);
    Optional<List<T>> fetchAll();
    Optional<T> fetchById(Long id);
    boolean update(Long id, T obj);
    boolean delete(Long id);

}
