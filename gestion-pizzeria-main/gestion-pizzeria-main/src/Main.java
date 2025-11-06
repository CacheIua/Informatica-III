import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Pizzeria pizzeria = new Pizzeria();
        Ordenador ordenador = new Ordenador();

        int opcion;
        do {
            System.out.println("\n===== SISTEMA DE GESTIÓN DE PEDIDOS - PIZZERÍA =====");
            System.out.println("1. Agregar pedido");
            System.out.println("2. Eliminar pedido");
            System.out.println("3. Actualizar pedido");
            System.out.println("4. Listar pedidos");
            System.out.println("5. Ordenar por tiempo (Inserción)");
            System.out.println("6. Ordenar por precio (Shellsort)");
            System.out.println("7. Ordenar por nombre (Quicksort)");
            System.out.println("8. Salir");
            System.out.print("Elija una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre del cliente: ");
                    String nombre = sc.nextLine();
                    System.out.print("Tiempo de preparación (min): ");
                    int tiempo = sc.nextInt();
                    System.out.print("Precio total: ");
                    double precio = sc.nextDouble();
                    pizzeria.agregarPedido(new Pedido(nombre, tiempo, precio));
                    break;

                case 2:
                    System.out.print("Nombre del cliente a eliminar: ");
                    String eliminar = sc.nextLine();
                    pizzeria.eliminarPedido(eliminar);
                    break;

                case 3:
                    System.out.print("Nombre del cliente a actualizar: ");
                    String actualizar = sc.nextLine();
                    System.out.print("Nuevo tiempo (min): ");
                    int nuevoTiempo = sc.nextInt();
                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = sc.nextDouble();
                    pizzeria.actualizarPedido(actualizar, nuevoTiempo, nuevoPrecio);
                    break;

                case 4:
                    pizzeria.listarPedidos();
                    break;

                case 5:
                    long t1 = TiempoOrdenamiento.medirTiempo(() -> ordenador.ordenarPorTiempo(pizzeria.getPedidos()));
                    TiempoOrdenamiento.mostrarTiempo("Inserción (Tiempo)", t1);
                    pizzeria.listarPedidos();
                    break;

                case 6:
                    long t2 = TiempoOrdenamiento.medirTiempo(() -> ordenador.ordenarPorPrecio(pizzeria.getPedidos()));
                    TiempoOrdenamiento.mostrarTiempo("Shellsort (Precio)", t2);
                    pizzeria.listarPedidos();
                    break;

                case 7:
                    long t3 = TiempoOrdenamiento.medirTiempo(() ->
                            ordenador.ordenarPorNombre(pizzeria.getPedidos(), 0, pizzeria.getPedidos().size() - 1));
                    TiempoOrdenamiento.mostrarTiempo("Quicksort (Nombre)", t3);
                    pizzeria.listarPedidos();
                    break;

                case 8:
                    System.out.println("👋 Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 8);
        sc.close();
    }
}
