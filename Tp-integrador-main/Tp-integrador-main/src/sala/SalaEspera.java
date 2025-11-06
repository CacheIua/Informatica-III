package sala;

import modelo.Paciente;

/**
 * Cola circular para sala de espera con overflow (si se quiere pisar al más antiguo, usar otra API).
 */
public class SalaEspera {
    private Paciente[] arr;
    private int front, rear, size, capacity;

    public SalaEspera(int capacity){
        this.capacity = capacity;
        this.arr = new Paciente[capacity];
        this.front = 0; this.rear = 0; this.size = 0;
    }

    public void llega(Paciente p){
        if (size==capacity){
            // overflow: overwrite oldest
            front = (front+1)%capacity; size--;
        }
        arr[rear] = p; rear = (rear+1)%capacity; size++;
    }

    public Paciente atiende(){
        if (size==0) return null;
        Paciente r = arr[front]; front=(front+1)%capacity; size--;
        return r;
    }

    public Paciente peek(){ return size==0?null:arr[front]; }
    public int size(){ return size; }
    public boolean vacia(){ return size==0; }
}
