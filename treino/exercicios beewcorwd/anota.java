import java.io.*;

/**
 * Questão: A Nota - Beecrowd
 * 
 * Problema: Dado N notas e um valor K, encontrar a soma de todas as K-ésimas maiores notas.
 * Por exemplo, se K=3, devemos somar a 3ª maior, 6ª maior, 9ª maior, etc.
 * A resposta deve ser retornada como módulo de 10^9 + 7.
 */
public class anota {
    
    // Constante para o módulo conforme especificado no problema
    private static final long MOD = 1000000007;
    
    /**
     * Método principal que processa a entrada e calcula a resposta
     */
    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);

        // Lê até EOF: cada caso tem primeiro dois inteiros n e k, depois n inteiros
        while (fs.hasNext()) {
            int n = fs.nextInt();
            int k = fs.nextInt();

            int[] notas = new int[n];
            for (int i = 0; i < n; i++) {
                notas[i] = fs.nextInt();
            }

            // Ordena as notas em ordem decrescente (maiores primeiro)
            ordenarDecrescente(notas);

            // Calcula a soma das K-ésimas maiores notas
            long soma = calcularSomaKEsimas(notas, k);

            // Imprime o resultado com módulo
            System.out.println(soma % MOD);
        }
    }
    
    /**
     * Converte uma string para inteiro manualmente
     * @param s String a ser convertida
     * @return Valor inteiro correspondente
     */
    /**
     * Scanner manual que lê bytes de um InputStream e parseia inteiros.
     * Implementação simples e manual, sem uso de String.split ou BufferedReader.
     */
    private static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            this.in = is;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        boolean hasNext() throws IOException {
            int c;
            while ((c = read()) != -1) {
                if (!isWhitespace(c)) {
                    ptr--; // step back one byte for next read
                    return true;
                }
            }
            return false;
        }

        int nextInt() throws IOException {
            int c = read();
            while (c != -1 && isWhitespace(c)) c = read();
            if (c == -1) throw new IOException("EOF");

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int val = 0;
            while (c != -1 && !isWhitespace(c)) {
                val = val * 10 + (c - '0');
                c = read();
            }

            return val * sign;
        }

        private static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == '\f';
        }
    }
    
    /**
     * Ordena o array em ordem decrescente usando QuickSort
     * @param arr Array a ser ordenado
     */
    private static void ordenarDecrescente(int[] arr) {
        quicksortDecrescente(arr, 0, arr.length - 1);
    }
    
    /**
     * Implementação do QuickSort para ordenação decrescente
     * @param arr Array a ser ordenado
     * @param esquerda Índice inicial da partição
     * @param direita Índice final da partição
     */
    private static void quicksortDecrescente(int[] arr, int esquerda, int direita) {
        // Caso base: se a partição tem 1 ou 0 elementos, já está ordenada
        if (esquerda >= direita) {
            return;
        }
        
        // Particiona o array e obtém a posição do pivô
        int indicePivo = particionar(arr, esquerda, direita);
        
        // Recursivamente ordena as duas metades
        quicksortDecrescente(arr, esquerda, indicePivo - 1);
        quicksortDecrescente(arr, indicePivo + 1, direita);
    }
    
    /**
     * Particiona o array para o QuickSort (ordem decrescente)
     * @param arr Array a ser particionado
     * @param esquerda Índice inicial
     * @param direita Índice final
     * @return Posição final do pivô
     */
    private static int particionar(int[] arr, int esquerda, int direita) {
        // Escolhe o último elemento como pivô
        int pivo = arr[direita];
        int i = esquerda - 1; // Índice do menor elemento
        
        // Percorre o array comparando com o pivô
        for (int j = esquerda; j < direita; j++) {
            // Para ordem decrescente, coloca elementos MAIORES que o pivô à esquerda
            if (arr[j] > pivo) {
                i++;
                trocar(arr, i, j);
            }
        }
        
        // Coloca o pivô na posição correta
        trocar(arr, i + 1, direita);
        return i + 1;
    }
    
    /**
     * Troca dois elementos de posição no array
     * @param arr Array onde a troca será feita
     * @param i Índice do primeiro elemento
     * @param j Índice do segundo elemento
     */
    private static void trocar(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * Calcula a soma das K-ésimas maiores notas
     * Soma as notas nas posições: K-1, 2K-1, 3K-1, ... (índices começam em 0)
     * Por exemplo, se K=5, soma as posições 4, 9, 14, 19, ... (5ª, 10ª, 15ª, 20ª maiores)
     * 
     * @param notas Array de notas ordenado em ordem decrescente
     * @param k Intervalo entre as notas a serem somadas
     * @return Soma das K-ésimas maiores notas
     */
    private static long calcularSomaKEsimas(int[] notas, int k) {
        long soma = 0;
        
        // Começa no índice k-1 (k-ésima maior nota) e pula de K em K posições
        for (int i = k - 1; i < notas.length; i += k) {
            soma += notas[i];
        }
        
        return soma;
    }
}
