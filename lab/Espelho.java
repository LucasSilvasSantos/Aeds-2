import java.util.Scanner;

public class Espelho {

    public static void espelho(int primeiro, int ultimo) {
        char[] buffer = new char[10000]; // espaço grande o bastante
        int pos = 0;

        // Parte crescente → armazena no buffer
        for (int i = primeiro; i <= ultimo; i++) {
            String num = Integer.toString(i);
            for (int j = 0; j < num.length(); j++) {
                buffer[pos++] = num.charAt(j);
                System.out.print(num.charAt(j)); // imprime direto também
            }
        }

        // Parte espelhada → imprime o buffer de trás pra frente
        for (int k = pos - 1; k >= 0; k--) {
            System.out.print(buffer[k]);
        }

        System.out.println(); // quebra de linha
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            int primeiro = sc.nextInt();
            int ultimo = sc.nextInt();
            espelho(primeiro, ultimo);
        }

        sc.close();
    }
}
