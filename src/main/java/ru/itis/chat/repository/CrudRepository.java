package ru.itis.chat.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, T> {
    List<E> findAll();

    Optional<E> findById(T id);

    void delete(E id);

    Optional<E> save(E entity);
}
