package agenda;

import modelo.Turno;
import modelo.Medico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgendaMedicoAVL implements AgendaMedico {

    class Node {
        Turno t;
        LocalDateTime k;
        Node l, r;
        int h;
        Node(Turno tt) { t = tt; k = tt.fechaHora; h = 1; }
    }

    private Node root;
    private Medico owner;
    private List<String> ids = new ArrayList<>();

    public AgendaMedicoAVL(Medico owner) { this.owner = owner; this.root = null; }

    private int height(Node n) { return n == null ? 0 : n.h; }
    private int balance(Node n) { return n == null ? 0 : height(n.l) - height(n.r); }

    private Node rightRot(Node y) {
        Node x = y.l;
        Node T2 = x.r;
        x.r = y;
        y.l = T2;
        y.h = Math.max(height(y.l), height(y.r)) + 1;
        x.h = Math.max(height(x.l), height(x.r)) + 1;
        return x;
    }

    private Node leftRot(Node x) {
        Node y = x.r;
        Node T2 = y.l;
        y.l = x;
        x.r = T2;
        x.h = Math.max(height(x.l), height(x.r)) + 1;
        y.h = Math.max(height(y.l), height(y.r)) + 1;
        return y;
    }

    // private recursive insert used by public insert
    private Node insert(Node node, Turno t) {
        if (node == null) return new Node(t);
        if (t.fechaHora.isBefore(node.k)) node.l = insert(node.l, t);
        else node.r = insert(node.r, t);

        node.h = 1 + Math.max(height(node.l), height(node.r));
        int bal = balance(node);

        // LL
        if (bal > 1 && t.fechaHora.isBefore(node.l.k)) return rightRot(node);
        // RR
        if (bal < -1 && t.fechaHora.isAfter(node.r.k)) return leftRot(node);
        // LR
        if (bal > 1 && t.fechaHora.isAfter(node.l.k)) {
            node.l = leftRot(node.l);
            return rightRot(node);
        }
        // RL
        if (bal < -1 && t.fechaHora.isBefore(node.r.k)) {
            node.r = rightRot(node.r);
            return leftRot(node);
        }
        return node;
    }

    private Node minVal(Node n) {
        Node cur = n;
        while (cur.l != null) cur = cur.l;
        return cur;
    }

    private Node delete(Node node, LocalDateTime key, String id) {
        if (node == null) return null;
        if (key.isBefore(node.k)) node.l = delete(node.l, key, id);
        else if (key.isAfter(node.k)) node.r = delete(node.r, key, id);
        else {
            // could be multiple with same time; find matching id
            if (!node.t.id.equals(id)) {
                node.l = delete(node.l, key, id);
                node.r = delete(node.r, key, id);
                return node;
            }
            if (node.l == null || node.r == null)
                return (node.l != null) ? node.l : node.r;
            Node succ = minVal(node.r);
            node.t = succ.t;
            node.k = succ.k;
            node.r = delete(node.r, succ.k, succ.t.id);
        }
        node.h = 1 + Math.max(height(node.l), height(node.r));
        int bal = balance(node);
        if (bal > 1 && balance(node.l) >= 0) return rightRot(node);
        if (bal > 1 && balance(node.l) < 0) { node.l = leftRot(node.l); return rightRot(node); }
        if (bal < -1 && balance(node.r) <= 0) return leftRot(node);
        if (bal < -1 && balance(node.r) > 0) { node.r = rightRot(node.r); return leftRot(node); }
        return node;
    }

    private Node pred(Node node, LocalDateTime key) {
        Node res = null;
        while (node != null) {
            if (node.k.isBefore(key)) { res = node; node = node.r; }
            else node = node.l;
        }
        return res;
    }

    private Node succ(Node node, LocalDateTime key) {
        Node res = null;
        while (node != null) {
            if (!node.k.isBefore(key)) { res = node; node = node.l; }
            else node = node.r;
        }
        return res;
    }

    private boolean overlaps(Turno a, Turno b) {
        return !(a.fin().isEqual(b.fechaHora) || a.fin().isBefore(b.fechaHora) ||
                 b.fin().isEqual(a.fechaHora) || b.fin().isBefore(a.fechaHora));
    }

    @Override
    public boolean agendar(Turno t) {
        Node p = pred(root, t.fechaHora);
        if (p != null && overlaps(p.t, t)) return false;
        Node s = succ(root, t.fechaHora);
        if (s != null && overlaps(s.t, t)) return false;
        root = insert(root, t);
        ids.add(t.id);
        return true;
    }

    @Override
    public boolean cancelar(String id) {
        Node f = findById(root, id);
        if (f == null) return false;
        root = delete(root, f.k, id);
        ids.remove(id);
        return true;
    }

    private Node findById(Node node, String id) {
        if (node == null) return null;
        if (node.t.id.equals(id)) return node;
        Node l = findById(node.l, id);
        if (l != null) return l;
        return findById(node.r, id);
    }

    @Override
    public Optional<Turno> siguiente(LocalDateTime t) {
        Node s = succ(root, t);
        return s == null ? Optional.empty() : Optional.of(s.t);
    }

    @Override
    public Optional<LocalDateTime> primerHueco(LocalDateTime t0, int durMin) {
        java.time.LocalTime start = java.time.LocalTime.of(8, 0);
        java.time.LocalTime end = java.time.LocalTime.of(16, 0);
        java.time.LocalDateTime cursor = t0;
        if (cursor.toLocalTime().isBefore(start)) cursor = java.time.LocalDateTime.of(cursor.toLocalDate(), start);
        if (cursor.toLocalTime().isAfter(end)) cursor = java.time.LocalDateTime.of(cursor.toLocalDate().plusDays(1), start);

        Optional<Turno> nextOpt = siguiente(cursor);
        Turno prev = null;

        for (int days = 0; days < 365; days++) {
            if (!nextOpt.isPresent()) {
                if (!cursor.plusMinutes(durMin).isAfter(java.time.LocalDateTime.of(cursor.toLocalDate(), end)))
                    return Optional.of(cursor);
                cursor = java.time.LocalDateTime.of(cursor.toLocalDate().plusDays(1), start);
                nextOpt = siguiente(cursor);
                prev = null;
                continue;
            }
            Turno next = nextOpt.get();
            java.time.LocalDateTime dayEnd = java.time.LocalDateTime.of(cursor.toLocalDate(), end);
            if (next.fechaHora.isAfter(dayEnd)) {
                if (!cursor.plusMinutes(durMin).isAfter(dayEnd)) return Optional.of(cursor);
                cursor = java.time.LocalDateTime.of(cursor.toLocalDate().plusDays(1), start);
                nextOpt = siguiente(cursor);
                prev = null;
                continue;
            }
            if (prev == null) {
                if (!cursor.plusMinutes(durMin).isAfter(next.fechaHora)) return Optional.of(cursor);
            } else {
                java.time.LocalDateTime finPrev = prev.fin();
                if (!finPrev.plusMinutes(durMin).isAfter(next.fechaHora)) return Optional.of(finPrev);
            }
            prev = next;
            nextOpt = siguiente(next.fechaHora.plusMinutes(next.duracionMin + 1));
            cursor = prev.fin();
            if (cursor.toLocalTime().isAfter(end) || cursor.equals(dayEnd)) {
                cursor = java.time.LocalDateTime.of(cursor.toLocalDate().plusDays(1), start);
                nextOpt = siguiente(cursor);
                prev = null;
            }
        }
        return Optional.empty();
    }

    // --- Nuevo método público para insertar (para uso interno por planners)
    public void insert(Turno t) {
        root = insert(root, t);
        ids.add(t.id);
    }

    // --- Nuevo método público para recolectar todos los turnos en orden
    public java.util.List<Turno> getTurnos() {
        java.util.List<Turno> res = new ArrayList<>();
        inOrderCollect(root, res);
        return res;
    }

    private void inOrderCollect(Node n, java.util.List<Turno> list) {
        if (n == null) return;
        inOrderCollect(n.l, list);
        list.add(n.t);
        inOrderCollect(n.r, list);
    }

    // simple inorder printer (existente)
    public void inorderPrint() { inorder(root); }
    private void inorder(Node n) { if (n == null) return; inorder(n.l); System.out.println(n.t); inorder(n.r); }
}
