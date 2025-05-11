package domain;

import java.util.List;

public class IdGenerator {
    //    private static int lastIdcomanda = 105;
//    private static int lastIdtort = 105;
//
//    public static int generateIdcomanda() {
//        return ++lastIdcomanda;
//    }
//
//    public static int generateIdtort() {
//        return ++lastIdtort;
//    }
    private static int lastIdcomanda;
    private static int lastIdtort;

    public static void initializeLastIds(List<Comanda> comenzi, List<Tort> torturi) {
        // Căutăm ID-ul maxim în comenzile existente
        lastIdcomanda = comenzi.stream()
                .mapToInt(Comanda::getId)
                .max()
                .orElse(0);  // Dacă lista e goală, plecăm de la 0

        // Căutăm ID-ul maxim în torturile existente
        lastIdtort = torturi.stream()
                .mapToInt(Tort::getId)
                .max()
                .orElse(0);  // Dacă lista e goală, plecăm de la 0
    }

    public static int generateIdcomanda() {
        return ++lastIdcomanda;
    }

    public static int generateIdtort() {
        return ++lastIdtort;
    }

}
