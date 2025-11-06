// Practico7.java - Montículo Binario (MinHeap/MaxHeap) interactivo
import java.util.*;

class MinHeap {
    int[] heap; int size;
    public MinHeap(int capacity) { 
        heap = new int[capacity]; 
        size = 0; 
    }
    public MinHeap(int[] datos) {
        heap = Arrays.copyOf(datos, datos.length);
        size = datos.length; heapify();
    }
    private void ensureCapacity() {
        if (size == heap.length){
            heap = Arrays.copyOf(heap, heap.length*2+1);
        }  
    }
    public boolean isEmpty() {
        return size==0; 
    }
    public void add(int val) {
        ensureCapacity();
        heap[size++] = val;
        percolateUp(size-1);
    }
    private void percolateUp(int i) {
        int cur = heap[i];
        while (i>0) {
            int p = (i-1)/2;
            if (heap[p] <= cur) break;
            heap[i] = heap[p];
            System.out.println("swap index " + i + " with parent " + p + " -> " + heap[p]);
            i = p;
        }
        heap[i] = cur;
    }
    public int peek() { return isEmpty() ? Integer.MIN_VALUE : heap[0]; }
    public int poll() {
        if (isEmpty()) return Integer.MIN_VALUE;
        int min = heap[0];
        heap[0] = heap[size-1];
        size--; percolateDown(0);
        return min;
    }
    private void percolateDown(int i) {
        int cur = heap[i];
        while (true) {
            int l = 2*i+1, r = 2*i+2, smallest = i;
            if (l < size && heap[l] < heap[smallest]) smallest = l;
            if (r < size && heap[r] < heap[smallest]) smallest = r;
            if (smallest == i) break;
            heap[i] = heap[smallest]; heap[smallest] = cur; i = smallest;
        }
    }
    protected  void heapify() {
         for (int i = (size/2)-1; i>=0; i--){
            percolateDown(i); 
         } 
        }
    public void printTree() {
        int level=0, count=0;
        for (int i=0;i<size;i++) {
            System.out.print(heap[i] + " "); count++;
            if (count == (1<<level)) { 
                System.out.println();
                level++;
                count=0;
            }
        }
        System.out.println();
    }
    public void printArray() { 
    System.out.println(Arrays.toString(Arrays.copyOf(heap, size)));
    }
    public static void heapsort(int[] arr) {
        MinHeap h = new MinHeap(arr);
        for (int i=0;i<arr.length;i++) {
            arr[i]=h.poll();
        }
    }
}

class MaxHeap extends MinHeap {
    public MaxHeap(int capacity) { super(capacity); }
    public MaxHeap(int[] datos) { super(datos); flipSign(); }
    private void flipSign() { for (int i=0;i<size;i++) heap[i] = -heap[i]; heapify(); }
    @Override public void add(int val) { super.add(-val); }
    @Override public int peek() { return -super.peek(); }
    @Override public int poll() { return -super.poll(); }
}

class Paciente implements Comparable<Paciente> {
    String nombre; int prioridad;
    public Paciente(String n, int p) { nombre=n; prioridad=p; }
    public int compareTo(Paciente o) { return Integer.compare(this.prioridad, o.prioridad); }
    public String toString(){return nombre+"("+prioridad+")";}
}

