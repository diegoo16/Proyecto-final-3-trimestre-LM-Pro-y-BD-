package com.Diego.repository;

import java.util.List;

public interface CrudRepository<T> {
    void create(T entity);
    T findById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(int id);
}