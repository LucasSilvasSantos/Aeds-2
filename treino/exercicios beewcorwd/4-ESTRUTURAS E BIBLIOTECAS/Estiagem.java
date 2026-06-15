import java.io.IOException;

public class Estiagem {

    static int lerInt() throws IOException {
        int c;
        while ((c = System.in.read()) < '0' || c > '9'); // le apenas um caractere, ignorando tudo que não for dígito
        int numero = c - '0'; // converte o caractere para o valor numerico correspondente 
        while ((c = System.in.read()) >= '0' && c <= '9') {  // le os próximos caracteres enquanto forem dígitos, construindo o número
            numero = numero * 10 + (c - '0'); // converte o caractere para número e adiciona à direita do número atual
        }
        return numero; // retorna o número inteiro completo lido da entrada
    }

    static void insertionSort(int[] moradores, int[] consumo, int n) {
        for (int i = 1; i < n; i++) { // faço um for para percorrer o array a partir do segundo elemento
            int chaveMoradores = moradores[i]; // guardo o valor do número de moradores atual como chave
            int chaveConsumo   = consumo[i]; // guardo o valor do consumo atual como chave
            int j = i - 1; // exemplo de j iniciando na posição anterior a i
            while (j >= 0 && consumo[j] > chaveConsumo) { // enquanto j for maior ou igual a 0 e o consumo na posição j for maior que a chave de consumo
                moradores[j + 1] = moradores[j]; // movo o número de moradores da posição j para a posição j + 1 (deslocamento para a direita)
                consumo[j + 1]   = consumo[j];  // movo o consumo da posição j para a posição j + 1 (deslocamento para a direita)
                j--;
            }
            moradores[j + 1] = chaveMoradores;
            consumo[j + 1]   = chaveConsumo;
        }
    }

    public static void main(String[] args) throws IOException {

        StringBuilder sb = new StringBuilder();
        int cidade = 0;
        int n;

        while ((n = lerInt()) != 0) {  // le o numero de  e o loop so continua se n for diferente de 0, ou seja, enquanto houver dados para processar
            cidade++; // incremento o contador de cidades para cada nova cidade processada

            int[] moradores = new int[n]; // crio um array para armazenar o número de moradores de cada residência
            int[] consumo   = new int[n]; // crio um array para armazenar o consumo per capita de cada residência (consumo total dividido pelo número de moradores)

            long totalMoradores = 0;
            long totalConsumo   = 0;

            for (int i = 0; i < n; i++) {
                int x = lerInt(); // le o número de moradores da residência atual
                int y = lerInt(); 
                moradores[i] = x;  // armazena o numero de moradores no array
                consumo[i]   = y / x; // calcula o consumo per capita dividindo o consumo total pelo número de moradores e armazena no array
                totalMoradores += x; // acumula o total de moradores para calcular a média posteriormente
                totalConsumo   += y; // acumula o total de consumo para calcular a média posteriormente
            }

            insertionSort(moradores, consumo, n); // ordena os arrays de moradores e consumo usando o método de ordenação por inserção, garantindo que o consumo per capita esteja em ordem crescente

            if (cidade > 1) sb.append("\n"); // adiciona uma linha em branco entre as saídas de diferentes cidades, mas não antes da primeira cidade

            sb.append("Cidade# ").append(cidade).append(":\n");

            for (int i = 0; i < n; i++) {  
                if (i > 0) sb.append(" "); // adiciona um espaço antes de cada residência, exceto antes da primeira, para formatar a saída conforme o exemplo
                sb.append(moradores[i]).append("-").append(consumo[i]);
            }
            sb.append("\n");

            double media = (double) totalConsumo / totalMoradores;
            long mediaX100 = (long)(media * 100);
            long parteInteira  = mediaX100 / 100;
            long parteDecimal  = mediaX100 % 100;

            String decimal;
            if (parteDecimal < 10) {
                decimal = "0" + parteDecimal;
            } else {
                decimal = "" + parteDecimal;
            }

            sb.append("Consumo medio: ")
              .append(parteInteira).append(".").append(decimal)
              .append(" m3.\n");
        }

        System.out.print(sb);
    }
}
