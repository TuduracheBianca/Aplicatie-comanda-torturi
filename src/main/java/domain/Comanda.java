package domain;

import service.tortService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Comanda extends Entity implements Serializable {
    private Date date;
    private List<Tort> torturi;
    private static final long serialVersionUID = 1L;


    public List<Tort> getTorturi() {
        return torturi;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    /// Transforms the object into a CSV string
    public String toCsv() {
        String torturiIds = torturi.stream()
                .map(t -> String.valueOf(t.getId()))
                .collect(Collectors.joining(";")); // IDs are separated by ';'
        return getId() + "," + date.getTime() + "," + torturiIds;
    }


    // Recreates the object from a CSV string without a provided list of torturi
    public static Comanda fromCsv(String csv) {
        String[] parts = csv.split(",", 3);
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid CSV format for Comanda");
        }

        int id = Integer.parseInt(parts[0]);
        Date date = new Date(Long.parseLong(parts[1]));

        List<Tort> torturi = new ArrayList<>();
        if (!parts[2].isBlank()) {
            String[] torturiIds = parts[2].split(";");
            for (String idStr : torturiIds) {
                int tortId = Integer.parseInt(idStr);
                Tort tort = tortService.getInstance().findById(tortId);
                if (tort != null) {
                    torturi.add(tort);
                } else {
                    System.out.println("Tort with ID " + tortId + " not found.");
                }
            }
        }

        return new Comanda(id, torturi, date);
    }



//    public static String toText(Comanda comanda) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(comanda.getId()).append(",");
//        sb.append(comanda.getDate()).append(",");
//        for (Tort t : comanda.getTorturi()) {
//            sb.append(t).append(",");
//        }
//        return sb.toString();
//    }
//
//    public static Comanda fromText(String text, List<Tort> all) {
//        String[] lines = text.split(",");
//        int id = Integer.parseInt(lines[0]);
//        Date date= new Date(lines[1]);
//        List<Tort> torturi = new ArrayList<>();
//        if(lines.length > 2) {
//            String[] torturiStr = lines[2].split(",");
//            for (String torturiStr1 : torturiStr) {
//                int torturiId = Integer.parseInt(torturiStr1);
//                for (Tort tort : all)
//                    if(tort.getId() == torturiId)
//                    {
//                        torturi.add(tort);
//                        break;
//                    }
//            }
//        }
//        return new Comanda(id, torturi, date);
//    }


    public Comanda() {

    }
    public java.sql.Date getDateSQL()
    {
        return new java.sql.Date(date.getTime());
    }
    public Comanda(int id, List<Tort> torturi, Date date) {
        super(id);
        this.date = date;
        this.torturi = new ArrayList<>(torturi);
    }

    @Override
    public String toString() {
        return "Comanda{"+ "id=" + getId()  + ", date=" + date + ", torturi=" + torturi + '}';
    }
}