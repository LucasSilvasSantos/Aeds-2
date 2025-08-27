import java.util.*;

public class Aleatoria {

    public static String Alterar(String palavra, Random gerador) {
        char letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        char letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));

        char[] aleatorio = new char[palavra.length()];
       
        for(int i = 0; i < palavra.length(); i++){
            aleatorio[i] = palavra.charAt(i);
        }

        for(int j = 0; j < aleatorio.length; j++){
            if(aleatorio[j] == letra1){
                aleatorio[j] = letra2;
            }
        }

        return new String(aleatorio);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random gerador = new Random();
        gerador.setSeed(4);
        while (sc.hasNextLine()) {
            String entrada = sc.nextLine();
            if (entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M') {
                break;
            }
            System.out.println(Alterar(entrada, gerador));
        }
        sc.close();
    }
}