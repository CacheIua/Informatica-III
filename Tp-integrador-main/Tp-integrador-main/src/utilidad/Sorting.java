package utilidad;

import modelo.Turno;
import java.util.List;

/**
 * Insertion (estable), Shellsort and Quicksort (Lomuto) implementations.
 */
public class Sorting {

    public static void insertionSortByFecha(List<Turno> arr){
        for (int i=1;i<arr.size();i++){
            Turno key = arr.get(i);
            int j = i-1;
            while (j>=0 && arr.get(j).fechaHora.isAfter(key.fechaHora)){
                arr.set(j+1, arr.get(j)); j--;
            }
            arr.set(j+1, key);
        }
    }

    public static void shellSortByDuracion(List<Turno> arr){
        int n = arr.size();
        for (int gap = n/2; gap>0; gap/=2){
            for (int i=gap;i<n;i++){
                Turno temp = arr.get(i);
                int j=i;
                while (j>=gap && arr.get(j-gap).duracionMin > temp.duracionMin){
                    arr.set(j, arr.get(j-gap)); j-=gap;
                }
                arr.set(j, temp);
            }
        }
    }

    public static void quicksortByApellido(List<Turno> arr, int l, int r){
        if (l<r){
            int p = partition(arr, l, r);
            quicksortByApellido(arr, l, p-1);
            quicksortByApellido(arr, p+1, r);
        }
    }

    private static int partition(List<Turno> arr, int l, int r){
        String pivot = apellido(arr.get(r).dniPaciente);
        int i = l;
        for (int j=l;j<r;j++){
            if (apellido(arr.get(j).dniPaciente).compareTo(pivot) <= 0){
                swap(arr, i, j); i++;
            }
        }
        swap(arr, i, r); return i;
    }

    private static String apellido(String dniNombre){
        // dniPaciente stored as "dni nombre"; try to extract last token as apellido; fallback to entire string
        String[] parts = dniNombre.split(" ");
        if (parts.length>=2) return parts[parts.length-1];
        return dniNombre;
    }

    private static void swap(List<Turno> a, int i, int j){
        Turno t = a.get(i); a.set(i, a.get(j)); a.set(j, t);
    }
}
