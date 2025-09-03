import java.util.Scanner;
public class Algebraboleana {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while(sc.hasNext()) {
            // Lê o número de entradas
            int n = sc.nextInt();
            
            // Lê os valores binários
            boolean[] valores = new boolean[n];
            for(int i = 0; i < n; i++) {
                valores[i] = sc.nextInt() == 1;
            }
            
            // Lê a expressão (resto da linha)
            sc.nextLine(); // Limpa o buffer
            String expressao = sc.nextLine();
            
            // Avalia a expressão
            boolean resultado = avaliarExpressao(expressao, valores);
            
            // Imprime resultado
            System.out.println(resultado ? "SIM" : "NAO");
        }
        sc.close();
    }
    
    public static boolean avaliarExpressao(String expressao, boolean[] valores) {
        // Substitui as letras pelos valores
        for(int i = 0; i < valores.length; i++) {
            char letra = (char)('A' + i);
            expressao = expressao.replace(String.valueOf(letra), 
                                       String.valueOf(valores[i]));
        }
        
        // Remove espaços e parênteses desnecessários
        expressao = expressao.replaceAll("\\s+", "");
        
        // Avalia NOT
        while(expressao.contains("not")) {
            int index = expressao.indexOf("not");
            boolean valor = Boolean.parseBoolean(
                expressao.substring(index + 3, index + 8));
            expressao = expressao.substring(0, index) + (!valor) + 
                       expressao.substring(index + 8);
        }
        
        // Avalia AND
        while(expressao.contains("and")) {
            int index = expressao.indexOf("and");
            boolean valor1 = Boolean.parseBoolean(
                expressao.substring(index - 5, index));
            boolean valor2 = Boolean.parseBoolean(
                expressao.substring(index + 3, index + 8));
            expressao = expressao.substring(0, index - 5) + 
                       (valor1 && valor2) + 
                       expressao.substring(index + 8);
        }
        
        // Avalia OR
        while(expressao.contains("or")) {
            int index = expressao.indexOf("or");
            boolean valor1 = Boolean.parseBoolean(
                expressao.substring(index - 5, index));
            boolean valor2 = Boolean.parseBoolean(
                expressao.substring(index + 2, index + 7));
            expressao = expressao.substring(0, index - 5) + 
                       (valor1 || valor2) + 
                       expressao.substring(index + 7);
        }
        
        return Boolean.parseBoolean(expressao);
    }
}