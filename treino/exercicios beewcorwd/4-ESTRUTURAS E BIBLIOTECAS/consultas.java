import java.util.Scanner;

public class consultas {

    static long[] tree;
    static long[] lazy;

    // propaga o valor pendente do nó pai para os filhos
    static void propagar(int no, int ini, int fim) {
        if (lazy[no] != 0) {
            int mid = (ini + fim) / 2;
            tree[2 * no]     += lazy[no] * (mid - ini + 1);
            tree[2 * no + 1] += lazy[no] * (fim - mid);
            lazy[2 * no]     += lazy[no];
            lazy[2 * no + 1] += lazy[no];
            lazy[no] = 0;
        }
    }

    // adiciona v a todos os elementos no intervalo [l, r]
    static void atualizar(int no, int ini, int fim, int l, int r, long v) {
        if (r < ini || fim < l) return;
        if (l <= ini && fim <= r) {
            tree[no] += v * (fim - ini + 1);
            lazy[no] += v;
            return;
        }
        propagar(no, ini, fim);
        int mid = (ini + fim) / 2;
        atualizar(2 * no,     ini,     mid, l, r, v);
        atualizar(2 * no + 1, mid + 1, fim, l, r, v);
        tree[no] = tree[2 * no] + tree[2 * no + 1];
    }

    // retorna a soma dos elementos no intervalo [l, r]
    static long consultar(int no, int ini, int fim, int l, int r) {
        if (r < ini || fim < l) return 0;
        if (l <= ini && fim <= r) return tree[no];
        propagar(no, ini, fim);
        int mid = (ini + fim) / 2;
        return consultar(2 * no,     ini,     mid, l, r)
             + consultar(2 * no + 1, mid + 1, fim, l, r);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        int T = sc.nextInt();
        while (T-- > 0) {
            int N = sc.nextInt();
            int C = sc.nextInt();

            // Java inicializa arrays com 0, não precisa construir a árvore
            tree = new long[4 * N + 4];
            lazy = new long[4 * N + 4];

            for (int i = 0; i < C; i++) {
                int cmd = sc.nextInt();
                if (cmd == 0) {
                    int  p = sc.nextInt();
                    int  q = sc.nextInt();
                    long v = sc.nextLong();
                    atualizar(1, 1, N, p, q, v);
                } else {
                    int p = sc.nextInt();
                    int q = sc.nextInt();
                    sb.append(consultar(1, 1, N, p, q)).append("\n");
                }
            }
        }

        System.out.print(sb);
    }
}
