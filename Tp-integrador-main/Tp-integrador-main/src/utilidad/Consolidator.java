package utilidad;

import modelo.Turno;
import java.util.ArrayList;
import java.util.List;

/**
 * Merge de dos listas ordenadas de turnos en O(|A|+|B|) con deduplicación y logging.
 */
public class Consolidator {

    public static List<Turno> merge(List<Turno> A, List<Turno> B){
        List<Turno> res = new ArrayList<>();
        int i=0,j=0;
        while(i<A.size() && j<B.size()){
            Turno a = A.get(i), b = B.get(j);
            if (a.fechaHora.isBefore(b.fechaHora)){
                if (!alreadyDuplicate(res, a)) res.add(a);
                i++;
            } else if (a.fechaHora.isAfter(b.fechaHora)){
                if (!alreadyDuplicate(res, b)) res.add(b);
                j++;
            } else {
                // same instant
                if (a.id.equals(b.id) || (a.matriculaMedico.equals(b.matriculaMedico) && a.fechaHora.equals(b.fechaHora))){
                    System.err.println("Conflict: keeping one of " + a.id + " and " + b.id);
                    if (!alreadyDuplicate(res, a)) res.add(a);
                } else {
                    if (!alreadyDuplicate(res, a)) res.add(a);
                    if (!alreadyDuplicate(res, b)) res.add(b);
                }
                i++; j++;
            }
        }
        while(i<A.size()){ if (!alreadyDuplicate(res, A.get(i))) res.add(A.get(i)); i++; }
        while(j<B.size()){ if (!alreadyDuplicate(res, B.get(j))) res.add(B.get(j)); j++; }
        return res;
    }

    private static boolean alreadyDuplicate(List<Turno> list, Turno t){
        for (Turno x: list){
            if (x.id.equals(t.id)) return true;
            if (x.matriculaMedico.equals(t.matriculaMedico) && x.fechaHora.equals(t.fechaHora)) return true;
        }
        return false;
    }
}
