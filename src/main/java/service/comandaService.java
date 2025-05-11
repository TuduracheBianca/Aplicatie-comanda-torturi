package service;

import domain.Comanda;
import domain.IdGenerator;
import domain.Tort;
import repository.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class comandaService {
    Repository<Comanda> repository;
    Repository<Tort> tortRepository;

    public comandaService(Repository<Comanda> repository, Repository<Tort> tortRepository) {
        this.repository = repository;
        this.tortRepository = tortRepository;
    }


    public void add(List<Tort> torturi, Date date) {
        IdGenerator.initializeLastIds(repository.getAll(), tortRepository.getAll());
        Comanda comanda = new Comanda(IdGenerator.generateIdcomanda(), torturi, date);
        repository.add(comanda);
    }

    public void update_after_delete(int id) {
        List<Comanda> toUpdate = new ArrayList<>();

        for (Comanda comanda : repository.getAll())
        {
            boolean modified = comanda.getTorturi().removeIf(tort -> tort.getId() == id);
            if(modified)
            {
                toUpdate.add(comanda);
            }
        }
        for (Comanda comanda : toUpdate) {
            repository.delete(comanda);
            repository.add(comanda);
        }
    }

    public void delete(int id) {
        Comanda comanda = repository.findById(id);
        repository.delete(comanda);
    }

    public Comanda findById(int id) {
        return repository.findById(id);
    }

    public List<Comanda> display() {
        return repository.getAll();
    }

    public void updateComanda(int id, Date dataNoua, List<Tort> listaTorturiNou){
        Comanda comandaNoua = new Comanda(id, listaTorturiNou, dataNoua);
        repository.update(id, comandaNoua);
    }

    public List<Map.Entry<String, Long>> rapoarteTorturiPeZi() {
        return repository.getAll().stream()
                .collect(Collectors.groupingBy(comanda -> comanda.getDateSQL().toLocalDate().toString(),
                        Collectors.flatMapping(comanda -> comanda.getTorturi().stream(), Collectors.counting())))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

    // Raport: Numărul de torturi comandate în fiecare lună
    public List<Map.Entry<String, Long>> rapoarteTorturiPeLuna() {
        return repository.getAll().stream()
                .collect(Collectors.groupingBy(comanda -> {
                            // Formatează corect anul și luna în formatul "yyyy-MM"
                            int year = comanda.getDate().getYear() + 1900;
                            int month = comanda.getDateSQL().getMonth() + 1; // Dacă folosești java.sql.Date
                            return String.format("%d-%02d", year, month);
                        },
                        Collectors.flatMapping(comanda -> comanda.getTorturi().stream(), Collectors.counting())))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toList());
    }

//
//    // Raport: Cele mai des comandate torturi
//    public List<Map.Entry<Tort, Long>> torturiCeleMaiComandate() {
//        return repository.getAll().stream()
//                .flatMap(comanda -> comanda.getTorturi().stream())  // FlatMap pentru a obține toate torturile
//                .collect(Collectors.groupingBy(tort -> tort, Collectors.counting()))  // Grupează torturile și le numără
//                .entrySet().stream()
//                .sorted(Map.Entry.<Tort, Long>comparingByValue().reversed())  // Sortează torturile în funcție de numărul de comenzi
//                .collect(Collectors.toList());
//    }

    public List<Map.Entry<String, Long>> torturiCeleMaiComandate() {
        return repository.getAll().stream()
                .flatMap(comanda -> comanda.getTorturi().stream())  // FlatMap pentru a obține toate torturile
                .collect(Collectors.groupingBy(
                        tort -> tort.getTip_tort(),  // Grupăm după tipul tortului
                        Collectors.counting()  // Numărăm aparițiile
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())  // Sortează torturile în funcție de numărul de comenzi
                .collect(Collectors.toList());
    }

}