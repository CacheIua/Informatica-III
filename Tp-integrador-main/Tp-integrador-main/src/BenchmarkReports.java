import modelo.Turno;
import utilidad.Sorting;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Benchmarking insertion/shell/quicksort on synthetic datasets.
 */
public class BenchmarkReports {
    public static void main(String[] args){
        int[] sizes = {1000, 10000, 50000};
        for (int n: sizes){
            List<Turno> ds = synthetic(n);
            List<Turno> a = new ArrayList<>(ds);
            long t1 = System.nanoTime();
            Sorting.insertionSortByFecha(a);
            long t2 = System.nanoTime();
            System.out.printf("InsertionSort (%d): %.2f ms\n", n, (t2-t1)/1e6);

            List<Turno> b = new ArrayList<>(ds);
            t1 = System.nanoTime();
            Sorting.shellSortByDuracion(b);
            t2 = System.nanoTime();
            System.out.printf("ShellSort (%d): %.2f ms\n", n, (t2-t1)/1e6);

            List<Turno> c = new ArrayList<>(ds);
            t1 = System.nanoTime();
            Sorting.quicksortByApellido(c, 0, c.size()-1);
            t2 = System.nanoTime();
            System.out.printf("QuickSortApellido (%d): %.2f ms\n\n", n, (t2-t1)/1e6);
        }
    }

    private static List<Turno> synthetic(int n){
        List<Turno> list = new ArrayList<>();
        for (int i=0;i<n;i++){
            String dniName = (1000 + i%1000) + " Pérez"; // simple
            list.add(new Turno("T"+i, dniName, "A001", LocalDateTime.now().plusMinutes(i), (i%60)+15, "m"));
        }
        return list;
    }
}
