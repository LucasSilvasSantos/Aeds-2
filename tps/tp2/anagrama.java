import java.util.Scanner;

public class Anagrama {
    
    // metodo manual = tolowercase()
    static char paraMinusculo(char c) {
        if (c >= 'A' && c <= 'Z') { // Verifica se o caractere  maisculo está entre A e Z usando a tabela asc
            c = (char) (c + 32); // caso sim converte para minuscula que na tabela asc estão 32 posições a frente das minusculas 
        }
        return c; //retornando o caractere convertido
    }
    
    // Converte uma string inteira para minúsculas
    static String stringParaMinusculo(String s) { // uso como entrada uma string s 
        char[] chars = s.toCharArray(); // usando o toCharArray para modificar cada caractere individualmente       
        for (int i = 0; i < chars.length; i++) { // pecorrendo cada caractere da string ate  o tamanho dela 
            chars[i] = paraMinusculo(chars[i]); // chamando o metodo paraMinusculo para converter cada caractere para minusculo
        }
        return new String(chars);  // retornando a nova string com os caracteres convertidos
    }
    
    // Divide a string em duas partes com base no hífen (substitui split)
    static String[] dividirPorHifen(String linha) {
        String[] resultado = new String[2]; // criei um array de string com 2 posições para armazenar as duas partes
        int indiceHifen = -1; // inicializando o indice com -1 para indicar que ainda não encontrou o hifen 
        
        // Procura o hífen
        for (int i = 0; i < linha.length(); i++) { //percorrendo  a string atraves de um loop de acordo com o tamanho dela 
            if (linha.charAt(i) == '-') { //procura o caractere hifen ao encontrar salva a posição do array e para o loop
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
        if (palavra1.length() != palavra2.length()) { // se o tamanho das palavras forem diferentes não são anagramas
            return false;
        }
        
        int[] frequencia1 = new int[256]; // arrays para contar a frequência de cada caractere (assumindo ASCII estendido)
        int[] frequencia2 = new int[256];
        
        // Conta frequência dos caracteres
        for (int i = 0; i < palavra1.length(); i++) {
            frequencia1[palavra1.charAt(i)]++; //
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
            if (linha.charAt(0)== 'F' && linha.charAt(1) == 'I' && linha.charAt(2) == 'M') {
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
            String palavra1 = stringParaMinusculo(partes[0]); // chama  o metodo para converter para minusculo
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