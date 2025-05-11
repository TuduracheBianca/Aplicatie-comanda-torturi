package domain;

import java.io.Serializable;

public class Tort extends Entity implements Serializable {
    private String tip_tort;
    private static final long serialVersionUID = 8936754565338634033L; // Versiune serial UID pentru compatibilitate



    public String getTip_tort() {
        return tip_tort;
    }

    public void setTip_tort(String tip_tort) {
        this.tip_tort = tip_tort;
    }
    public Tort() {
    }


    public Tort(int id, String tip_tort) {
        super(id);
        this.tip_tort = tip_tort;
    }
    @Override
    public String toString() {
        return "Tort {" + "id=" + getId() + ", tip_tort=" + tip_tort + '}';
    }


    public static Tort fromText(String line) {
        if (line == null || !line.contains(",")) {
            throw new IllegalArgumentException("Line format invalid or null");
        }
        String[] parts = line.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Line does not contain expected data");
        }
        try {
            int id = Integer.parseInt(parts[0]);
            String tipTort = parts[1];
            return new Tort(id, tipTort);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID format invalid", e);
        }
    }

    public static String toText(Tort tort) {
        return String.format("%d,%s", tort.getId(), tort.getTip_tort());
    }
}