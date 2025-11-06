package modelo;

/**
 * Clase que representa a un paciente del sistema.
 */
public class Paciente {
    public String dni;
    public String nombre;

    public Paciente(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return dni + " - " + nombre;
    }
}
