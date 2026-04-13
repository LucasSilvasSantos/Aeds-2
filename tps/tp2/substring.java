import java.util.Scanner;

public class substring {

    public static int longestSubstring(String input) {
        int inicio = 0;       
        int maxTamanho = 0;   

        for (int fim = 0; fim < input.length(); fim++) {
            char atual = input.charAt(fim);

            
            for (int j = inicio; j < fim; j++) {
                if (input.charAt(j) == atual) {
                   
                    inicio = j + 1;
                    break;
                }
            }

            
            int tamanhoAtual = fim - inicio + 1;
            if (tamanhoAtual > maxTamanho) {
                maxTamanho = tamanhoAtual;
            }
        }

        return maxTamanho;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        System.out.println(longestSubstring(input));

        sc.close();
    }
}


