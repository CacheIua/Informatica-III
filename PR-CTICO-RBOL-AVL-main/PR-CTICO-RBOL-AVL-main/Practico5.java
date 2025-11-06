import java.util.*;

class NodoAVL {
    int val, altura;
    NodoAVL izq, der;
    public NodoAVL(int v) { val = v; altura = 1; }
}

class AVL {
    public NodoAVL raiz;
    public int rotaciones = 0; // contador de rotaciones aplicadas

    private int altura(NodoAVL n) { return n == null ? 0 : n.altura; }
    private int fe(NodoAVL n) { return n == null ? 0 : altura(n.izq) - altura(n.der); }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izq;
        NodoAVL T2 = x.der;
        x.der = y;
        y.izq = T2;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        rotaciones++;
        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.der;
        NodoAVL T2 = y.izq;
        y.izq = x;
        x.der = T2;
        x.altura = Math.max(altura(x.izq), altura(x.der)) + 1;
        y.altura = Math.max(altura(y.izq), altura(y.der)) + 1;
        rotaciones++;
        return y;
    }

    public NodoAVL insertar(NodoAVL nodo, int clave) {
        if (nodo == null) return new NodoAVL(clave);
        if (clave < nodo.val) nodo.izq = insertar(nodo.izq, clave);
        else if (clave > nodo.val) nodo.der = insertar(nodo.der, clave);
        else return nodo; // duplicados ignorados

        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
        int balance = fe(nodo);

        // LL
        if (balance > 1 && clave < nodo.izq.val) return rotarDerecha(nodo);
        // RR
        if (balance < -1 && clave > nodo.der.val) return rotarIzquierda(nodo);
        // LR
        if (balance > 1 && clave > nodo.izq.val) {
            nodo.izq = rotarIzquierda(nodo.izq);
            return rotarDerecha(nodo);
        }
        // RL
        if (balance < -1 && clave < nodo.der.val) {
            nodo.der = rotarDerecha(nodo.der);
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

    public void insertar(int clave) { raiz = insertar(raiz, clave); }

    private NodoAVL minValueNode(NodoAVL node) {
        NodoAVL current = node;
        while (current.izq != null) current = current.izq;
        return current;
    }

    public NodoAVL eliminar(NodoAVL root, int clave) {
        if (root == null) return root;
        if (clave < root.val) root.izq = eliminar(root.izq, clave);
        else if (clave > root.val) root.der = eliminar(root.der, clave);
        else {
            if ((root.izq == null) || (root.der == null)) {
                NodoAVL temp = (root.izq != null) ? root.izq : root.der;
                if (temp == null) { root = null; }
                else root = temp;
            } else {
                NodoAVL temp = minValueNode(root.der);
                root.val = temp.val;
                root.der = eliminar(root.der, temp.val);
            }
        }
        if (root == null) return root;
        root.altura = Math.max(altura(root.izq), altura(root.der)) + 1;
        int balance = fe(root);
        // LL
        if (balance > 1 && fe(root.izq) >= 0) return rotarDerecha(root);
        // LR
        if (balance > 1 && fe(root.izq) < 0) {
            root.izq = rotarIzquierda(root.izq);
            return rotarDerecha(root);
        }
        // RR
        if (balance < -1 && fe(root.der) <= 0) return rotarIzquierda(root);
        // RL
        if (balance < -1 && fe(root.der) > 0) {
            root.der = rotarDerecha(root.der);
            return rotarIzquierda(root);
        }
        return root;
    }

    public void eliminar(int clave) { raiz = eliminar(raiz, clave); }

    public boolean esABB(NodoAVL node, int min, int max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return esABB(node.izq, min, node.val) && esABB(node.der, node.val, max);
    }

    public Object[] esAVL_Check(NodoAVL node) {
        // devuelve {boolean esAVL, int altura}
        if (node == null) return new Object[]{true, 0};
        Object[] iz = esAVL_Check(node.izq);
        Object[] dr = esAVL_Check(node.der);
        boolean esIz = (Boolean) iz[0];
        boolean esDr = (Boolean) dr[0];
        int hi = (Integer) iz[1];
        int hd = (Integer) dr[1];
        boolean esAVL = esIz && esDr && Math.abs(hi - hd) <= 1;
        int altura = Math.max(hi, hd) + 1;
        return new Object[]{esAVL, altura};
    }

    public void inorder(NodoAVL node) {
        if (node == null) return;
        inorder(node.izq);
        System.out.print(node.val + " ");
        inorder(node.der);
    }

    public void mostrarConAlturas(NodoAVL node) {
        if (node == null) return;
        mostrarConAlturas(node.izq);
        System.out.println("Valor: " + node.val + ", Altura: " + node.altura + ", FE: " + fe(node));
        mostrarConAlturas(node.der);
    }
}

public class Practico5 {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        AVL avl = new AVL();
        int opcion;
        do {
            System.out.println("\n--- PRACTICO 5: ARBOL AVL ---");
            System.out.println("1. Inserciones paso a paso (LL/RR)");
            System.out.println("2. Inserciones con rotación doble (LR/RL)");
            System.out.println("3. Secuencia ordenada y efecto peinar");
            System.out.println("4. Eliminación con rebalanceo");
            System.out.println("5. Comprobador esAVL()");
            System.out.println("6. Factor de equilibrio completo");
            System.out.println("7. Implementación rotación izquierda (mostrar)");
            System.out.println("8. Implementación rotación doble LR (mostrar)");
            System.out.println("9. Costos y altura (explicativo)");
            System.out.println("10. Secuencias estresantes y pruebas unitarias");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    AVL t = new AVL();
                    int[] seq = {30,20,10,40,50,60};
                    System.out.println("Insertando: " + Arrays.toString(seq));
                    for (int k : seq) {
                        t.insertar(k);
                        System.out.print("In-order: "); t.inorder(t.raiz); System.out.println();
                        t.mostrarConAlturas(t.raiz);
                        System.out.println("---");
                    }
                    break;
                case 2:
                    t = new AVL();
                    seq = new int[]{30,10,20,40,35,37};
                    System.out.println("Insertando: " + Arrays.toString(seq));
                    for (int k : seq) {
                        t.insertar(k);
                        System.out.print("In-order: "); t.inorder(t.raiz); System.out.println();
                        t.mostrarConAlturas(t.raiz);
                        System.out.println("---");
                    }
                    break;
                case 3:
                    t = new AVL();
                    seq = new int[]{5,10,15,20,25,30,35};
                    System.out.println("Insertando en ABB/AVL: " + Arrays.toString(seq));
                    for (int k : seq) t.insertar(k);
                    System.out.print("In-order (AVL): "); t.inorder(t.raiz); System.out.println();
                    t.mostrarConAlturas(t.raiz);
                    break;
                case 4:
                    t = new AVL();
                    seq = new int[]{50,30,70,20,40,60,80,65,75};
                    for (int k : seq) t.insertar(k);
                    System.out.println("Arbol inicial:"); t.mostrarConAlturas(t.raiz);
                    System.out.println("\nEliminando 20..."); t.eliminar(20); t.mostrarConAlturas(t.raiz);
                    System.out.println("\nEliminando 70..."); t.eliminar(70); t.mostrarConAlturas(t.raiz);
                    break;
                case 5:
                    t = new AVL();
                    int[] good = {20,10,30,5,15,25,35};
                    for (int k : good) t.insertar(k);
                    Object[] res = t.esAVL_Check(t.raiz);
                    System.out.println("Arbol valido? " + res[0] + ", Altura: " + res[1]);
                    // Construimos un arbol no-avl manualmente
                    NodoAVL bad = new NodoAVL(10);
                    bad.izq = new NodoAVL(5);
                    bad.izq.izq = new NodoAVL(2);
                    Object[] res2 = t.esAVL_Check(bad);
                    System.out.println("Arbol no-avl valido? " + res2[0] + ", Altura: " + res2[1]);
                    System.out.println("Comprueba tambien propiedad ABB: " + t.esABB(t.raiz, Integer.MIN_VALUE, Integer.MAX_VALUE));
                    break;
                case 6:
                    t = new AVL();
                    seq = new int[]{10,100,20,80,40,70};
                    for (int k : seq) t.insertar(k);
                    System.out.println("Lista final (valor, altura, FE):");
                    t.mostrarConAlturas(t.raiz);
                    break;
                case 7:
                    System.out.println("Se demuestra rotación izquierda interna usando secuencia con caso RR...");
                    t = new AVL();
                    seq = new int[]{10,20,30};
                    for (int k : seq) t.insertar(k);
                    System.out.println("Antes (in-order): "); t.inorder(t.raiz); System.out.println();
                    System.out.println("Alturas/FE:"); t.mostrarConAlturas(t.raiz);
                    break;
                case 8:
                    System.out.println("Se demuestra rotación doble LR con la secuencia del ejercicio 2...");
                    t = new AVL();
                    seq = new int[]{30,10,20,40,35,37};
                    for (int k : seq) t.insertar(k);
                    System.out.println("Resultado (in-order): "); t.inorder(t.raiz); System.out.println();
                    t.mostrarConAlturas(t.raiz);
                    break;
                case 9:
                    System.out.println("Explicación breve:");
                    System.out.println("La altura del AVL es O(log n) porque se mantiene balanceado: cada rotación evita cadenas largas.");
                    System.out.println("Esto garantiza búsqueda/insertar/eliminar en O(log n). En ABB no balanceado el peor caso es O(n). Rojinegros ofrecen balance amortiguado con diferencias en reglas.");
                    break;
                case 10:
                    System.out.println("Secuencias de prueba: creciente, decreciente, pseudoaleatoria");
                    int[][] casos = { generateSequence(20, true), generateSequence(20, false), generatePseudoRandom(20) };
                    String[] nombres = {"Creciente","Decreciente","Pseudoaleatoria"};
                    for (int i = 0; i < casos.length; i++) {
                        t = new AVL();
                        for (int k : casos[i]) t.insertar(k);
                        Object[] ok = t.esAVL_Check(t.raiz);
                        System.out.println(nombres[i] + ": esAVL=" + ok[0] + ", altura=" + ok[1] + ", rotaciones=" + t.rotaciones);
                        // In-order check
                        System.out.print("In-order: "); t.inorder(t.raiz); System.out.println();
                    }
                    break;
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

    private static int[] generateSequence(int n, boolean asc) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = asc ? i+1 : n - i;
        return arr;
    }

    private static int[] generatePseudoRandom(int n) {
        int[] arr = new int[n];
        Random r = new Random(12345); // semilla fija para reproducibilidad
        for (int i = 0; i < n; i++) arr[i] = r.nextInt(100);
        return arr;
    }
}