public class Practico7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opt;
        do {
            System.out.println("\n--- PRACTICO 7: MONTICULO BINARIO ---");
            System.out.println("1. Crear MinHeap y probar add/poll");
            System.out.println("2. percolateUp demo (muestra swaps)");
            System.out.println("3. percolateDown demo (muestra array antes/después)");
            System.out.println("4. Mostrar heap como árbol");
            System.out.println("5. Construir desde arreglo (heapify)");
            System.out.println("6. Heapsort (usar MinHeap)");
            System.out.println("7. MaxHeap demo");
            System.out.println("8. Cola prioridad de pacientes");
            System.out.println("9. printArray seguimiento");
            System.out.println("10. Agenda de tareas con prioridad (integrador)");
            System.out.println("0. Salir");
            System.out.print("Elija opción: "); opt = sc.nextInt(); sc.nextLine();
            switch(opt) {
                case 1: {
                    MinHeap h = new MinHeap(10);
                    int[] vals = {20,5,15,3,11};
                    for (int v: vals) h.add(v);
                    System.out.println("Orden de extracción:"); while(!h.isEmpty()) System.out.print(h.poll()+" "); System.out.println();
                    break;
                }
                case 2: {
                    MinHeap h = new MinHeap(4);
                    System.out.println("Insertando 20,5,15,3,11 mostrando swaps:");
                    h.add(20); h.add(5); h.add(15); h.add(3);h.add(11);; h.printArray();
                    break;
                }
                case 3: {
                    MinHeap h = new MinHeap(new int[]{20,5,15,3,11});
                    System.out.println("Antes heapify (array original):"); h.printArray();
                    System.out.println("Estructura en árbol:"); h.printTree();
                    System.out.println("Extrayendo min (mostrar antes/despues):");
                    int before = h.peek(); System.out.println("Antes poll: " + Arrays.toString(Arrays.copyOf(h.heap,h.size))); h.poll(); System.out.println("Despues: " + Arrays.toString(Arrays.copyOf(h.heap,h.size)));
                    break;
                }
                case 4: {
                    MinHeap h = new MinHeap(new int[]{3,5,15,20,11}); System.out.println("Tree:"); h.printTree();
                    break;
                }
                case 5: {
                    int[] datos = {9,4,7,1,6,2}; MinHeap h = new MinHeap(datos);
                    System.out.println("Heap construido (interna):"); h.printArray(); h.printTree();
                    break;
                }
                case 6: {
                    int[] arr = {9,4,7,1,6,2}; System.out.println("Antes: " + Arrays.toString(arr)); MinHeap.heapsort(arr); System.out.println("Heapsort resultado: " + Arrays.toString(arr));
                    break;
                }
                case 7: {
                    int[] arr = {10,3,15,8,6,12}; MaxHeap mh = new MaxHeap(arr.length);
                    for (int v: arr) mh.add(v);
                    System.out.print("Eliminación de mayor a menor: "); while(!mh.isEmpty()) System.out.print(mh.poll()+" "); System.out.println();
                    break;
                }
                case 8: {
                    PriorityQueue<Paciente> pq = new PriorityQueue<>();
                    pq.add(new Paciente("Ana",2)); pq.add(new Paciente("Luis",1)); pq.add(new Paciente("Marta",3)); pq.add(new Paciente("Pedro",1)); pq.add(new Paciente("Sofia",2));
                    System.out.println("Orden de atención:"); while(!pq.isEmpty()) System.out.println(pq.poll());
                    break;
                }
                case 9: {
                    MinHeap h = new MinHeap(10);
                    h.add(10); h.printArray(); h.add(5); h.printArray(); h.add(8); h.printArray(); h.poll(); h.printArray();
                    break;
                }
                case 10: {
                    // Agenda: usar PriorityQueue of Tarea
                    class Tarea implements Comparable<Tarea>{ String desc; int pr; Tarea(String d,int p){desc=d;pr=p;} public int compareTo(Tarea o){return Integer.compare(this.pr,o.pr);} public String toString(){return desc+"("+pr+")";} }
                    PriorityQueue<Tarea> agenda = new PriorityQueue<>();
                    boolean salir=false; while(!salir) {
                        System.out.println("1.Add 2.peek 3.poll 4.show 0.exit"); int o=sc.nextInt(); sc.nextLine();
                        switch(o) {
                            case 1: { System.out.print("Desc: "); String d=sc.nextLine(); System.out.print("Prio(int): "); int p=sc.nextInt(); sc.nextLine(); agenda.add(new Tarea(d,p)); break; }
                            case 2: { System.out.println("Proxima: " + (agenda.peek()==null?"-":agenda.peek())); break; }
                            case 3: { System.out.println("Completada: " + (agenda.poll()==null?"-":agenda.poll())); break; }
                            case 4: { System.out.println("Pendientes:"); for (Tarea t: agenda) System.out.println(t); break; }
                            case 0: salir=true; break; default: System.out.println("Op invalida"); break;
                        }
                    }
                    break;
                }
                case 0: System.out.println("Saliendo Practico7..."); break;
                default : System.out.println("Opcion invalida"); break;
            }
        } while(opt!=0);
        sc.close();
    }
}
