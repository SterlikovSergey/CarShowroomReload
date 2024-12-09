package ru.clevertec.repository;

import java.util.List;

public interface IBaseRepository<T, ID> {
    void save(T entity);

    void update(T entity);

    void delete(T entity);

    T findById(ID id);

    List<T> findAll();
}
