import java.util.Scanner;

public class sortsort {

    // Função que verifica se um número é ímpar
    public static boolean isOdd(int x) {
        return x % 2 != 0;
    }

    // Função para comparar dois números de acordo com as regras
    public static boolean compare(int a, int b, int M) {
        int modA = a % M;
        int modB = b % M;

        // 1) Ordenar pelo valor do módulo
        if (modA != modB) {
            return modA > modB; // se modA maior, a deve ir depois de b
        }

        // 2) Se os módulos forem iguais, aplicar as outras regras
        boolean oddA = isOdd(a);
        boolean oddB = isOdd(b);

        // Ímpar vem antes de par
        if (oddA && !oddB) return false; // a vem antes de b
        if (!oddA && oddB) return true;  // b vem antes de a

        // Ambos ímpares → ordem decrescente
        if (oddA && oddB) {
            return a < b; // se a menor que b, então troca
        }

        // Ambos pares → ordem crescente
        if (!oddA && !oddB) {
            return a > b; // se a maior que b, então troca
        }

        return false;
    }

    public static void bubbleSort(int[] arr, int N, int M) {
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < N - i - 1; j++) {
                if (compare(arr[j], arr[j + 1], M)) {
                    // Troca manual
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            if (N == 0 && M == 0) {
                System.out.println("0 0");
                break;
            }

            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
            }

            // Ordenar com nosso bubble sort manual
            bubbleSort(arr, N, M);

            // Saída
            System.out.println(N + " " + M);
            for (int i = 0; i < N; i++) {
                System.out.println(arr[i]);
            }
        }

        sc.close();
    }
}
