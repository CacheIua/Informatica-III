package agenda;

import modelo.Turno;
import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendaConHistorial extends AgendaMedico {
    boolean reprogramar(String idTurno, LocalDateTime nuevaFecha);
    boolean undo();
    boolean redo();
}
