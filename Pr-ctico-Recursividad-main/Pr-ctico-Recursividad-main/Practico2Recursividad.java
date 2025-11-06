import java.util.Arrays;
import java.util.HashMap;

public class Practico2Recursividad {

    // 🧮 Ejercicio 1 – Conteo de dígitos
    public static int contarDigitos(int n) {
        if (n < 10) return 1;
        return 1 + contarDigitos(n / 10);
    }

    // 🔄 Ejercicio 2 – Invertir una cadena
    public static String invertirCadena(String s) {
        if (s.length() <= 1) return s;
        return invertirCadena(s.substring(1)) + s.charAt(0);
    }

    // ➕ Ejercicio 3 – Suma y promedio de elementos de un arreglo
    public static int sumarArreglo(int[] arr, int index) {
        if (index == arr.length) return 0;
        return arr[index] + sumarArreglo(arr, index + 1);
    }

    public static double promedioArreglo(int[] arr) {
        return (double) sumarArreglo(arr, 0) / arr.length;
    }

    // 🔢 Ejercicio 4 – Máximo Común Divisor (Euclides)
    public static int mcd(int a, int b) {
        if (b == 0) return a;
        return mcd(b, a % b);
    }

    // 💻 Ejercicio 5 – Conversión binaria
    public static String aBinario(int n) {
        if (n == 0) return "0";
        if (n == 1) return "1";
        return aBinario(n / 2) + (n % 2);
    }

    // 🔁 Ejercicio 6 – Palíndromo
    public static boolean esPalindromo(String s) {
        if (s.length() <= 1) return true;
        if (s.charAt(0) != s.charAt(s.length() - 1)) return false;
        return esPalindromo(s.substring(1, s.length() - 1));
    }

    // 🧠 Ejercicio 7 – Fibonacci optimizado con memoización
    private static HashMap<Integer, Long> memo = new HashMap<>();

    public static long fibonacci(int n) {
        if (n <= 1) return n;
        if (memo.containsKey(n)) return memo.get(n);
        long resultado = fibonacci(n - 1) + fibonacci(n - 2);
        memo.put(n, resultado);
        return resultado;
    }

    // 🔍 Ejercicio 8 – Buscar en un arreglo
    public static boolean buscarEnArreglo(int[] arr, int index, int valor) {
        if (index == arr.length) return false;
        if (arr[index] == valor) return true;
        return buscarEnArreglo(arr, index + 1, valor);
    }

    // 🧪 MAIN PARA PRUEBAS
    public static void main(String[] args) {

        System.out.println("===== PRÁCTICO 2 - RECURSIVIDAD =====\n");

        // Ejercicio 1
        int numero = 12345;
        System.out.println("1️⃣ Conteo de dígitos de " + numero + " → " + contarDigitos(numero));

        // Ejercicio 2
        String texto = "recursivo";
        System.out.println("2️⃣ Invertir cadena \"" + texto + "\" → " + invertirCadena(texto));

        // Ejercicio 3
        int[] arreglo = {2, 4, 6, 8};
        System.out.println("3️⃣ Suma arreglo " + Arrays.toString(arreglo) + " → " + sumarArreglo(arreglo, 0));
        System.out.println("   Promedio → " + promedioArreglo(arreglo));

        // Ejercicio 4
        System.out.println("4️⃣ MCD(48, 18) → " + mcd(48, 18));

        // Ejercicio 5
        int binario = 13;
        System.out.println("5️⃣ Binario de " + binario + " → " + aBinario(binario));

        // Ejercicio 6
        String palabra = "neuquen";
        System.out.println("6️⃣ \"" + palabra + "\" es palíndromo → " + esPalindromo(palabra));

        // Ejercicio 7
        int nFib = 10;
        System.out.println("7️⃣ Fibonacci(" + nFib + ") → " + fibonacci(nFib));

        // Ejercicio 8
        int buscar = 7;
        System.out.println("8️⃣ Buscar " + buscar + " en " + Arrays.toString(arreglo) + " → " + buscarEnArreglo(arreglo, 0, buscar));

        System.out.println("\n✅ Fin de las pruebas.");
    }
}
