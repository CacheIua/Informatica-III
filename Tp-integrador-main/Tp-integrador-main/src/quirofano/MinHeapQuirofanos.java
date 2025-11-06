package quirofano;

import modelo.Quirofano;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class MinHeapQuirofanos {
    private ArrayList<Quirofano> heap = new ArrayList<>();

    public MinHeapQuirofanos() {
    }

    public void insert(Quirofano q) {
        heap.add(q);
        siftUp(heap.size() - 1);
    }

    public Quirofano extractMin() {
        if (heap.isEmpty())
            throw new NoSuchElementException();
        Quirofano min = heap.get(0);
        Quirofano last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return min;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (heap.get(i).compareTo(heap.get(p)) < 0) {
                Quirofano t = heap.get(i);
                heap.set(i, heap.get(p));
                heap.set(p, t);
                i = p;
            } else
                break;
        }
    }

    private void siftDown(int i) {
        int n = heap.size();
        while (true) {
            int l = 2 * i + 1, r = 2 * i + 2, s = i;
            if (l < n && heap.get(l).compareTo(heap.get(s)) < 0)
                s = l;
            if (r < n && heap.get(r).compareTo(heap.get(s)) < 0)
                s = r;
            if (s != i) {
                Quirofano t = heap.get(i);
                heap.set(i, heap.get(s));
                heap.set(s, t);
                i = s;
            } else
                break;
        }
    }
}
