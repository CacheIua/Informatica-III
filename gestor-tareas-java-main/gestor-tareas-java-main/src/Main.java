import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorTareas gestor = new GestorTareas();
        int opcion;

        do {
            System.out.println("\n===== GESTOR DE TAREAS =====");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Listar tareas");
            System.out.println("3. Marcar tarea como completada");
            System.out.println("4. Eliminar tareas completadas");
            System.out.println("5. Guardar y salir");
            System.out.print("Elige una opción: ");

            while (!sc.hasNextInt()) {
                System.out.print("Ingrese un número válido: ");
                sc.next();
            }
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Descripción de la tarea: ");
                    String desc = sc.nextLine();
                    gestor.agregarTarea(desc);
                    break;
                case 2:
                    gestor.listarTareas();
                    break;
                case 3:
                    gestor.listarTareas();
                    System.out.print("Número de la tarea a completar: ");
                    int num = sc.nextInt();
                    gestor.marcarCompletada(num);
                    break;
                case 4:
                    gestor.eliminarCompletadas();
                    break;
                case 5:
                    gestor.guardarEnArchivo();
                    System.out.println("💾 Cambios guardados. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 5);
        sc.close();
    }
}
