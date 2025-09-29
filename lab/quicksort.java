import java.util.Arrays;
import java.util.Random;

public class quicksort {
    static Random aleatorio = new Random();

    // Função auxiliar para trocar elementos
    static void trocar(int[] vetor, int i, int j) {
        int temp = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = temp;
    }

    // Particionamento (Lomuto)
    static int particionar(int[] vetor, int esquerda, int direita, int indicePivo) {
        int pivo = vetor[indicePivo];
        trocar(vetor, indicePivo, direita); // coloca o pivô no final
        int i = esquerda;
        for (int j = esquerda; j < direita; j++) {
            if (vetor[j] < pivo) {
                trocar(vetor, i, j);
                i++;
            }
        }
        trocar(vetor, i, direita);
        return i;
    }

    // QuickSort - pivô no primeiro elemento
    static void quickSortPrimeiroPivo(int[] vetor, int esquerda, int direita) {
        if (esquerda < direita) {
            int indicePivo = esquerda;
            int p = particionar(vetor, esquerda, direita, indicePivo);
            quickSortPrimeiroPivo(vetor, esquerda, p - 1);
            quickSortPrimeiroPivo(vetor, p + 1, direita);
        }
    }

    // QuickSort - pivô no último elemento
    static void quickSortUltimoPivo(int[] vetor, int esquerda, int direita) {
        if (esquerda < direita) {
            int indicePivo = direita;
            int p = particionar(vetor, esquerda, direita, indicePivo);
            quickSortUltimoPivo(vetor, esquerda, p - 1);
            quickSortUltimoPivo(vetor, p + 1, direita);
        }
    }

    // QuickSort - pivô aleatório
    static void quickSortPivoAleatorio(int[] vetor, int esquerda, int direita) {
        if (esquerda < direita) {
            int indicePivo = esquerda + aleatorio.nextInt(direita - esquerda + 1);
            int p = particionar(vetor, esquerda, direita, indicePivo);
            quickSortPivoAleatorio(vetor, esquerda, p - 1);
            quickSortPivoAleatorio(vetor, p + 1, direita);
        }
    }

    // QuickSort - mediana de três
    static int medianaDeTres(int[] vetor, int esquerda, int direita) {
        int meio = (esquerda + direita) / 2;
        if (vetor[esquerda] > vetor[meio]) trocar(vetor, esquerda, meio);
        if (vetor[esquerda] > vetor[direita]) trocar(vetor, esquerda, direita);
        if (vetor[meio] > vetor[direita]) trocar(vetor, meio, direita);
        return meio;
    }

    static void quickSortMedianaDeTres(int[] vetor, int esquerda, int direita) {
        if (esquerda < direita) {
            int indicePivo = medianaDeTres(vetor, esquerda, direita);
            int p = particionar(vetor, esquerda, direita, indicePivo);
            quickSortMedianaDeTres(vetor, esquerda, p - 1);
            quickSortMedianaDeTres(vetor, p + 1, direita);
        }
    }

    // Função para medir o tempo
    static long medirTempo(Runnable metodoOrdenacao) {
        long inicio = System.currentTimeMillis();
        metodoOrdenacao.run();
        return System.currentTimeMillis() - inicio;
    }

    // Geração de arrays de teste
    static int[] gerarVetorAleatorio(int n) {
        int[] vetor = new int[n];
        for (int i = 0; i < n; i++) vetor[i] = aleatorio.nextInt(100000);
        return vetor;
    }

    static int[] gerarVetorOrdenado(int n) {
        int[] vetor = new int[n];
        for (int i = 0; i < n; i++) vetor[i] = i;
        return vetor;
    }

    static int[] gerarVetorQuaseOrdenado(int n) {
        int[] vetor = gerarVetorOrdenado(n);
        for (int i = 0; i < n / 100; i++) { // bagunça 1% dos elementos
            int a = aleatorio.nextInt(n);
            int b = aleatorio.nextInt(n);
            trocar(vetor, a, b);
        }
        return vetor;
    }

    // Programa principal
    public static void main(String[] args) {
        int[] tamanhos = {100, 1000, 10000}; // pode aumentar para 100000 se quiser
        for (int n : tamanhos) {
            System.out.println("=== Teste com N = " + n + " ===");

            // Três tipos de vetores
            int[][] conjuntos = {
                gerarVetorAleatorio(n),
                gerarVetorOrdenado(n),
                gerarVetorQuaseOrdenado(n)
            };
            String[] tipos = {"Aleatório", "Ordenado", "Quase Ordenado"};

            for (int t = 0; t < conjuntos.length; t++) {
                int[] base = conjuntos[t];
                System.out.println("Vetor: " + tipos[t]);

                // Copias para cada algoritmo
                int[] v1 = Arrays.copyOf(base, base.length);
                int[] v2 = Arrays.copyOf(base, base.length);
                int[] v3 = Arrays.copyOf(base, base.length);
                int[] v4 = Arrays.copyOf(base, base.length);

                long tempo1 = medirTempo(() -> quickSortPrimeiroPivo(v1, 0, v1.length - 1));
                long tempo2 = medirTempo(() -> quickSortUltimoPivo(v2, 0, v2.length - 1));
                long tempo3 = medirTempo(() -> quickSortPivoAleatorio(v3, 0, v3.length - 1));
                long tempo4 = medirTempo(() -> quickSortMedianaDeTres(v4, 0, v4.length - 1));

                System.out.println("  Primeiro pivô: " + tempo1 + " ms");
                System.out.println("  Último pivô:   " + tempo2 + " ms");
                System.out.println("  Aleatório:     " + tempo3 + " ms");
                System.out.println("  Mediana-3:     " + tempo4 + " ms");
                System.out.println();
            }
        }
    }
}
