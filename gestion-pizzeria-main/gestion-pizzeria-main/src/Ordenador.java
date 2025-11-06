import java.util.List;

public class Ordenador {

    // 🧩 Ordenar por tiempo de preparación (Inserción)
    public void ordenarPorTiempo(List<Pedido> pedidos) {
        for (int i = 1; i < pedidos.size(); i++) {
            Pedido actual = pedidos.get(i);
            int j = i - 1;
            while (j >= 0 && pedidos.get(j).getTiempoPreparacion() > actual.getTiempoPreparacion()) {
                pedidos.set(j + 1, pedidos.get(j));
                j--;
            }
            pedidos.set(j + 1, actual);
        }
    }

    // 💰 Ordenar por precio total (Shellsort)
    public void ordenarPorPrecio(List<Pedido> pedidos) {
        int n = pedidos.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Pedido temp = pedidos.get(i);
                int j;
                for (j = i; j >= gap && pedidos.get(j - gap).getPrecioTotal() > temp.getPrecioTotal(); j -= gap) {
                    pedidos.set(j, pedidos.get(j - gap));
                }
                pedidos.set(j, temp);
            }
        }
    }

    // 👤 Ordenar por nombre de cliente (Quicksort)
    public void ordenarPorNombre(List<Pedido> pedidos, int izquierda, int derecha) {
        if (izquierda < derecha) {
            int indicePivote = particion(pedidos, izquierda, derecha);
            ordenarPorNombre(pedidos, izquierda, indicePivote - 1);
            ordenarPorNombre(pedidos, indicePivote + 1, derecha);
        }
    }

    private int particion(List<Pedido> pedidos, int izquierda, int derecha) {
        String pivote = pedidos.get(derecha).getNombreCliente();
        int i = izquierda - 1;
        for (int j = izquierda; j < derecha; j++) {
            if (pedidos.get(j).getNombreCliente().compareToIgnoreCase(pivote) <= 0) {
                i++;
                Pedido temp = pedidos.get(i);
                pedidos.set(i, pedidos.get(j));
                pedidos.set(j, temp);
            }
        }
        Pedido temp = pedidos.get(i + 1);
        pedidos.set(i + 1, pedidos.get(derecha));
        pedidos.set(derecha, temp);
        return i + 1;
    }
}
