import java.util.Scanner;

public class Anagrama {
    
    // Converte um caractere para minúsculo
    static char paraMinusculo(char c) {
        if (c >= 'A' && c <= 'Z') {
            c = (char) (c + 32);
        }
        return c; // Adicionado o return
    }
    
    // Converte uma string inteira para minúsculas
    static String stringParaMinusculo(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = paraMinusculo(chars[i]);
        }
        return new String(chars);
    }
    
    // Divide a string em duas partes com base no hífen (substitui split)
    static String[] dividirPorHifen(String linha) {
        String[] resultado = new String[2];
        int indiceHifen = -1;
        
        // Procura o hífen
        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == '-') {
                indiceHifen = i;
                break;
            }
        }
        
        // Se não encontrar hífen, retorna array com null para indicar erro
        if (indiceHifen == -1) {
            return new String[] { null, null };
        }
        
        // Extrai as duas partes
        resultado[0] = linha.substring(0, indiceHifen).trim();
        resultado[1] = linha.substring(indiceHifen + 1).trim();
        
        return resultado;
    }
    
    // Verifica se duas strings são anagramas
    static boolean saoAnagramas(String palavra1, String palavra2) {
        if (palavra1.length() != palavra2.length()) {
            return false;
        }
        
        int[] frequencia1 = new int[256];
        int[] frequencia2 = new int[256];
        
        // Conta frequência dos caracteres
        for (int i = 0; i < palavra1.length(); i++) {
            frequencia1[palavra1.charAt(i)]++;
            frequencia2[palavra2.charAt(i)]++;
        }
        
        // Compara as frequências
        for (int i = 0; i < 256; i++) {
            if (frequencia1[i] != frequencia2[i]) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            
            // Verifica se é o fim da entrada
            if (linha.equals("FIM")) {
                break;
            }
            
            // Divide a linha em duas palavras
            String[] partes = dividirPorHifen(linha);
            
            // Verifica se a divisão foi válida
            if (partes[0] == null || partes[1] == null) {
                System.out.println("N\u00C3O");
                continue;
            }
            
            // Converte para minúsculas
            String palavra1 = stringParaMinusculo(partes[0]);
            String palavra2 = stringParaMinusculo(partes[1]);
            
            // Verifica se são anagramas e imprime o resultado
            if (saoAnagramas(palavra1, palavra2)) {
                System.out.println("SIM");
            } else {
                System.out.println("N\u00C3O");
            }
        }
        
        sc.close();
    }
}