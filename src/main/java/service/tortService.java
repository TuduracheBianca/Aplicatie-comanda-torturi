package service;

import domain.Comanda;
import domain.IdGenerator;
import domain.Tort;
import repository.Repository;
import repository.RepositoryException;

import java.util.List;
import java.util.ServiceConfigurationError;

public class tortService {
    Repository<Tort> repository;
    Repository<Comanda> comandaRepository;
    private static tortService instance;

    public tortService(Repository<Tort> repository, Repository<Comanda> comandaRepository) {
        this.repository = repository;
        this.comandaRepository = comandaRepository;
    }

    public static synchronized tortService getInstance() {
        if (instance == null) {
            throw new IllegalStateException("tortService has not been initialized.");
        }
        return instance;
    }

    public static synchronized void initialize(Repository<Tort> repository, Repository<Comanda> comandaRepository) {
        if (instance == null) {
            instance = new tortService(repository, comandaRepository);
        }
    }

//    public static synchronized tortService getInstance(Repository<Tort> repository, Repository<Comanda> comandaRepository) {
//        if (instance == null) {
//            instance = new tortService(repository, comandaRepository);
//        }
//        return instance;
//    }

//    public static synchronized tortService getInstance() {
//        if (instance == null) {
//            instance = new tortService(getInstance().repository, getInstance().comandaRepository);
//        }
//        return instance;
//    }

    public void add(String type) {
        IdGenerator.initializeLastIds(comandaRepository.getAll(), repository.getAll());
        Tort tort = new Tort(IdGenerator.generateIdtort(), type);
        repository.add(tort);
    }

    public Tort findById(int id) {
        return repository.findById(id);
    }

    public List<Tort> display() {
        return repository.getAll();
    }

    public void delete(int id) {
        Tort tort = repository.findById(id);
        repository.delete(tort);

    }

    public void updateTort (int id, String tipNou) throws RepositoryException {
        if(tipNou == null)
            throw new ServiceConfigurationError(("tipul nu poate fi null"));
        Tort tortNou = new Tort(id, tipNou);
        repository.update(id, tortNou);
    }
}