import java.util.Scanner;

public class OrdenamientoYBusqueda {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int n = leerEntero("Digite el tamaño del arreglo: ", 1, Integer.MAX_VALUE);
        String[] palabras = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Palabra " + (i + 1) + ": ");
            palabras[i] = sc.nextLine().trim();
        }

        System.out.println("\nElija el algoritmo de ordenamiento:");
        System.out.println("1. Selección");
        System.out.println("2. Inserción");
        System.out.println("3. Burbuja");
        System.out.println("4. Mezcla (Merge Sort)");
        System.out.println("5. Rápido (Quick Sort)");
        int opcion = leerEntero("Opción: ", 1, 5);

        switch (opcion) {
            case 1: seleccion(palabras); break;
            case 2: insercion(palabras); break;
            case 3: burbuja(palabras); break;
            case 4: mezcla(palabras, 0, palabras.length - 1); break;
            case 5: rapido(palabras, 0, palabras.length - 1); break;
        }

        System.out.println("\nArreglo ordenado:");
        imprimir(palabras);

        String continuar;
        do {
            System.out.print("\nDigite la palabra a buscar: ");
            String buscada = sc.nextLine().trim();
            int pos = busquedaBinaria(palabras, buscada);
            if (pos >= 0) {
                System.out.println("La palabra \"" + buscada + "\" se encontró en la posición " + pos + ".");
            } else {
                System.out.println("La palabra \"" + buscada + "\" no está en el arreglo.");
            }
            System.out.print("¿Desea buscar otra palabra? (s/n): ");
            continuar = sc.nextLine().trim();
        } while (continuar.equalsIgnoreCase("s"));

        System.out.println("Fin del programa.");
    }

    static void seleccion(String[] a) {
        for (int i = 0; i < a.length - 1; i++) {

            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (comparar(a[j], a[min]) < 0) {
                    min = j;
                }
            }

            intercambiar(a, i, min);
        }
    }

    static void insercion(String[] a) {
        for (int i = 1; i < a.length; i++) {

            String clave = a[i];
            int j = i - 1;

            while (j >= 0 && comparar(a[j], clave) > 0) {
                a[j + 1] = a[j];
                j--;
            }

            a[j + 1] = clave;
        }
    }

    static void burbuja(String[] a) {
        boolean huboCambio;
        for (int i = 0; i < a.length - 1; i++) {
            huboCambio = false;

            for (int j = 0; j < a.length - 1 - i; j++) {
                if (comparar(a[j], a[j + 1]) > 0) {
                    intercambiar(a, j, j + 1);
                    huboCambio = true;
                }
            }

            if (!huboCambio) break;
        }
    }

    static void mezcla(String[] a, int izq, int der) {
        if (izq < der) {

            int medio = (izq + der) / 2;

            mezcla(a, izq, medio);
            mezcla(a, medio + 1, der);

            mezclar(a, izq, medio, der);
        }
    }

    static void mezclar(String[] a, int izq, int medio, int der) {
        int tamIzq = medio - izq + 1;
        int tamDer = der - medio;
        String[] L = new String[tamIzq];
        String[] R = new String[tamDer];
        for (int i = 0; i < tamIzq; i++) L[i] = a[izq + i];
        for (int j = 0; j < tamDer; j++) R[j] = a[medio + 1 + j];

        int i = 0, j = 0, k = izq;

        while (i < tamIzq && j < tamDer) {
            if (comparar(L[i], R[j]) <= 0) {
                a[k++] = L[i++];
            } else {
                a[k++] = R[j++];
            }
        }

        while (i < tamIzq) a[k++] = L[i++];
        while (j < tamDer) a[k++] = R[j++];
    }

    static void rapido(String[] a, int bajo, int alto) {
        if (bajo < alto) {

            int p = particion(a, bajo, alto);

            rapido(a, bajo, p - 1);
            rapido(a, p + 1, alto);
        }
    }

    static int particion(String[] a, int bajo, int alto) {
        String pivote = a[alto];
        int i = bajo - 1;
        for (int j = bajo; j < alto; j++) {
            if (comparar(a[j], pivote) < 0) {
                i++;
                intercambiar(a, i, j);
            }
        }
        intercambiar(a, i + 1, alto);
        return i + 1;
    }

    static int busquedaBinaria(String[] a, String buscada) {
        int bajo = 0, alto = a.length - 1;
        while (bajo <= alto) {
            int medio = (bajo + alto) / 2;
            int cmp = comparar(buscada, a[medio]);
            if (cmp == 0) {
                return medio;
            } else if (cmp < 0) {
                alto = medio - 1;
            } else {
                bajo = medio + 1;
            }
        }
        return -1;
    }

    static int comparar(String x, String y) {
        return x.compareToIgnoreCase(y);
    }

    static void intercambiar(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    static void imprimir(String[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(i + ": " + a[i]);
        }
    }

    static int leerEntero(String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException e) {

            }
            System.out.println("Valor inválido, intente de nuevo.");
        }
    }
}
