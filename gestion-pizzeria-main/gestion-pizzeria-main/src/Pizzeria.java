import java.util.ArrayList;
import java.util.List;

public class Pizzeria {
    private List<Pedido> pedidos;

    public Pizzeria() {
        pedidos = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
        System.out.println("✅ Pedido agregado correctamente.");
    }

    public void eliminarPedido(String nombreCliente) {
        pedidos.removeIf(p -> p.getNombreCliente().equalsIgnoreCase(nombreCliente));
        System.out.println("🗑️ Pedido eliminado si existía.");
    }

    public void actualizarPedido(String nombreCliente, int nuevoTiempo, double nuevoPrecio) {
        for (Pedido p : pedidos) {
            if (p.getNombreCliente().equalsIgnoreCase(nombreCliente)) {
                p.setTiempoPreparacion(nuevoTiempo);
                p.setPrecioTotal(nuevoPrecio);
                System.out.println("🔄 Pedido actualizado.");
                return;
            }
        }
        System.out.println("⚠️ No se encontró el pedido del cliente: " + nombreCliente);
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        System.out.println("\n--- LISTA DE PEDIDOS ---");
        pedidos.forEach(System.out::println);
    }
}
