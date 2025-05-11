//package domain;
//
//import domain.Comanda;
//import domain.Tort;
//import service.comandaService;
//import repository.Repository;
//import repository.MemoryRepository;
//import service.tortService;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
//public class ComandaGenerator {
//    private final comandaService comandaService;
//    private final tortService tortService;
//    private final Random random = new Random();
//
//    // Constructorul primește service-ul pentru comenzi și repository-ul pentru torturi
//    public ComandaGenerator(comandaService comandaService, tortService tortService) {
//        this.comandaService = comandaService;
//        this.tortService = tortService;
//    }
//
//    // Metoda care generează comenzile
//    public void genereazaComenzi(int numarComenzi) {
//        for (int i = 0; i < numarComenzi; i++) {
//            List<Tort> torturi = genereazaTorturi(); // Generăm torturi aleatoare
//            Date dataComanda = genereazaDataAleatoare(); // Generăm data aleatoare
//
//            // Adăugăm comanda în service
//            comandaService.add(torturi, dataComanda);
//
//            System.out.println("Comanda #" + (i + 1) + " adăugată: " + torturi + " | Data: " + dataComanda);
//        }
//    }
//
//    private List<Tort> genereazaTorturi() {
//        List<Tort> torturi = new ArrayList<>();
//        int numarTorturi = random.nextInt(5) + 1; // Generăm între 1 și 5 torturi
//
//        // Verificăm dacă repository-ul de torturi conține elemente
//        int torturiDisponibile = tortService.display().size();
//        System.out.println(torturiDisponibile);
//        if (torturiDisponibile == 0) {
//            System.out.println("Nu sunt torturi disponibile în repository.");
//            return torturi; // Returnează o listă goală dacă nu sunt torturi
//        }
//
//        for (int i = 0; i < numarTorturi; i++) {
//            int indexTort = random.nextInt(torturiDisponibile); // Alegem un tort aleatoriu din repository
//            Tort tort = tortService.display().get(indexTort);
//            torturi.add(tort);
//        }
//
//        return torturi;
//    }
//
//
//    // Metodă pentru a genera o dată aleatoare
//    private Date genereazaDataAleatoare() {
//        long currentTime = System.currentTimeMillis();
//        long randomOffset = (long) (random.nextInt(60) * 24 * 60 * 60 * 1000); // Zile aleatorii între 0 și 60
//        return new Date(currentTime - randomOffset); // Calculăm data aleatorie
//    }
//
//    // Metoda main pentru a rula generatorul
//    public static void main(String[] args) {
//        // Creăm repository-ul pentru torturi (poți înlocui cu cel real)
//        tortService tortService = n
//        comandaService comandaService = new comandaService(new MemoryRepository<>(), new MemoryRepository<>()); // Înlocuiește cu service-ul real
//
//        // Creăm generatorul de comenzi
//        ComandaGenerator generator = new ComandaGenerator(comandaService, tortService);
//
//        // Generăm 100 de comenzi
//        generator.genereazaComenzi(100);
//        System.out.println("Generarea comenzilor s-a încheiat cu succes!");
//    }
//}
package domain;

import domain.Comanda;
import domain.Tort;
import repository.SQLComandaRepository;
import repository.SQLTortRepository;
import service.comandaService;
import service.tortService;
import service.comandaService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ComandaGenerator {
    private final comandaService comandaService;
    private final tortService tortService;
    private final Random random = new Random();

    // Constructorul primește service-urile pentru comenzi și torturi
    public ComandaGenerator(comandaService comandaService, tortService tortService) {
        this.comandaService = comandaService;
        this.tortService = tortService;
    }

    // Metoda care generează comenzile
    public void genereazaComenzi(int numarComenzi) {
        for (int i = 0; i < numarComenzi; i++) {
            List<Tort> torturi = genereazaTorturi(); // Generăm torturi aleatoare
            Date dataComanda = genereazaDataAleatoare(); // Generăm data aleatoare

            // Adăugăm comanda în service
            Comanda comanda = new Comanda(i + 1, torturi, dataComanda);
            comandaService.add(torturi, dataComanda);

            System.out.println("Comanda #" + (i + 1) + " adăugată: " + torturi + " | Data: " + dataComanda);
        }
    }

    private List<Tort> genereazaTorturi() {
        List<Tort> torturi = new ArrayList<>();
        int numarTorturi = random.nextInt(5) + 1; // Generăm între 1 și 5 torturi

        // Verificăm dacă există torturi în baza de date
        List<Tort> torturiDisponibile = tortService.display();
        int torturiDisponibileCount = torturiDisponibile.size();
        System.out.println(torturiDisponibileCount);
        if (torturiDisponibileCount == 0) {
            System.out.println("Nu sunt torturi disponibile în baza de date.");
            return torturi; // Returnează o listă goală dacă nu sunt torturi
        }

        // Alegem torturi aleatorii din lista de torturi disponibile
        for (int i = 0; i < numarTorturi; i++) {
            int indexTort = random.nextInt(torturiDisponibileCount); // Alegem un tort aleatoriu
            Tort tort = torturiDisponibile.get(indexTort);
            torturi.add(tort);
        }

        return torturi;
    }

    // Metodă pentru a genera o dată aleatoare
    private Date genereazaDataAleatoare() {
        long currentTime = System.currentTimeMillis();
        long randomOffset = (long) (random.nextInt(60) * 24 * 60 * 60 * 1000); // Zile aleatorii între 0 și 60
        return new Date(currentTime - randomOffset); // Calculăm data aleatorie
    }

    // Metoda main pentru a rula generatorul
    public static void main(String[] args) {
        // Creăm instanțele serviciilor
        SQLTortRepository tortRepository = new SQLTortRepository();
        SQLComandaRepository comandaRepository = new SQLComandaRepository();

        comandaService comandaService = new comandaService(comandaRepository, tortRepository); // Instanțierea corectă a serviciilor
        tortService tortService = new tortService(tortRepository,comandaRepository); // Instanțierea corectă a serviciilor

        // Creăm generatorul de comenzi
        ComandaGenerator generator = new ComandaGenerator(comandaService, tortService);

        // Generăm 100 de comenzi
        generator.genereazaComenzi(100);
        System.out.println("Generarea comenzilor s-a încheiat cu succes!");
    }
}
