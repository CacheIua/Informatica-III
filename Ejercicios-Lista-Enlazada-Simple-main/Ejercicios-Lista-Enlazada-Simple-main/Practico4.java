import java.util.Scanner;

class Nodo {
    int dato;
    Nodo siguiente;

    public Nodo(int dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}

class ListaEnlazada {
    private Nodo cabeza;

    public ListaEnlazada() {
        cabeza = null;
    }

    public void insertarInicio(int dato) {
        Nodo nuevo = new Nodo(dato);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
    }

    public void insertarFinal(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }
        Nodo aux = cabeza;
        while (aux.siguiente != null) aux = aux.siguiente;
        aux.siguiente = nuevo;
    }

    public void eliminar(int valor) {
        if (cabeza == null) return;
        if (cabeza.dato == valor) {
            cabeza = cabeza.siguiente;
            return;
        }
        Nodo aux = cabeza;
        while (aux.siguiente != null && aux.siguiente.dato != valor) aux = aux.siguiente;
        if (aux.siguiente != null) aux.siguiente = aux.siguiente.siguiente;
    }

    public boolean buscar(int valor) {
        Nodo aux = cabeza;
        while (aux != null) {
            if (aux.dato == valor) return true;
            aux = aux.siguiente;
        }
        return false;
    }

    public int contar() {
        int c = 0;
        Nodo aux = cabeza;
        while (aux != null) {
            c++;
            aux = aux.siguiente;
        }
        return c;
    }

    public void invertir() {
        Nodo ant = null, act = cabeza, sig;
        while (act != null) {
            sig = act.siguiente;
            act.siguiente = ant;
            ant = act;
            act = sig;
        }
        cabeza = ant;
    }

    public void insertarEn(int pos, int valor) {
        Nodo nuevo = new Nodo(valor);
        if (pos == 0) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
            return;
        }
        Nodo aux = cabeza;
        for (int i = 0; i < pos - 1 && aux != null; i++) aux = aux.siguiente;
        if (aux != null) {
            nuevo.siguiente = aux.siguiente;
            aux.siguiente = nuevo;
        }
    }

    public void eliminarDuplicados() {
        Nodo actual = cabeza;
        while (actual != null) {
            Nodo temp = actual;
            while (temp.siguiente != null) {
                if (temp.siguiente.dato == actual.dato) temp.siguiente = temp.siguiente.siguiente;
                else temp = temp.siguiente;
            }
            actual = actual.siguiente;
        }
    }

    public void mostrar() {
        Nodo aux = cabeza;
        while (aux != null) {
            System.out.print(aux.dato + (aux.siguiente != null ? " -> " : ""));
            aux = aux.siguiente;
        }
        System.out.println();
    }
}

class Alumno {
    String nombre;
    int legajo;
    Alumno siguiente;

    public Alumno(String nombre, int legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
    }
}

class ListaAlumnos {
    private Alumno cabeza;

    public void agregarAlumno(String nombre, int legajo) {
        Alumno nuevo = new Alumno(nombre, legajo);
        if (cabeza == null) cabeza = nuevo;
        else {
            Alumno aux = cabeza;
            while (aux.siguiente != null) aux = aux.siguiente;
            aux.siguiente = nuevo;
        }
    }

    public boolean buscarAlumno(int legajo) {
        Alumno aux = cabeza;
        while (aux != null) {
            if (aux.legajo == legajo) return true;
            aux = aux.siguiente;
        }
        return false;
    }

    public void eliminarAlumno(int legajo) {
        if (cabeza == null) return;
        if (cabeza.legajo == legajo) {
            cabeza = cabeza.siguiente;
            return;
        }
        Alumno aux = cabeza;
        while (aux.siguiente != null && aux.siguiente.legajo != legajo) aux = aux.siguiente;
        if (aux.siguiente != null) aux.siguiente = aux.siguiente.siguiente;
    }

    public void mostrar() {
        Alumno aux = cabeza;
        while (aux != null) {
            System.out.println(aux.nombre + " (" + aux.legajo + ")");
            aux = aux.siguiente;
        }
    }
}

