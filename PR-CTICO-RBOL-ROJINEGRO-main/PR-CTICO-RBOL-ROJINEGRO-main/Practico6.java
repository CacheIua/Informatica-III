import java.util.Scanner;

enum Color { RED, BLACK }

class RBNode<K extends Comparable<K>, V> {
    K key; V val;
    Color color;
    RBNode<K,V> left, right, parent;
    public RBNode(K k, V v, Color c) { key=k; val=v; color=c; left=right=parent=null; }
}

class RBTree<K extends Comparable<K>, V> {
    public final RBNode<K,V> NIL;
    public RBNode<K,V> root;

    public RBTree() {
        NIL = new RBNode<>(null, null, Color.BLACK);
        NIL.left = NIL.right = NIL.parent = NIL;
        root = NIL;
    }

    // rotateLeft
    public void rotateLeft(RBNode<K,V> x) {
        RBNode<K,V> y = x.right;
        x.right = y.left;
        if (y.left != NIL) y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == NIL) root = y;
        else if (x == x.parent.left) x.parent.left = y;
        else x.parent.right = y;
        y.left = x; x.parent = y;
    }

    // rotateRight
    public void rotateRight(RBNode<K,V> y) {
        RBNode<K,V> x = y.left;
        y.left = x.right;
        if (x.right != NIL) x.right.parent = y;
        x.parent = y.parent;
        if (y.parent == NIL) root = x;
        else if (y == y.parent.left) y.parent.left = x;
        else y.parent.right = x;
        x.right = y; y.parent = x;
    }

    // insert as BST (red node, children point to NIL)
    public RBNode<K,V> insertBST(K key, V val) {
        RBNode<K,V> z = new RBNode<>(key, val, Color.RED);
        z.left = z.right = z.parent = NIL;
        RBNode<K,V> y = NIL;
        RBNode<K,V> x = root;
        while (x != NIL) { y = x; if (z.key.compareTo(x.key) < 0) x = x.left; else x = x.right; }
        z.parent = y;
        if (y == NIL) root = z;
        else if (z.key.compareTo(y.key) < 0) y.left = z; else y.right = z;
        return z;
    }

    // classify case for fixInsert
    public String classifyCase(RBNode<K,V> z) {
        RBNode<K,V> p = z.parent, g = p.parent, tio = (p == g.left) ? g.right : g.left;
        if (p.color == Color.RED && tio != NIL && tio.color == Color.RED) return "TIO_ROJO";
        if (p == g.left && z == p.left) return "LL";
        if (p == g.right && z == p.right) return "RR";
        if (p == g.left && z == p.right) return "LR";
        if (p == g.right && z == p.left) return "RL";
        return "DESCONOCIDO";
    }

    // fixInsert (standard RB fix)
    public void fixInsert(RBNode<K,V> z) {
        while (z.parent.color == Color.RED) {
            RBNode<K,V> p = z.parent; RBNode<K,V> g = p.parent;
            RBNode<K,V> tio = (p == g.left) ? g.right : g.left;
            if (tio != NIL && tio.color == Color.RED) {
                // tio rojo: recolorear
                p.color = Color.BLACK; tio.color = Color.BLACK; g.color = Color.RED; z = g; // subir
            } else {
                if (p == g.left) {
                    if (z == p.right) { z = p; rotateLeft(z); p = z.parent; g = p.parent; }
                    // LL
                    p.color = Color.BLACK; g.color = Color.RED; rotateRight(g);
                } else {
                    if (z == p.left) { z = p; rotateRight(z); p = z.parent; g = p.parent; }
                    // RR
                    p.color = Color.BLACK; g.color = Color.RED; rotateLeft(g);
                }
            }
            if (z == root) break;
        }
        root.color = Color.BLACK;
    }

    public void insert(K key, V val) {
        RBNode<K,V> z = insertBST(key, val);
        fixInsert(z);
    }

    // successor
    public RBNode<K,V> successor(RBNode<K,V> x) {
        if (x.right != NIL) { RBNode<K,V> cur = x.right; while (cur.left != NIL) cur = cur.left; return cur; }
        RBNode<K,V> y = x.parent;
        while (y != NIL && x == y.right) { x = y; y = y.parent; }
        return y == NIL ? null : y;
    }

    // predecessor
    public RBNode<K,V> predecessor(RBNode<K,V> x) {
        if (x.left != NIL) { RBNode<K,V> cur = x.left; while (cur.right != NIL) cur = cur.right; return cur; }
        RBNode<K,V> y = x.parent;
        while (y != NIL && x == y.left) { x = y; y = y.parent; }
        return y == NIL ? null : y;
    }

    // range query [a,b]
    public void rangeQuery(K a, K b) { rangeQuery(root, a, b); System.out.println(); }
    private void rangeQuery(RBNode<K,V> node, K a, K b) {
        if (node == NIL) return;
        if (node.key.compareTo(a) > 0) rangeQuery(node.left, a, b);
        if (node.key.compareTo(a) >= 0 && node.key.compareTo(b) <= 0) System.out.print(node.key + " ");
        if (node.key.compareTo(b) < 0) rangeQuery(node.right, a, b);
    }

    // vérificadores
    public boolean raizNegra() { return root == NIL || root.color == Color.BLACK; }
    public boolean sinRojoRojo() { return sinRojoRojo(root); }
    private boolean sinRojoRojo(RBNode<K,V> n) {
        if (n == NIL) return true;
        if (n.color == Color.RED) {
            if (n.left.color == Color.RED || n.right.color == Color.RED) return false;
        }
        return sinRojoRojo(n.left) && sinRojoRojo(n.right);
    }
    // black height check, returns -1 if invalid
    public int alturaNegra() { return alturaNegra(root); }
    private int alturaNegra(RBNode<K,V> n) {
        if (n == NIL) return 0;
        int l = alturaNegra(n.left);
        int r = alturaNegra(n.right);
        if (l == -1 || r == -1 || l != r) return -1;
        return l + (n.color == Color.BLACK ? 1 : 0);
    }

    // in-order print
    public void inorder() { inorder(root); System.out.println(); }
    private void inorder(RBNode<K,V> n) { if (n==NIL) return; inorder(n.left); System.out.print(n.key + "(" + n.color + ") "); inorder(n.right); }

    // find node by key
    public RBNode<K,V> find(K key) { RBNode<K,V> cur = root; while (cur!=NIL) { int cmp = key.compareTo(cur.key); if (cmp==0) return cur; cur = cmp<0?cur.left:cur.right; } return null; }
}

