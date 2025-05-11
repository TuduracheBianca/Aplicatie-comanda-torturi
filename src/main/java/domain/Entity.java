package domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private int id;
    private static final long serialVersionUID = 8936754565338634033L; // Versiune pentru serializare

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
