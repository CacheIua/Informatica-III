package modelo;

import java.time.LocalDateTime;

/**
 * Representa una solicitud de cirugía que debe procesarse con una deadline.
 */
public class SolicitudCirugia {
    public String id;
    public String matricula;
    public int durMin;
    public LocalDateTime deadline;

    public SolicitudCirugia(String id, String matricula, int durMin, LocalDateTime deadline){
        this.id = id;
        this.matricula = matricula;
        this.durMin = durMin;
        this.deadline = deadline;
    }
    public int getDuracionMin() {
        return durMin;
    }
    public String getId() {
        return id;
    }
    public LocalDateTime getDeadline() {
        return deadline;
    }

     public String getMatriculaMedico() {
        return matricula;
    }
}