public class Practico4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- PRACTICO 4: LISTA ENLAZADA SIMPLE ---");
            System.out.println("1. Crear un nodo");
            System.out.println("2. Insertar al inicio");
            System.out.println("3. Insertar al final");
            System.out.println("4. Eliminar por valor");
            System.out.println("5. Buscar un valor");
            System.out.println("6. Contar elementos");
            System.out.println("7. Invertir la lista");
            System.out.println("8. Insertar en posición");
            System.out.println("9. Eliminar duplicados");
            System.out.println("10. Registro de alumnos");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1: {
                    Nodo n1 = new Nodo(10);
                    Nodo n2 = new Nodo(20);
                    Nodo n3 = new Nodo(30);
                    n1.siguiente = n2;
                    n2.siguiente = n3;
                    Nodo aux = n1;
                    while (aux != null) {
                        System.out.print(aux.dato + (aux.siguiente != null ? " -> " : ""));
                        aux = aux.siguiente;
                    }
                    System.out.println();
                    break;
                }
                case 2: {
                    ListaEnlazada lista = new ListaEnlazada();
                    lista.insertarInicio(10);
                    lista.insertarInicio(20);
                    lista.insertarInicio(30);
                    lista.mostrar();
                    break;
                }
                case 3: {
                    ListaEnlazada lista = new ListaEnlazada();
                    lista.insertarFinal(1);
                    lista.insertarFinal(2);
                    lista.insertarFinal(3);
                    lista.mostrar();
                    break;
                }
                case 4: {
                    ListaEnlazada lista = new ListaEnlazada();
                    lista.insertarFinal(10);
                    lista.insertarFinal(20);
                    lista.insertarFinal(30);
                    lista.insertarFinal(40);
                    lista.eliminar(30);
                    lista.mostrar();
                    break;
                }
                case 5: {
                    ListaEnlazada lista = new ListaEnlazada();
                    lista.insertarFinal(5);
                    lista.insertarFinal(15);
                    lista.insertarFinal(25);
                    lista.insertarFinal(35);
                    System.out.println("Buscar 25: " + lista.buscar(25));
                    System.out.println("Buscar 100: " + lista.buscar(100));
                    break;
                }
                case 6: {
                    ListaEnlazada lista = new ListaEnlazada();
                    for (int i = 1; i <= 5; i++) lista.insertarFinal(i);
                    System.out.println("Cantidad de elementos: " + lista.contar());
                    break;
                }
                case 7: {
                    ListaEnlazada lista = new ListaEnlazada();
                    lista.insertarFinal(10);
                    lista.insertarFinal(20);
                    lista.insertarFinal(30);
                    lista.insertarFinal(40);
                    lista.invertir();
                    lista.mostrar();
                    break;
                }
                case 8: {
                    ListaEnlazada lista = new ListaEnlazada();
                    lista.insertarFinal(1);
                    lista.insertarFinal(2);
                    lista.insertarFinal(4);
                    lista.insertarEn(2, 3);
                    lista.mostrar();
                    break;
                }
                case 9: {
                    ListaEnlazada lista = new ListaEnlazada();
                    lista.insertarFinal(1);
                    lista.insertarFinal(2);
                    lista.insertarFinal(2);
                    lista.insertarFinal(3);
                    lista.insertarFinal(1);
                    lista.eliminarDuplicados();
                    lista.mostrar();
                    break;
                }
                case 10: {
                    ListaAlumnos lista = new ListaAlumnos();
                    System.out.print("Ingrese cantidad de alumnos: ");
                    int n = sc.nextInt();
                    sc.nextLine();
                    for (int i = 0; i < n; i++) {
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Legajo: ");
                        int legajo = sc.nextInt();
                        sc.nextLine();
                        lista.agregarAlumno(nombre, legajo);
                    }
                    System.out.println("\nLista de alumnos:");
                    lista.mostrar();
                    System.out.print("\nIngrese legajo a buscar: ");
                    int l = sc.nextInt();
                    System.out.println(lista.buscarAlumno(l) ? "Encontrado." : "No encontrado.");
                    System.out.print("\nIngrese legajo a eliminar: ");
                    int e = sc.nextInt();
                    lista.eliminarAlumno(e);
                    System.out.println("\nLista final:");
                    lista.mostrar();
                    break;
                }
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 0);

        sc.close();
    }
}
