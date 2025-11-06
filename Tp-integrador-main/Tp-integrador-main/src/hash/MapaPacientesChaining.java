package hash;

import modelo.Paciente;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Hash table with chaining and rehash at load factor 0.75.
 */
public class MapaPacientesChaining implements MapaPacientes {

    static class Entry { String key; Paciente val; Entry(String k, Paciente v){ key=k; val=v; } }

    private List<List<Entry>> buckets;
    private int capacity;
    private int size;

    public MapaPacientesChaining(){
        this.capacity = 16;
        buckets = new ArrayList<>();
        for (int i=0;i<capacity;i++) buckets.add(new LinkedList<>());
        size = 0;
    }

    private int index(String k){
        int h = k.hashCode();
        h ^= (h>>>16);
        return Math.abs(h) % capacity;
    }

    @Override
    public void put(String dni, Paciente p){
        if ((double)(size+1)/capacity > 0.75) rehash();
        int idx = index(dni);
        List<Entry> bucket = buckets.get(idx);
        for (Entry e: bucket) if (e.key.equals(dni)){ e.val = p; return; }
        bucket.add(new Entry(dni, p)); size++;
    }

    @Override
    public Paciente get(String dni){
        int idx = index(dni);
        for (Entry e: buckets.get(idx)) if (e.key.equals(dni)) return e.val;
        return null;
    }

    @Override
    public boolean remove(String dni){
        int idx = index(dni);
        Iterator<Entry> it = buckets.get(idx).iterator();
        while(it.hasNext()){
            Entry e = it.next();
            if (e.key.equals(dni)){ it.remove(); size--; return true; }
        }
        return false;
    }

    @Override
    public boolean containsKey(String dni){ return get(dni)!=null; }

    @Override
    public int size(){ return size; }

    @Override
    public Iterable<String> keys(){
        List<String> ks = new ArrayList<>();
        for (List<Entry> b: buckets) for (Entry e: b) ks.add(e.key);
        return ks;
    }

    private void rehash(){
        int newCap = capacity*2;
        List<List<Entry>> newB = new ArrayList<>();
        for (int i=0;i<newCap;i++) newB.add(new LinkedList<>());
        for (List<Entry> b: buckets) for (Entry e: b){
            int idx = Math.abs((e.key.hashCode() ^ (e.key.hashCode()>>>16))) % newCap;
            newB.get(idx).add(e);
        }
        buckets = newB; capacity = newCap;
    }
}
