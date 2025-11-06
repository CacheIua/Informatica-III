package agenda;

import modelo.Turno;
import java.time.LocalDateTime;
import java.util.Stack;
import java.util.Optional;

/**
 * Implementación de AgendaConHistorial que envuelve otra AgendaMedico.
 */
public class AgendaConHistorialImpl implements AgendaConHistorial {

    private final AgendaMedico base;
    private final Stack<Action> undo;
    private final Stack<Action> redo;

    public AgendaConHistorialImpl(AgendaMedico base){
        this.base = base;
        this.undo = new Stack<>();
        this.redo = new Stack<>();
    }

    private static class Action {
        enum Type { AGENDAR, CANCELAR, REPROGRAMAR }
        Type type;
        Turno before;
        Turno after;
        Action(Type t, Turno b, Turno a){ this.type=t; this.before=b; this.after=a; }
    }

    @Override
    public boolean agendar(Turno t) {
        boolean ok = base.agendar(t);
        if (ok) { undo.push(new Action(Action.Type.AGENDAR, null, t)); redo.clear(); }
        return ok;
    }

    @Override
    public boolean cancelar(String idTurno) {
        // to undo cancellation we need the Turno; try to find via siguiente scanning around - assume base has method find? fallback: cancel and record is limited
        // We'll attempt to locate the Turno by scanning nexts from epoch (simple but works)
        Optional<Turno> found = findById(idTurno);
        if (!found.isPresent()) return false;
        Turno before = found.get();
        boolean ok = base.cancelar(idTurno);
        if (ok) { undo.push(new Action(Action.Type.CANCELAR, before, null)); redo.clear(); }
        return ok;
    }

    @Override
    public boolean reprogramar(String idTurno, LocalDateTime nuevaFecha) {
        Optional<Turno> f = findById(idTurno);
        if (!f.isPresent()) return false;
        Turno old = f.get();
        Turno nuevo = new Turno(old.id, old.dniPaciente, old.matriculaMedico, nuevaFecha, old.duracionMin, old.motivo);
        boolean canceled = base.cancelar(idTurno);
        if (!canceled) return false;
        boolean ok = base.agendar(nuevo);
        if (!ok) { base.agendar(old); return false; }
        undo.push(new Action(Action.Type.REPROGRAMAR, old, nuevo)); redo.clear(); return true;
    }

    @Override
    public boolean undo() {
        if (undo.isEmpty()) return false;
        Action a = undo.pop();
        switch(a.type){
            case AGENDAR:
                base.cancelar(a.after.id);
                redo.push(a);
                return true;
            case CANCELAR:
                base.agendar(a.before);
                redo.push(a);
                return true;
            case REPROGRAMAR:
                base.cancelar(a.after.id);
                base.agendar(a.before);
                redo.push(a);
                return true;
        }
        return false;
    }

    @Override
    public boolean redo() {
        if (redo.isEmpty()) return false;
        Action a = redo.pop();
        switch(a.type){
            case AGENDAR:
                base.agendar(a.after);
                undo.push(a);
                return true;
            case CANCELAR:
                base.cancelar(a.before.id);
                undo.push(a);
                return true;
            case REPROGRAMAR:
                base.cancelar(a.before.id);
                base.agendar(a.after);
                undo.push(a);
                return true;
        }
        return false;
    }

    @Override
    public Optional<Turno> siguiente(java.time.LocalDateTime t) { return base.siguiente(t); }
    @Override
    public Optional<java.time.LocalDateTime> primerHueco(java.time.LocalDateTime t0, int durMin) { return base.primerHueco(t0, durMin); }

    // naive findById using successive next calls over a long range - acceptable for history use
    private Optional<Turno> findById(String id){
        // search next from now minus 365 days to now + 365 days stepping using base.siguiente
        java.time.LocalDateTime cursor = java.time.LocalDateTime.now().minusDays(365);
        for (int i=0;i<100000;i++){
            Optional<Turno> o = base.siguiente(cursor);
            if (!o.isPresent()) break;
            Turno t = o.get();
            if (t.id.equals(id)) return Optional.of(t);
            cursor = t.fechaHora.plusSeconds(1);
        }
        return Optional.empty();
    }
}
