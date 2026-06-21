import java.util.Scanner;

public class euposso {

    static int[] st = new int[1001];
    static int   stTop;

    static int[] qu    = new int[1001];
    static int   quHead, quTail;

    static int[] hp     = new int[1001]; // max-heap 1-indexado
    static int   hpSize;

    static void heapPush(int x) {
        int i = ++hpSize;
        hp[i] = x;
        while (i > 1 && hp[i] > hp[i / 2]) {
            int tmp = hp[i]; hp[i] = hp[i / 2]; hp[i / 2] = tmp;
            i /= 2;
        }
    }

    static int heapPop() {
        int top = hp[1];
        hp[1] = hp[hpSize--];
        int i = 1;
        while (true) {
            int maior = i, l = 2 * i, r = 2 * i + 1;
            if (l <= hpSize && hp[l] > hp[maior]) maior = l;
            if (r <= hpSize && hp[r] > hp[maior]) maior = r;
            if (maior == i) break;
            int tmp = hp[i]; hp[i] = hp[maior]; hp[maior] = tmp;
            i = maior;
        }
        return top;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        while (sc.hasNextInt()) {
            int n = sc.nextInt();

            stTop = 0;
            quHead = quTail = 0;
            hpSize = 0;
            boolean stackOk = true, queueOk = true, prioOk = true;

            for (int i = 0; i < n; i++) {
                int op = sc.nextInt();
                if (op == 1) {
                    int x = sc.nextInt();
                    if (stackOk) st[stTop++] = x;
                    if (queueOk) qu[quTail++] = x;
                    if (prioOk)  heapPush(x);
                } else {
                    int x = sc.nextInt();
                    if (stackOk && (stTop == 0 || st[--stTop] != x))
                        stackOk = false;
                    if (queueOk && (quHead == quTail || qu[quHead++] != x))
                        queueOk = false;
                    if (prioOk  && (hpSize == 0 || heapPop() != x))
                        prioOk = false;
                }
            }

            int count = (stackOk ? 1 : 0) + (queueOk ? 1 : 0) + (prioOk ? 1 : 0);
            if      (count == 0)  sb.append("impossible\n");
            else if (count >  1)  sb.append("not sure\n");
            else if (stackOk)     sb.append("stack\n");
            else if (queueOk)     sb.append("queue\n");
            else                  sb.append("priority queue\n");
        }

        System.out.print(sb);
    }
}
