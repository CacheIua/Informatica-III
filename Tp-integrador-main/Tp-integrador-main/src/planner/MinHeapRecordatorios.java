package planner;

import modelo.Recordatorio;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MinHeapRecordatorios implements Planner {
    private List<Recordatorio> heap = new ArrayList<>();

    @Override
    public void programar(Recordatorio r) {
        heap.add(r);
        siftUp(heap.size()-1);
    }

    @Override
    public Recordatorio proximo() {
        if (heap.isEmpty()) return null;
        Recordatorio root = heap.get(0);
        Recordatorio last = heap.remove(heap.size()-1);
        if (!heap.isEmpty()){ heap.set(0, last); siftDown(0); }
        return root;
    }

    @Override
    public void reprogramar(String id, LocalDateTime nuevaFecha) {
        int idx = -1;
        for (int i=0;i<heap.size();i++) if (heap.get(i).id.equals(id)){ idx=i; break; }
        if (idx==-1) return;
        heap.get(idx).fecha = nuevaFecha;
        siftUp(idx); siftDown(idx);
    }

    @Override
    public int size(){ return heap.size(); }

    private void siftUp(int i){
        while(i>0){
            int p=(i-1)/2;
            if (heap.get(p).fecha.isAfter(heap.get(i).fecha)){ swap(p,i); i=p; } else break;
        }
    }
    private void siftDown(int i){
        int n = heap.size();
        while(true){
            int l=2*i+1, r=2*i+2, smallest=i;
            if (l<n && heap.get(l).fecha.isBefore(heap.get(smallest).fecha)) smallest=l;
            if (r<n && heap.get(r).fecha.isBefore(heap.get(smallest).fecha)) smallest=r;
            if (smallest!=i){ swap(i, smallest); i=smallest; } else break;
        }
    }
    private void swap(int a,int b){ Recordatorio t=heap.get(a); heap.set(a, heap.get(b)); heap.set(b, t); }
}
