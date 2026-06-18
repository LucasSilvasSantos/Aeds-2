import java.util.Scanner;

public class compras {

    static int[] maxTree, minTree;

    static void build(int[] p, int no, int ini, int fim) {
        if (ini == fim) {
            maxTree[no] = p[ini];
            minTree[no] = p[ini];
            return;
        }
        int mid = (ini + fim) / 2;
        build(p, 2 * no,     ini,       mid);
        build(p, 2 * no + 1, mid + 1,   fim);
        maxTree[no] = maxTree[2 * no] > maxTree[2 * no + 1] ? maxTree[2 * no] : maxTree[2 * no + 1];
        minTree[no] = minTree[2 * no] < minTree[2 * no + 1] ? minTree[2 * no] : minTree[2 * no + 1];
    }

    // atualização pontual: troca o preço da loja idx para val
    static void atualizar(int no, int ini, int fim, int idx, int val) {
        if (ini == fim) {
            maxTree[no] = val;
            minTree[no] = val;
            return;
        }
        int mid = (ini + fim) / 2;
        if (idx <= mid) atualizar(2 * no,     ini,     mid, idx, val);
        else            atualizar(2 * no + 1, mid + 1, fim, idx, val);
        maxTree[no] = maxTree[2 * no] > maxTree[2 * no + 1] ? maxTree[2 * no] : maxTree[2 * no + 1];
        minTree[no] = minTree[2 * no] < minTree[2 * no + 1] ? minTree[2 * no] : minTree[2 * no + 1];
    }

    static int consultaMax(int no, int ini, int fim, int l, int r) {
        if (r < ini || fim < l) return Integer.MIN_VALUE;
        if (l <= ini && fim <= r) return maxTree[no];
        int mid = (ini + fim) / 2;
        int esq = consultaMax(2 * no,     ini,     mid, l, r);
        int dir = consultaMax(2 * no + 1, mid + 1, fim, l, r);
        return esq > dir ? esq : dir;
    }

    static int consultaMin(int no, int ini, int fim, int l, int r) {
        if (r < ini || fim < l) return Integer.MAX_VALUE;
        if (l <= ini && fim <= r) return minTree[no];
        int mid = (ini + fim) / 2;
        int esq = consultaMin(2 * no,     ini,     mid, l, r);
        int dir = consultaMin(2 * no + 1, mid + 1, fim, l, r);
        return esq < dir ? esq : dir;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        while (sc.hasNextInt()) {
            int N = sc.nextInt();
            int[] precos = new int[N + 1];
            for (int i = 1; i <= N; i++) precos[i] = sc.nextInt();

            maxTree = new int[4 * N + 4];
            minTree = new int[4 * N + 4];
            build(precos, 1, 1, N);

            int Q = sc.nextInt();
            for (int q = 0; q < Q; q++) {
                int op = sc.nextInt();
                if (op == 1) {
                    int i = sc.nextInt();
                    int p = sc.nextInt();
                    atualizar(1, 1, N, i, p);
                } else {
                    int i = sc.nextInt();
                    int j = sc.nextInt();
                    int max = consultaMax(1, 1, N, i, j);
                    int min = consultaMin(1, 1, N, i, j);
                    sb.append(max - min).append("\n");
                }
            }
        }

        System.out.print(sb);
    }
}
