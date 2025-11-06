import java.util.List;

public class TiempoOrdenamiento {

    public static long medirTiempo(Runnable algoritmo) {
        long inicio = System.nanoTime();
        algoritmo.run();
        long fin = System.nanoTime();
        return fin - inicio;
    }

    public static void mostrarTiempo(String nombreAlgoritmo, long tiempoNanosegundos) {
        System.out.printf("⏱️ %s: %.4f ms%n", nombreAlgoritmo, tiempoNanosegundos / 1_000_000.0);
    }
}
