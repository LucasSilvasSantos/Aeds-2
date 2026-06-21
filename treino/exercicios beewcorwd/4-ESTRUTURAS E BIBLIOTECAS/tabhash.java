import java.util.Scanner;

public class tabhash {

    static int[] head;
    static int[] nodeKey;
    static int[] nodeNext;
    static int nodeCount;

    static void init(int M) {
        head = new int[M];
        for (int i = 0; i < M; i++) head[i] = -1;
        nodeCount = 0;
    }

    static void insert(int x, int M) {
        int h = x % M;
        nodeKey[nodeCount] = x;
        nodeNext[nodeCount] = -1;

        if (head[h] == -1) {
            head[h] = nodeCount;
        } else {
            int cur = head[h];
            while (nodeNext[cur] != -1) cur = nodeNext[cur];
            nodeNext[cur] = nodeCount;
        }
        nodeCount++;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        // max 200 chaves por caso de teste
        nodeKey  = new int[201];
        nodeNext = new int[201];

        StringBuilder sb = new StringBuilder();

        for (int t = 0; t < N; t++) {
            int M = sc.nextInt();
            int C = sc.nextInt();

            init(M);

            for (int i = 0; i < C; i++) insert(sc.nextInt(), M);

            if (t > 0) sb.append('\n');

            for (int i = 0; i < M; i++) {
                sb.append(i).append(" -> ");
                int cur = head[i];
                while (cur != -1) {
                    sb.append(nodeKey[cur]).append(" -> ");
                    cur = nodeNext[cur];
                }
                sb.append("\\\n");
            }
        }

        System.out.print(sb);
    }
}
