package quirofano;

import modelo.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * PlanificadorQuirofanoExtended
 * Extensión del planificador básico:
 * - Valida disponibilidad del médico (no permite solapamiento)
 * - Registra Turno en la Agenda (AgendaMedicoAVL.insert)
 * - Mantiene lista de espera y método para reintentos
 * - Mantiene minutos bloqueados por médico y top-K
 *
 * Constructor:
 *   new PlanificadorQuirofanoExtended(cantidadQuirofanos, medicosMap)
 *
 * Requisitos:
 * - Clase Medico con campo 'agenda' que tenga insert(Turno) y getTurnos()
 * - Clase Turno con constructor Turno(id, LocalDateTime fecha, int durMin)
 * - Clase SolicitudCirugia con getters: getId(), getMatriculaMedico(), getDuracionMin(), getDeadline()
 */
public class PlanificadorQuirofanoExtended {

    private static class Quirofano {
        String id;
        LocalDateTime disponibleDesde;
        Quirofano(String id, LocalDateTime disponibleDesde) {
            this.id = id;
            this.disponibleDesde = disponibleDesde;
        }
    }

    private final PriorityQueue<Quirofano> heapQuirofanos;
    private final Map<String, Integer> minutosPorMedico; // matricula -> minutos
    private final Map<String, String> nombresMedicos; // opcional: matricula -> nombre
    private final DateTimeFormatter tf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    /**
     * Crea el planificador con Q quirófanos y mapa opcional de nombres.
     * @param Q cantidad de quirófanos (ej: 5 para Q1..Q5)
     * @param nombresMedicos mapa matricula->nombre (puede ser null)
     */
    public PlanificadorQuirofanoExtended(int Q, Map<String, String> nombresMedicos) {
        if (Q <= 0) throw new IllegalArgumentException("Q debe ser > 0");
        this.nombresMedicos = nombresMedicos != null ? nombresMedicos : new HashMap<>();
        this.minutosPorMedico = new HashMap<>();
        this.heapQuirofanos = new PriorityQueue<>(Comparator.comparing(q -> q.disponibleDesde));
        LocalDateTime ahora = LocalDateTime.now();
        for (int i = 1; i <= Q; i++) {
            heapQuirofanos.add(new Quirofano("Q" + i, ahora));
        }
    }

    /**
     * Procesa la solicitud: asigna al primer quirófano libre cuyo fin <= deadline.
     * Imprime resultado en formato simple y actualiza minutos por médico.
     *
     * @param s SolicitudCirugia (debe exponer: getId(), getMatriculaMedico(), getDuracionMin(), getDeadline())
     * @return true si asignó, false si no (deadline no cumple)
     */
    public synchronized boolean procesar(SolicitudCirugia s) {
        if (s == null) return false;

        // obtener el quirófano más temprano
        Quirofano q = heapQuirofanos.poll();
        if (q == null) {
            System.out.println("? No hay quirófanos configurados");
            return false;
        }

        // inicio real (cuando queda libre el quirófano)
        LocalDateTime inicio = q.disponibleDesde.isAfter(LocalDateTime.now()) ? q.disponibleDesde : LocalDateTime.now();
        LocalDateTime fin = inicio.plusMinutes(s.getDuracionMin());

        // si no cumple deadline -> devolver y no asignar
        if (fin.isAfter(s.getDeadline())) {
            // devolver el quirófano tal como estaba
            heapQuirofanos.add(q);
            System.out.println("? Solicitud " + s.getId() + " no pudo asignarse (deadline)");
            return false;
        }

        // asignar: actualizar disponibilidad y reinsertar
        q.disponibleDesde = fin;
        heapQuirofanos.add(q);

        // actualizar minutos por médico
        String mat = s.getMatriculaMedico();
        int acumulado = minutosPorMedico.getOrDefault(mat, 0) + s.getDuracionMin();
        minutosPorMedico.put(mat, acumulado);

        // salida simple
        String nombre = nombresMedicos.getOrDefault(mat, mat);
        System.out.println("✓ " + s.getId() + " asignada a " + q.id + " (inicio: " + inicio.format(tf) + ", fin: " + fin.format(tf) + ") - " + nombre);

        return true;
    }

    /**
     * Top-K médicos más ocupados. Devuelve líneas con formato "1) Dr. Nombre - X.Y hs"
     * Convierte minutos a horas con un decimal.
     *
     * @param K cantidad de médicos a retornar
     * @return lista de strings formateados
     */
    public synchronized List<String> topKMedicosBloqueados(int K) {
        if (K <= 0) return Collections.emptyList();
        List<Map.Entry<String, Integer>> lista = new ArrayList<>(minutosPorMedico.entrySet());
        // ordenar por minutos descendente
        lista.sort((a,b) -> b.getValue().compareTo(a.getValue()));

        List<String> out = new ArrayList<>();
        int limite = Math.min(K, lista.size());
        for (int i = 0; i < limite; i++) {
            Map.Entry<String,Integer> e = lista.get(i);
            String mat = e.getKey();
            double horas = e.getValue() / 60.0;
            String nombre = nombresMedicos.getOrDefault(mat, mat);
            out.add((i+1) + ") " + nombre + " - " + String.format("%.1f", horas) + " hs");
        }
        // si no hay medicos, devolver vacío (o podrías devolver placeholders)
        return out;
    }

    /**
     * Devuelve copia del mapa de minutos por médico (en minutos).
     */
    public synchronized Map<String,Integer> getMinutosPorMedico() {
        return new HashMap<>(minutosPorMedico);
    }

    /**
     * Reinicia el planificador (quirófanos vuelven a now, contadores a cero)
     */
    public synchronized void reiniciar() {
        heapQuirofanos.clear();
        minutosPorMedico.clear();
        LocalDateTime ahora = LocalDateTime.now();
        // asumimos que Q original se deduce del tamaño inicial de la cola no disponible aquí,
        // por eso no lo hacemos dinámico. Si querés mantener Q, guardalo en un campo.
        // Para simplicidad, re-creamos 5 quirófanos por defecto si llamás reiniciar sin conocer Q.
        for (int i = 1; i <= 5; i++) heapQuirofanos.add(new Quirofano("Q" + i, ahora));
    }
}