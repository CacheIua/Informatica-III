package modelo;

import java.time.LocalDateTime;

/**
 * Clásico recordatorio para pacientes, usado por el Planner.
 */
public class Recordatorio {
    public String id;
    public LocalDateTime fecha;
    public String dniPaciente;
    public String mensaje;

    public Recordatorio(String id, LocalDateTime fecha, String dniPaciente, String mensaje){
        this.id = id;
        this.fecha = fecha;
        this.dniPaciente = dniPaciente;
        this.mensaje = mensaje;
    }
}
