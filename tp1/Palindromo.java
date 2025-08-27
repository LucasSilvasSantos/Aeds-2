import java.util.Scanner;

public class Palindromo {

    // Verifica se a string é palíndromo comparando exatamente os caracteres
    public static boolean ehPalindromo(String s) {
        int i = 0;
        int j = s.length() - 1;

        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String linha = sc.nextLine();

            // Condição de parada: se a entrada for "FIM"
            if (linha.length() == 3 && 
                linha.charAt(0) == 'F' && 
                linha.charAt(1) == 'I' && 
                linha.charAt(2) == 'M') {
                break;
            }

            System.out.println(ehPalindromo(linha) ? "SIM" : "NAO");
        }

        sc.close();
    }
}
