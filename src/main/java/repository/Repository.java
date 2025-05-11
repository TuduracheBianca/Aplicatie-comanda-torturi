package repository;

import domain.Entity;

import java.util.List;

public interface Repository<T extends Entity>  {
    void add(T entity);
    void update(int id, T item) throws RepositoryException;
    void delete(T entity);
    List<T> getAll();
    T findById(int id);
}
