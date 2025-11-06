package importer;

import hash.MapaPacientesChaining;
import modelo.Medico;
import java.util.Map;

public class HospitalData {
    public MapaPacientesChaining pacientes;
    public Map<String, Medico> medicos;
    public HospitalData(MapaPacientesChaining p, Map<String, Medico> m){ this.pacientes=p; this.medicos=m; }
}
