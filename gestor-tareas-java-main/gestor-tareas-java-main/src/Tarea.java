public class Tarea {
    private String descripcion;
    private boolean completada;

    public Tarea(String descripcion) {
        this.descripcion = descripcion;
        this.completada = false;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean estaCompletada() {
        return completada;
    }

    public void marcarCompletada() {
        this.completada = true;
    }

    @Override
    public String toString() {
        return (completada ? "[✔] " : "[ ] ") + descripcion;
    }

    // Para guardar en archivo
    public String aLineaArchivo() {
        return descripcion + ";" + (completada ? "1" : "0");
    }

    // Para cargar desde archivo
    public static Tarea desdeLineaArchivo(String linea) {
        String[] partes = linea.split(";");
        if (partes.length == 2) {
            Tarea t = new Tarea(partes[0]);
            if (partes[1].equals("1")) t.marcarCompletada();
            return t;
        }
        return null;
    }
}
