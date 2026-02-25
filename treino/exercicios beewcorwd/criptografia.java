import java.util.Scanner;

public class Criptografia {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.nextLine(); // consumir ENTER

        for (int i = 0; i < N; i++) {

            String linha = sc.nextLine();
            String primeiraPassada = "";

            //  primeira etapa - para cada caractere da string, se for letra, substitua pelo caractere 3 posições à frente na tabela ASCII
            for (int j = 0; j < linha.length(); j++) {

                char c = linha.charAt(j);

                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                    c = (char)(c + 3);
                }

                primeiraPassada += c; // armazendo o resultado da primeira etapa da criptografia  em uma string para ser usada na segunda etapa da criptografia
            }

            //  segunda etapa: inverte a string da primeira etapa da criptografia
            String invertida = "";
            for (int j = primeiraPassada.length() - 1; j >= 0; j--) {  // percorro a string da primeira etapa da criptografia de trás para frente para inverter a string 
                invertida += primeiraPassada.charAt(j); // aqui eu vou armazenando o resultao da segunda etapa caractere por caractere em uma string 
            }

            //  Tericeira etapa - para os caracteres da segunda etapa da criptografia, a partir da metade da string, subtraia 1 do valor ASCII de cada caractere
            int metade = invertida.length() / 2;
            String resultadoFinal = "";

            for (int j = 0; j < invertida.length(); j++) {

                char c = invertida.charAt(j);

                if (j >= metade) {
                    c = (char)(c - 1);
                }

                resultadoFinal += c;
            }

            System.out.println(resultadoFinal);
        }

        sc.close();
    }
}