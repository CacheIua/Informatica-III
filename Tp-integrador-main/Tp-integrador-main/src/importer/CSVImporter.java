package importer;

import hash.MapaPacientesChaining;
import modelo.Medico;
import modelo.Paciente;
import modelo.Turno;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * CSV importer reading three files with headers:
 * pacientes.csv: dni,nombre
 * medicos.csv: matricula,nombre,especialidad
 * turnos.csv: id,dni,matricula,fechaHora,duracionMin,motivo
 */
public class CSVImporter {

    public static HospitalData importAll(String pacientesCsv, String medicosCsv, String turnosCsv) throws Exception {
        MapaPacientesChaining pacientes = importarPacientes(pacientesCsv);
        Map<String, Medico> medicos = importarMedicos(medicosCsv);
        importarTurnos(turnosCsv, pacientes, medicos);
        return new HospitalData(pacientes, medicos);
    }

    private static MapaPacientesChaining importarPacientes(String file) throws Exception {
        MapaPacientesChaining mapa = new MapaPacientesChaining();
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String header = br.readLine();
            String line;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                if (p.length<2) continue;
                Paciente paciente = new Paciente(p[0].trim(), p[1].trim());
                mapa.put(paciente.dni, paciente);
            }
        }
        return mapa;
    }

    private static Map<String, Medico> importarMedicos(String file) throws Exception {
        Map<String, Medico> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            br.readLine();
            String line;
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",");
                if (p.length<3) continue;
                Medico m = new Medico(p[0].trim(), p[1].trim(), p[2].trim());
                map.put(m.matricula, m);
            }
        }
        return map;
    }

    private static void importarTurnos(String file, MapaPacientesChaining pacientes, Map<String, Medico> medicos) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            br.readLine();
            String line;
            Map<String, Boolean> ids = new HashMap<>();
            while((line=br.readLine())!=null){
                if (line.trim().isEmpty()) continue;
                String[] t = line.split(",");
                if (t.length<6) continue;
                String id = t[0].trim(), dni = t[1].trim(), mat = t[2].trim(), fechaS = t[3].trim();
                int dur = Integer.parseInt(t[4].trim());
                String motivo = t[5].trim();
                if (ids.containsKey(id)){ System.err.println("Duplicated id="+id+" -> rejected"); continue; }
                if (!pacientes.containsKey(dni)){ System.err.println("Paciente no existe: "+dni+" -> rejected"); continue; }
                if (!medicos.containsKey(mat)){ System.err.println("Medico no existe: "+mat+" -> rejected"); continue; }
                LocalDateTime fecha;
                try { fecha = LocalDateTime.parse(fechaS); } catch(Exception e){ System.err.println("Bad date format: "+fechaS); continue; }
                if (fecha.isBefore(LocalDateTime.now())){ System.err.println("Fecha pasada: "+id); continue; }
                if (dur<=0){ System.err.println("Duracion invalida: "+id); continue; }
                Turno turno = new Turno(id, dni, mat, fecha, dur, motivo);
                Medico medico = medicos.get(mat);
                boolean ok = medico.agenda.agendar(turno);
                if (!ok){ System.err.println("Conflicto en agenda para turno "+id); continue; }
                ids.put(id, true);
            }
        }
    }
}
