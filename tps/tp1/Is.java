import java.util.Scanner;

public class Is {

    public static boolean ehVogal(char c) {
       if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') { 
         return true; 
         }
       return false;
    }

    public static boolean ehConsoante(char c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) && !ehVogal(c);
    }

    public static boolean ehInteiro(char c) {
        return (c >= '0' && c <= '9');
    }

    public static boolean ehRealChar(char c) {
        return (c >= '0' && c <= '9') || c == '.' || c == ',';
    }

    public static boolean somenteVogais(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!ehVogal(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean somenteConsoantes(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!ehConsoante(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean somenteInteiro(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!ehInteiro(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean somenteReal(String word) {
        int separador = 0;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (c == '.' || c == ',') {
                separador++;
                if (separador > 1) return false;
            } else if (!ehInteiro(c)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            String linha = sc.nextLine();

            if (linha.equals("FIM")) break;

            boolean x1 = somenteVogais(linha);
            boolean x2 = somenteConsoantes(linha);
            boolean x3 = somenteInteiro(linha);
            boolean x4 = somenteReal(linha);

            System.out.printf("%s %s %s %s\n",
                    x1 ? "SIM" : "NAO",
                    x2 ? "SIM" : "NAO",
                    x3 ? "SIM" : "NAO",
                    x4 ? "SIM" : "NAO");
        }

        sc.close();
    }
}