import java.util.*;
import java.io.*;

public class GestorTareas {
    private ArrayList<Tarea> tareas;
    private final String archivo = "tareas.txt";

    public GestorTareas() {
        tareas = new ArrayList<>();
        cargarDesdeArchivo();
    }

    public void agregarTarea(String descripcion) {
    if (descripcion.trim().isEmpty()) {
        System.out.println("La descripción no puede estar vacía.");
        return;
    }
    tareas.add(new Tarea(descripcion));
    System.out.println("Tarea agregada.");
}



    public void listarTareas() {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas.");
            return;
        }
        for (int i = 0; i < tareas.size(); i++) {
            System.out.println((i + 1) + ". " + tareas.get(i));
        }
    }

    public void marcarCompletada(int indice) {
        if (indice < 1 || indice > tareas.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        tareas.get(indice - 1).marcarCompletada();
        System.out.println("Tarea marcada como completada.");
    }

    public void eliminarCompletadas() {
        tareas.removeIf(Tarea::estaCompletada);
        System.out.println("Tareas completadas eliminadas.");
    }

    public void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Tarea t : tareas) {
                pw.println(t.aLineaArchivo());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las tareas: " + e.getMessage());
        }
    }

    private void cargarDesdeArchivo() {
        File f = new File(archivo);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Tarea t = Tarea.desdeLineaArchivo(linea);
                if (t != null) tareas.add(t);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar tareas: " + e.getMessage());
        }
    }
}
