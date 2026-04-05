import java.util.Scanner;

public class eh {
    public static class Fila {
        private char[] array;
        private int primeiro;
        private int ultimo;

        public Fila(int tamanho) {
            array = new char[tamanho];
            primeiro = 0;
            ultimo = 0;
        }

        public void enqueue(char c) {
            if (ultimo < array.length) {
                array[ultimo++] = c;
            }
        }

        boolean isPalindromo() {
            int inicio = primeiro;
            int fim = ultimo - 1;
            while (inicio < fim) {
                if (array[inicio] != array[fim]) {
                    return false;
                }
                inicio++;
                fim--;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        Fila f = new Fila(s.length());
        for (int i = 0; i < s.length(); i++) {
            f.enqueue(s.charAt(i));
        }
        System.out.println(f.isPalindromo() ? "SIM" : "NAO");
        scanner.close();
    }
}