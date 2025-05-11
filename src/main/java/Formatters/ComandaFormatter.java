//package Formatters;
//
//import domain.Comanda;
//import domain.Tort;
//import repository.TextFileRepository;
//import service.tortService;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.regex.PatternSyntaxException;
//import java.util.stream.Collectors;
//
//public class ComandaFormatter  {
//
//    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//
////    @Override
////    public String serialize(Comanda entity) {
////        StringBuilder serialized = new StringBuilder();
////        serialized.append(entity.getId()).append(";")
////                .append(dateFormat.format(entity.getDate())).append(";");
////
////        // Serializare lista de torturi
////        List<String> torturiSerialized = new ArrayList<>();
////        for (var tort : entity.getTorturi()) {
////            torturiSerialized.add(tort.toString()); // presupunem că `toString` din Tort este adecvat
////        }
////        serialized.append(String.join(",", torturiSerialized));
////
////        return serialized.toString();
////    }
////    @Override
////    public Comanda deserialize(String data) throws PatternSyntaxException, ParseException, ParseException {
////        String[] split = data.split(";");
////
////        if (split.length < 3) {
////            throw new PatternSyntaxException("Datele pentru Comanda sunt incomplete.", data, -1);
////        }
////
////        int id = Integer.parseInt(split[0]);
////        Date date = dateFormat.parse(split[1]);
////
////        // Deserializare lista de torturi
////        List<Tort> torturi = new ArrayList<>();
////        if (!split[2].isEmpty()) {
////            String[] torturiData = split[2].split(",");
////            for (String tortData : torturiData) {
////                // presupunem că există o metodă statică `Tort.fromString(String)`
////                torturi.add(Tort.fromText(tortData));
////            }
////        }
////
////        return new Comanda(id, torturi, date);
////    }
//
//    public String toCsv(Comanda comanda) {
//        String torturiIds = comanda.getTorturi().stream()
//                .map(t -> String.valueOf(t.getId()))
//                .collect(Collectors.joining(";")); // IDs are separated by ';'
//        return comanda.getId() + "," + comanda.getDate() + "," + torturiIds;
//    }
//
//
//    // Recreates the object from a CSV string without a provided list of torturi
//    public static List<Tort> fromCsv(String csv) {
//        String[] parts = csv.split(",", 3);
//        if (parts.length < 3) {
//            throw new IllegalArgumentException("Invalid CSV format for Comanda");
//        }
//        List<Tort> torturi = new ArrayList<>();
//        if (!parts[2].isBlank()) {
//            String[] tortDetails = parts[2].split("\\|");
//            for (String tortText : tortDetails) {
//                try {
//                    Tort tort = Tort.fromText(tortText);
//                    torturi.add(tort);
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Invalid Tort data: " + tortText + " - " + e.getMessage());
//                }
//            }
//        }
////        String[] parts = csv.split(",", 3);
////        if (parts.length < 3) {
////            throw new IllegalArgumentException("Invalid CSV format for Comanda");
////        }
////
////        int id = Integer.parseInt(parts[0]);
////        Date date = new Date(Long.parseLong(parts[1]));
////
////        List<Tort> torturi = new ArrayList<>();
////        if (!parts[2].isBlank()) {
////            String[] torturiIds = parts[2].split(";");
////            for (String idStr : torturiIds) {
////                int tortId = Integer.parseInt(idStr);
////                Tort tort = tortService.getInstance().findById(tortId);
////                if (tort != null) {
////                    torturi.add(tort);
////                } else {
////                    System.out.println("Tort with ID " + tortId + " not found.");
////                }
////            }
////        }
//
//        return torturi;
//    }
//}
package Formatters;

import domain.Comanda;
import domain.Tort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ComandaFormatter {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public String toCsv(Comanda comanda) {
        String torturiIds = comanda.getTorturi().stream()
                .map(t -> String.valueOf(t.getId()))
                .collect(Collectors.joining(";"));
        return comanda.getId() + "," + dateFormat.format(comanda.getDate()) + "," + torturiIds;
    }

    public Comanda fromCsv(String csv) throws ParseException {
        String[] parts = csv.split(",", 3);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid CSV format for Comanda");
        }

        int id = Integer.parseInt(parts[0]);
        Date date = dateFormat.parse(parts[1]);

        List<Tort> torturi = new ArrayList<>();
        if (!parts[2].isBlank()) {
            String[] tortDetails = parts[2].split(";");
            for (String tortText : tortDetails) {
                Tort tort = Tort.fromText(tortText); // presupunem că `Tort.fromText` funcționează
                torturi.add(tort);
            }
        }

        return new Comanda(id, torturi, date);
    }
}
