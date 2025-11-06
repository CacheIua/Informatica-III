package planner;

import modelo.Recordatorio;
import java.time.LocalDateTime;

public interface Planner {
    void programar(Recordatorio r);
    Recordatorio proximo();
    void reprogramar(String id, LocalDateTime nuevaFecha);
    int size();
}
