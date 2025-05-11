package repository;

import domain.Entity;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository<T extends Entity> implements Repository<T> {
    protected List<T> entities = new ArrayList<>();


    //    private void initializeRepository() {
//        // Inițializăm câteva torturi pentru comenzile de exemplu
//        List<Tort> torturi1 = List.of(new Tort(101, "Ciocolată"), new Tort(102, "Vanilie"));
//        List<Tort> torturi2 = List.of(new Tort(103, "Fructe de pădure"), new Tort(104, "Mango"));
//        List<Tort> torturi3 = List.of(new Tort(105, "Caramel"));
//        List<Tort> torturi4 = List.of(new Tort(101, "Ciocolată"), new Tort(104, "Mango"));
//        List<Tort> torturi5 = List.of(new Tort(102, "Vanilie"), new Tort(105, "Caramel"));
//
//        entities.add((T) new Comanda(101, torturi1, new Date()));
//        entities.add((T) new Comanda(102, torturi2, new Date()));
//        entities.add((T) new Comanda(103, torturi3, new Date()));
//        entities.add((T) new Comanda(104, torturi4, new Date()));
//        entities.add((T) new Comanda(105, torturi5, new Date()));
//    }
    @Override
    public void add(T entity) {
        if(findById(entity.getId())==null)
        {
            entities.add(entity);
        }
        else {
            throw new IllegalArgumentException("Entity already exists");
        }
    }

    @Override
    public void update(int id, T item) {
        int ok = 1;
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == id) {
                entities.set(i, item);
                ok = 0;
            }
        }
        if (ok == 1) {
            throw new RepositoryException("Entity with id " + item.getId()+ " not found!");
        }
    }

//    @Override
//    public void update(T entity) {
//
//    }

    @Override
    public void delete(T entity) {
        boolean removed = entities.removeIf(existingEntity -> existingEntity.equals(entity));
        if(!removed)
            throw new RepositoryException("Entity with id" + entity.getId() + " does not exist");

    }

    @Override
    public List<T> getAll() {
        return entities;
    }

    @Override
    public T findById(int id) throws RepositoryException {
        for (T entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }


//    @Override
//    public Iterator<T> iterator() {
//        return null;
//    }
}