import java.util.Scanner;

class PilaArreglo {
    private int[] datos;
    private int tope;
    private int capacidad;

    public PilaArreglo(int capacidad) {
        this.capacidad = capacidad;
        datos = new int[capacidad];
        tope = -1;
    }

    public boolean isEmpty() {
        return tope == -1;
    }

    public boolean isFull() {
        return tope == capacidad - 1;
    }

    public void push(int dato) {
        if (!isFull()) {
            datos[++tope] = dato;
        } else {
            System.out.println("Pila llena.");
        }
    }

    public int pop() {
        if (!isEmpty()) {
            return datos[tope--];
        } else {
            System.out.println("Pila vacía.");
            return -1;
        }
    }

    public int top() {
        if (!isEmpty()) {
            return datos[tope];
        } else {
            System.out.println("Pila vacía.");
            return -1;
        }
    }
}

class ColaArreglo {
    private int[] datos;
    private int frente, fin, tamaño, capacidad;

    public ColaArreglo(int capacidad) {
        this.capacidad = capacidad;
        datos = new int[capacidad];
        frente = 0;
        fin = -1;
        tamaño = 0;
    }

    public boolean isEmpty() {
        return tamaño == 0;
    }

    public boolean isFull() {
        return tamaño == capacidad;
    }

    public void enqueue(int dato) {
        if (!isFull()) {
            fin = (fin + 1) % capacidad;
            datos[fin] = dato;
            tamaño++;
        } else {
            System.out.println("Cola llena.");
        }
    }

    public int dequeue() {
        if (!isEmpty()) {
            int valor = datos[frente];
            frente = (frente + 1) % capacidad;
            tamaño--;
            return valor;
        } else {
            System.out.println("Cola vacía.");
            return -1;
        }
    }

    public int top() {
        if (!isEmpty()) {
            return datos[frente];
        } else {
            System.out.println("Cola vacía.");
            return -1;
        }
    }
}

class ColaCircular {
    private String[] datos;
    private int frente, fin, tamaño, capacidad;

    public ColaCircular(int capacidad) {
        this.capacidad = capacidad;
        datos = new String[capacidad];
        frente = 0;
        fin = -1;
        tamaño = 0;
    }

    public void enqueue(String llamada) {
        fin = (fin + 1) % capacidad;
        datos[fin] = llamada;
        if (tamaño < capacidad) tamaño++;
        else frente = (frente + 1) % capacidad; // Sobrescribe la más antigua
    }

    public void mostrar() {
        System.out.println("Estado final de la cola circular:");
        for (int i = 0; i < tamaño; i++) {
            int index = (frente + i) % capacidad;
            System.out.print(datos[index] + " ");
        }
        System.out.println();
    }
}

public class Practico3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- PRACTICO 3: PILA Y COLA CON ARREGLOS ---");
            System.out.println("1. Implementación de Pila");
            System.out.println("2. Implementación de Cola");
            System.out.println("3. Invertir una Cadena con Pila");
            System.out.println("4. Simulación de Turnos con Cola");
            System.out.println("5. Palíndromo con Pila y Cola");
            System.out.println("6. Deshacer/Rehacer con Pila");
            System.out.println("7. Simulación de Impresora con Cola");
            System.out.println("8. Cola Circular para Gestión de Llamadas");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1: {
                    PilaArreglo pila = new PilaArreglo(5);
                    pila.push(10);
                    pila.push(20);
                    pila.push(30);
                    pila.push(40);
                    pila.pop();
                    pila.pop();
                    System.out.println("Ejercicio 1 completado.");
                    break;
                }
                case 2 :{
                    ColaArreglo cola = new ColaArreglo(5);
                    cola.enqueue(1);
                    cola.enqueue(2);
                    cola.enqueue(3);
                    cola.enqueue(4);
                    cola.dequeue();
                    System.out.println("Ejercicio 2 completado.");
                    break;
                }
                case 3 : {
                    System.out.print("Ingrese una cadena: ");
                    String cadena = sc.nextLine();
                    PilaArreglo pila = new PilaArreglo(cadena.length());
                    for (char c : cadena.toCharArray()) pila.push(c);
                    System.out.print("Cadena invertida: ");
                    while (!pila.isEmpty()) System.out.print((char) pila.pop());
                    System.out.println();
                    break;
                }
                case 4 : {
                    ColaArreglo cola = new ColaArreglo(10);
                    String[] clientes = {"Ana", "Luis", "Marta", "Pedro"};
                    for (String c : clientes) System.out.println("Llega " + c);
                    cola.enqueue(1);
                    cola.enqueue(2);
                    cola.enqueue(3);
                    cola.enqueue(4);
                    System.out.println("Se atienden dos clientes...");
                    cola.dequeue();
                    cola.dequeue();
                    System.out.println("Ejercicio 4 completado.");
                    break;
                }
                case 5 : {
                    System.out.print("Ingrese una palabra: ");
                    String palabra = sc.nextLine();
                    PilaArreglo pila = new PilaArreglo(palabra.length());
                    ColaArreglo cola = new ColaArreglo(palabra.length());
                    for (char c : palabra.toCharArray()) {
                        pila.push(c);
                        cola.enqueue(c);
                    }
                    boolean palindromo = true;
                    while (!pila.isEmpty()) {
                        if (pila.pop() != cola.dequeue()) {
                            palindromo = false;
                            break;
                        }
                    }
                    System.out.println(palindromo ? "Es palíndromo." : "No es palíndromo.");
                    break;
                }
                case 6 : {
                    PilaArreglo deshacer = new PilaArreglo(10);
                    PilaArreglo rehacer = new PilaArreglo(10);
                    String[] acciones = {"Escribir", "Borrar", "Copiar", "Pegar", "Cortar"};
                    for (String a : acciones) deshacer.push(a.hashCode());
                    System.out.println("Simulación de acciones completada.");
                    System.out.println("Deshaciendo última acción...");
                    rehacer.push(deshacer.pop());
                    System.out.println("Ejercicio 6 completado.");
                    break;
                }
                case 7 : {
                    ColaArreglo impresora = new ColaArreglo(10);
                    for (int i = 1; i <= 5; i++) impresora.enqueue(i);
                    for (int i = 0; i < 3; i++) impresora.dequeue();
                    System.out.println("Ejercicio 7 completado.");
                    break;
                }
                case 8 : {
                    ColaCircular llamadas = new ColaCircular(5);
                    for (int i = 1; i <= 8; i++) llamadas.enqueue("Llamada" + i);
                    llamadas.mostrar();
                    break;
                }
                case 0 : System.out.println("Saliendo...") ; break;
                default : {
                    if (opcion < 0 || opcion > 8){
                        System.out.println("Opción inválida.");
                        }
                        break;
                }
            }
        } while (opcion != 0);
        sc.close();
    }
}
