import java.util.Scanner;

public class galhos {

    // Calcula o comprimento da cadeia central (ccl) de cada nó de forma iterativa
    // para evitar estouro de pilha com cadeias longas (N até 10^4)
    static void computeCcl(int[] center, int[] ccl, int n) {
        int[] path = new int[n + 1];
        for (int start = 1; start <= n; start++) {
            if (ccl[start] != -1) continue;
            int pathLen = 0;
            int v = start;
            // percorre a cadeia até encontrar nó já calculado ou nulo
            while (v != 0 && ccl[v] == -1) {
                path[pathLen++] = v;
                v = center[v];
            }
            int base = (v == 0) ? 0 : ccl[v];
            // preenche de trás pra frente: folha primeiro
            for (int i = pathLen - 1; i >= 0; i--) {
                ccl[path[i]] = ++base;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        while (sc.hasNextInt()) {
            // --- leitura da árvore canhota C (sem filhos direitos) ---
            int N = sc.nextInt();
            int[] centerC = new int[N + 1];
            int[] cclC    = new int[N + 1];
            for (int i = 1; i <= N; i++) cclC[i] = -1;

            for (int i = 0; i < N; i++) {
                int id = sc.nextInt();
                sc.nextInt();            // filho esquerdo (não usado)
                centerC[id] = sc.nextInt();
            }

            // --- leitura da árvore destra D (sem filhos esquerdos) ---
            int M = sc.nextInt();
            int[] centerD = new int[M + 1];
            int[] cclD    = new int[M + 1];
            for (int i = 1; i <= M; i++) cclD[i] = -1;

            for (int i = 0; i < M; i++) {
                int id = sc.nextInt();
                centerD[id] = sc.nextInt();
                sc.nextInt();            // filho direito (não usado)
            }

            computeCcl(centerC, cclC, N);
            computeCcl(centerD, cclD, M);

            // maior cadeia central em cada árvore (melhor nó candidato p/ encaixe)
            int maxCclC = 0;
            for (int i = 1; i <= N; i++) {
                if (cclC[i] > maxCclC) maxCclC = cclC[i];
            }
            int maxCclD = 0;
            for (int i = 1; i <= M; i++) {
                if (cclD[i] > maxCclD) maxCclD = cclD[i];
            }

            int cclC1 = cclC[1]; // cadeia da raiz de C
            int cclD1 = cclD[1]; // cadeia da raiz de D

            // caso 1: raiz de S = raiz de C  →  melhor nó de C recebe raiz de D
            int overlap1 = maxCclC < cclD1 ? maxCclC : cclD1;
            // caso 2: raiz de S = raiz de D  →  melhor nó de D recebe raiz de C
            int overlap2 = cclC1 < maxCclD ? cclC1 : maxCclD;

            int maxOverlap = overlap1 > overlap2 ? overlap1 : overlap2;

            sb.append(N + M - maxOverlap).append("\n");
        }

        System.out.print(sb);
    }
}