public class Practico6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RBTree<Integer,String> tree = new RBTree<>();
        int opt;

        do {
            System.out.println("\n--- PRACTICO 6: ARBOL ROJINEGRO ---");
            System.out.println("1. Crear nodo (insert BST + fix)");
            System.out.println("2. Rotación izquierda (demo)");
            System.out.println("3. Rotación derecha (demo)");
            System.out.println("4. Insert como ABB (sin fix)");
            System.out.println("5. Clasificador de caso para un nodo");
            System.out.println("6. Recoloreo por tío rojo (demo en inserción)");
            System.out.println("7. Successor / Predecessor");
            System.out.println("8. Consulta por rango [a,b]");
            System.out.println("9. Verificadores invariantes");
            System.out.println("0. Salir");
            System.out.print("Elija opción: ");
            opt = sc.nextInt();
            sc.nextLine();

            switch (opt) {
                case 1: {
                    System.out.print("Clave(int): ");
                    int k = sc.nextInt(); sc.nextLine();
                    System.out.print("Valor(str): ");
                    String v = sc.nextLine();
                    tree.insert(k, v);
                    System.out.println("Insertado y balanceado. In-order:");
                    tree.inorder();
                    break;
                }
                case 2: {
                    System.out.println("Demo rotación izquierda: construiremos 10 <- 20 <- 30 y rotamos izquierda en root...");
                    RBTree<Integer,String> t = new RBTree<>();
                    t.insert(10, "A"); t.insert(20,"B"); t.insert(30,"C");
                    System.out.println("Antes: "); t.inorder();
                    if (t.root != t.NIL) {
                        t.rotateLeft(t.root);
                        System.out.println("Después rotLeft(root):");
                        t.inorder();
                    }
                    break;
                }
                case 3: {
                    System.out.println("Demo rotación derecha: parecido al caso 2 (se construye y se rota)");
                    RBTree<Integer,String> t = new RBTree<>();
                    t.insert(30,"C"); t.insert(20,"B"); t.insert(10,"A");
                    System.out.println("Antes: "); t.inorder();
                    if (t.root != t.NIL) {
                        t.rotateRight(t.root);
                        System.out.println("Después rotRight(root):");
                        t.inorder();
                    }
                    break;
                }
                case 4: {
                    System.out.print("Clave(int): ");
                    int k = sc.nextInt(); sc.nextLine();
                    System.out.print("Valor(str): ");
                    String v = sc.nextLine();
                    tree.insertBST(k, v);
                    System.out.println("Insertado como BST (sin fix). In-order:");
                    tree.inorder();
                    break;
                }
                case 5: {
                    System.out.print("Ingrese clave existente para clasificar: ");
                    int k = sc.nextInt(); sc.nextLine();
                    RBNode<Integer,String> n = tree.find(k);
                    if (n == null) System.out.println("Nodo no encontrado.");
                    else System.out.println("Caso: " + tree.classifyCase(n));
                    break;
                }
                case 6: {
                    System.out.println("Inserciones que muestran recoloreo (tío rojo):");
                    RBTree<Integer,String> t = new RBTree<>();
                    int[] seq = {10,5,1,7,40,50};
                    for (int x : seq) {
                        t.insert(x, "v"+x);
                        System.out.print("In-order: ");
                        t.inorder();
                        System.out.println();
                    }
                    break;
                }
                case 7: {
                    System.out.print("Clave para buscar successor/predecessor: ");
                    int k = sc.nextInt(); sc.nextLine();
                    RBNode<Integer,String> n = tree.find(k);
                    if (n == null) System.out.println("No existe.");
                    else {
                        RBNode<Integer,String> s = tree.successor(n);
                        RBNode<Integer,String> p = tree.predecessor(n);
                        System.out.println("Successor: " + (s==null ? "null" : s.key));
                        System.out.println("Predecessor: " + (p==null ? "null" : p.key));
                    }
                    break;
                }
                case 8: {
                    System.out.print("Rango a (int): ");
                    int a = sc.nextInt();
                    System.out.print(" b: ");
                    int b = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Claves en rango:");
                    tree.rangeQuery(a, b);
                    break;
                }
                case 9: {
                    System.out.println("Raiz negra? " + tree.raizNegra());
                    System.out.println("Sin rojo-rojo? " + tree.sinRojoRojo());
                    System.out.println("Altura negra (si -1 -> inválida): " + tree.alturaNegra());
                    break;
                }
                case 0: {
                    System.out.println("Saliendo...");
                    break;
                }
                default: {
                    System.out.println("Opción inválida.");
                    break;
                }
            }
        } while (opt != 0);

        sc.close();
    }
}