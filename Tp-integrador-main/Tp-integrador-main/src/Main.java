import importer.CSVImporter;
import importer.HospitalData;
import modelo.Medico;
import modelo.Paciente;
import modelo.Turno;
import modelo.Recordatorio;
import modelo.SolicitudCirugia;
import agenda.AgendaMedicoAVL;
import agenda.AgendaConHistorialImpl;
import planner.MinHeapRecordatorios;
import quirofano.MinHeapQuirofanos;
import quirofano.PlanificadorQuirofanoExtended;
import sala.SalaEspera;
import utilidad.Consolidator;
import utilidad.Sorting;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Menú interactivo para el sistema de gestión de turnos.
 * Copiar como src/InteractiveMain.java y compilar junto al resto.
 */
public class Main {

    static Scanner sc = new Scanner(System.in);
    static HospitalData data;
    static MinHeapRecordatorios planner = new MinHeapRecordatorios();
    static SalaEspera sala = new SalaEspera(5); // capacidad por defecto
    static MinHeapQuirofanos planQuirofano = new MinHeapQuirofanos();

    public static void main(String[] args) {
        try {
            System.out.println("Cargando datos desde CSV (csv/pacientes.csv, csv/medicos.csv, csv/turnos.csv)...");
            data = CSVImporter.importAll("csv/pacientes.csv", "csv/medicos.csv", "csv/turnos.csv");
            System.out.println("Carga finalizada.");
        } catch (Exception e) {
            System.err.println("Error cargando CSV: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        boolean running = true;
        while (running) {
            printMenu();
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": verAgendaMedico(); break;
                case "2": buscarProximoDisponible(); break;
                case "3": simularSalaEspera(); break;
                case "4": programarRecordatorios(); break;
                case "5": consultarIndicePacientes(); break;
                case "6": consolidadorAgendas(); break;
                case "7": reportesOrdenamiento(); break;
                case "8": auditoriaUndoRedo(); break;
                case "9": quirofanoMenu(); break;
                case "0": running = false; break;
                default: System.out.println("Opción inválida. Intentá de nuevo."); break;
            }
        }

        System.out.println("Saliendo. ¡Hasta luego!");
    }

    static void printMenu(){
        System.out.println("\n-------------------------------------------");
        System.out.println("MENU PRINCIPAL");
        System.out.println("-------------------------------------------");
        System.out.println("1) Ver agenda de un médico");
        System.out.println("2) Buscar próximo turno disponible");
        System.out.println("3) Simular sala de espera");
        System.out.println("4) Programar recordatorios");
        System.out.println("5) Consultar índice de pacientes (Hash)");
        System.out.println("6) Consolidador de agendas");
        System.out.println("7) Reportes de ordenamiento");
        System.out.println("8) Auditoría Undo/Redo");
        System.out.println("9) Planificador de quirófano");
        System.out.println("0) Salir");
        System.out.print("Seleccione una opción: ");
    }

    // 1) Mostrar agenda de un médico
    static void verAgendaMedico(){
        System.out.print("Ingrese matrícula del médico: ");
        String mat = sc.nextLine().trim();
        Medico m = data.medicos.get(mat);
        if (m == null) { System.out.println("Médico no encontrado."); return; }
        System.out.println("AGENDA DEL " + m.nombre + " - " + m.especialidad);
        m.agenda.inorderPrint();
    }

    // 2) Buscar próximo turno disponible (usa primerHueco)
    static void buscarProximoDisponible(){
        System.out.print("Ingrese matrícula del médico: ");
        String mat = sc.nextLine().trim();
        Medico m = data.medicos.get(mat);
        if (m == null) { System.out.println("Médico no encontrado."); return; }

        System.out.print("Fecha/hora desde (YYYY-MM-DDTHH:mm) o ENTER para ahora: ");
        String f = sc.nextLine().trim();
        LocalDateTime t0 = f.isEmpty() ? LocalDateTime.now() : LocalDateTime.parse(f, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        System.out.print("Duración en minutos: ");
        int dur = Integer.parseInt(sc.nextLine().trim());

        Optional<LocalDateTime> opt = m.agenda.primerHueco(t0, dur);
        if (opt.isPresent()) System.out.println("Primer hueco: " + opt.get());
        else System.out.println("No se encontró hueco en el periodo buscado.");
    }

    // 3) Simular sala de espera
    static void simularSalaEspera(){
        System.out.println("Simulación Sala de Espera (capacidad = 5). Comandos: 'llega DNI', 'atiende', 'peek', 'salir'");
        while(true){
            System.out.print("cmd> ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("salir")) break;
            if (line.startsWith("llega")) {
                String[] sp = line.split("\\s+",2);
                if (sp.length<2){ System.out.println("Uso: llega DNI"); continue; }
                String dni = sp[1].trim();
                Paciente p = data.pacientes.get(dni);
                if (p==null) { System.out.println("Paciente no registrado: " + dni); continue; }
                sala.llega(p);
                System.out.println("Llegó: " + p);
            } else if (line.equalsIgnoreCase("atiende")){
                Paciente at = sala.atiende();
                System.out.println(at==null ? "Sala vacía" : "Atendido: " + at);
            } else if (line.equalsIgnoreCase("peek")){
                Paciente pk = sala.peek();
                System.out.println(pk==null ? "Sala vacía" : "Próximo: " + pk);
            } else System.out.println("Comando desconocido.");
        }
    }

    // 4) Programar recordatorios
    static void programarRecordatorios(){
        System.out.println("Programar recordatorio: formato 'id,dni,YYYY-MM-DDTHH:mm,MENSAJE' o 'salir'");
        while(true){
            System.out.print("input> ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("salir")) break;
            String[] parts = line.split(",",4);
            if (parts.length < 4) { System.out.println("Formato inválido."); continue; }
            String id = parts[0].trim();
            String dni = parts[1].trim();
            String fechaS = parts[2].trim();
            String mensaje = parts[3].trim();
            if (!data.pacientes.containsKey(dni)){ System.out.println("DNI no registrado."); continue; }
            LocalDateTime fecha;
            try { fecha = LocalDateTime.parse(fechaS); }
            catch(Exception e){ System.out.println("Fecha inválida."); continue; }
            planner.programar(new Recordatorio(id, fecha, dni, mensaje));
            System.out.println("Recordatorio programado.");
        }
    }

    // 5) Consultar índice de pacientes
    static void consultarIndicePacientes(){
        System.out.println("Índice de pacientes (size = " + data.pacientes.size() + ")");
        for (String k : data.pacientes.keys()) {
            System.out.println(" - " + k + " -> " + data.pacientes.get(k));
        }
    }

    // 6) Consolidador de agendas (merge entre 2 listas de turnos)
    static void consolidadorAgendas(){
        System.out.println("Consolidar agendas de dos médicos. Ingrese matrícula A y B:");
        System.out.print("Matrícula A: "); String a = sc.nextLine().trim();
        System.out.print("Matrícula B: "); String b = sc.nextLine().trim();
        Medico ma = data.medicos.get(a), mb = data.medicos.get(b);
        if (ma==null || mb==null){ System.out.println("Médico no encontrado."); return; }
        // Obtener listas en-orden recolectando
        List<Turno> A = collectInOrder(ma.agenda);
        List<Turno> B = collectInOrder(mb.agenda);
        List<Turno> merged = Consolidator.merge(A,B);
        System.out.println("Merge result ("+merged.size()+" turnos):");
        merged.forEach(System.out::println);
    }

    // ayuda: recolectar turno en in-order desde AgendaMedicoAVL (intentamos reflection safe)
    static List<Turno> collectInOrder(agenda.AgendaMedico ag){
        List<Turno> res = new ArrayList<>();
        if (ag instanceof AgendaMedicoAVL){
            AgendaMedicoAVL avl = (AgendaMedicoAVL) ag;
            // usamos impresión por System.out y no reflection; en su lugar re-travesamos con siguiente
            LocalDateTime cursor = LocalDateTime.now().minusYears(1);
            for (int i=0;i<100000;i++){
                Optional<Turno> o = avl.siguiente(cursor);
                if (!o.isPresent()) break;
                Turno t = o.get();
                res.add(t);
                cursor = t.fechaHora.plusSeconds(1);
            }
        }
        return res;
    }

    // 7) Reportes de ordenamiento
    static void reportesOrdenamiento(){
        System.out.println("Reportes: 1) Por hora (Insertion), 2) Por duración (Shell), 3) Por apellido (QuickSort)");
        System.out.print("Elija opción: ");
        String op = sc.nextLine().trim();
        // Armamos una lista con todos los turnos del sistema (simplemente recolectamos desde cada médico)
        List<Turno> all = new ArrayList<>();
        for (Object obj : data.medicos.values()){
            Medico m = (Medico) obj;
            all.addAll(collectInOrder(m.agenda));
        }
        if (all.isEmpty()){ System.out.println("No hay turnos para ordenar."); return; }
        switch(op){
            case "1":
                Sorting.insertionSortByFecha(all);
                System.out.println("Reportes por hora:");
                all.forEach(System.out::println);
                break;
            case "2":
                Sorting.shellSortByDuracion(all);
                System.out.println("Reportes por duración:");
                all.forEach(System.out::println);
                break;
            case "3":
                Sorting.quicksortByApellido(all, 0, all.size()-1);
                System.out.println("Reportes por apellido:");
                all.forEach(System.out::println);
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }

    // 8) Auditoría Undo/Redo (envolvemos la agenda seleccionada)
    static void auditoriaUndoRedo(){
        System.out.print("Matrícula del médico para historial: ");
        String mat = sc.nextLine().trim();
        Medico m = data.medicos.get(mat);
        if (m==null){ System.out.println("Médico no encontrado."); return; }
        AgendaConHistorialImpl hist = new AgendaConHistorialImpl(m.agenda);
        System.out.println("Comandos: agendar(id,dni,YYYY-MM-DDTHH:mm,dur,motivo), cancelar(id), reprogramar(id,YYYY-MM-DDTHH:mm), undo, redo, salir");
        while(true){
            System.out.print("hist> ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("salir")) break;
            if (line.startsWith("agendar")){
                try {
                    String inside = line.substring(line.indexOf("(")+1, line.lastIndexOf(")"));
                    String[] p = inside.split(",",5);
                    String id = p[0].trim(), dni = p[1].trim();
                    LocalDateTime fecha = LocalDateTime.parse(p[2].trim());
                    int dur = Integer.parseInt(p[3].trim());
                    String motivo = p[4].trim();
                    Turno t = new Turno(id, dni, m.matricula, fecha, dur, motivo);
                    boolean ok = hist.agendar(t);
                    System.out.println(ok ? "Agendado." : "No se pudo agendar (posible conflicto).");
                } catch(Exception e){ System.out.println("Error en formato."); }
            } else if (line.startsWith("cancelar")){
                String id = betweenPar(line);
                boolean ok = hist.cancelar(id);
                System.out.println(ok ? "Cancelado." : "No encontrado.");
            } else if (line.startsWith("reprogramar")){
                try {
                    String inside = line.substring(line.indexOf("(")+1, line.lastIndexOf(")"));
                    String[] p = inside.split(",",2);
                    String id = p[0].trim();
                    LocalDateTime nf = LocalDateTime.parse(p[1].trim());
                    boolean ok = hist.reprogramar(id, nf);
                    System.out.println(ok ? "Reprogramado." : "No se pudo reprogramar.");
                } catch(Exception e){ System.out.println("Formato inválido."); }
            } else if (line.equalsIgnoreCase("undo")){
                System.out.println(hist.undo() ? "Undo OK" : "Nada para deshacer");
            } else if (line.equalsIgnoreCase("redo")){
                System.out.println(hist.redo() ? "Redo OK" : "Nada para rehacer");
            } else System.out.println("Comando desconocido.");
        }
    }

    static String betweenPar(String s){
        if (!s.contains("(") || !s.contains(")")) return "";
        return s.substring(s.indexOf("(")+1, s.lastIndexOf(")")).trim();
    }

    // 9) Planificador de quirófano
    static void quirofanoMenu(){
   Map<String,String> nombres = new HashMap<>();
for (Map.Entry<String, Medico> e : data.medicos.entrySet()) {
    nombres.put(e.getKey(), e.getValue().nombre); // o e.getValue().getNombre()
}

    PlanificadorQuirofanoExtended planner = new PlanificadorQuirofanoExtended(5, nombres);
    System.out.println("Comandos: procesar id,matricula,durMin,YYYY-MM-DDTHH:mm  |  topK K  | salir");

    while(true){
        System.out.print("quir> ");
        String line = sc.nextLine().trim();

        if(line.equalsIgnoreCase("salir")) break;

        if(line.startsWith("procesar")){
            try {
                String inside = line.substring(line.indexOf(" ")+1).trim();
                String[] p = inside.split(",",4);
                if (p.length < 4) throw new IllegalArgumentException("Faltan parámetros");

                String id = p[0].trim();
                String matricula = p[1].trim();

                int durMin;
                try {
                    durMin = Integer.parseInt(p[2].trim());
                } catch(NumberFormatException nfe) {
                    System.out.println("Duración inválida. Debe ser un número entero de minutos.");
                    continue;
                }

                LocalDateTime deadline;
                try {
                    deadline = LocalDateTime.parse(p[3].trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                } catch(Exception ex) {
                    System.out.println("Fecha inválida. Use el formato YYYY-MM-DDTHH:mm");
                    continue;
                }

                SolicitudCirugia s = new SolicitudCirugia(id, matricula, durMin, deadline);

                boolean ok = planner.procesar(s);

                if (ok) {
                    System.out.println("✔ Solicitud " + s.getId() + " asignada correctamente");
                } else {
                    System.out.println("✖ Solicitud " + s.getId() + " no pudo ser asignada, agregada a lista de espera.");
                }

            } catch(Exception e) {
                System.out.println("Formato inválido. Ejemplo: procesar T001,B002,30,2025-12-15T10:00");
            }
        } else if(line.startsWith("topK")) {
            try {
                int k = Integer.parseInt(line.split("\\s+")[1]);
                List<String> top = planner.topKMedicosBloqueados(k);
                for(String s : top) System.out.println(s);
            } catch(Exception e) {
                System.out.println("Formato: topK K");
            }
        } else {
            System.out.println("Comando desconocido");
        }
    }
}
}
