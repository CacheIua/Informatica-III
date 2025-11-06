package modelo;

import java.time.LocalDateTime;

public class Quirofano implements Comparable<Quirofano> {
    public String id;                  // Q1, Q2, Q3...
    public LocalDateTime disponible;   // cuándo termina su última cirugía

    public Quirofano(String id, LocalDateTime disponible) {
        this.id = id;
        this.disponible = disponible;
    }

    @Override
    public int compareTo(Quirofano o) {
        return this.disponible.compareTo(o.disponible);
    }

    @Override
    public String toString() {
        return id + " (libre: " + disponible + ")";
    }
}
