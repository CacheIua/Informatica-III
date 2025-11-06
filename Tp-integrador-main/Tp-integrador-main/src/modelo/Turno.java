package modelo;

import java.time.LocalDateTime;

/**
 * Clase para representar un turno médico asignado.
 */
public class Turno {
    public String id;
    public String dniPaciente;
    public String matriculaMedico;
    public LocalDateTime fechaHora;
    public int duracionMin;
    public String motivo;

    public Turno(String id, String dniPaciente, String matriculaMedico, LocalDateTime fechaHora, int duracionMin, String motivo) {
        this.id = id;
        this.dniPaciente = dniPaciente;
        this.matriculaMedico = matriculaMedico;
        this.fechaHora = fechaHora;
        this.duracionMin = duracionMin;
        this.motivo = motivo;
    }

    public Turno(String id, LocalDateTime fechaHora, int duracionMin) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.duracionMin = duracionMin;
        this.dniPaciente = null;
        this.matriculaMedico = null;
        this.motivo = "Cirugía programada";
    }

    public LocalDateTime fin() {
        return fechaHora.plusMinutes(duracionMin);
    }

    @Override
    public String toString(){
        return id + " | " + dniPaciente + " | " + matriculaMedico + " | " + fechaHora.toString() + " (" + duracionMin + "m) " + motivo;
    }
}
