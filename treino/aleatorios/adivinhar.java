import java.util.Scanner;

public class adivinhar {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {

            int n = sc.nextInt();

            int[] stack = new int[1000];  // pilha  ultimo a entrar primeiro a sair 
            int[] queue = new int[1000]; // fila primeiro a entrar primeiro a sair 
            int[] priority = new int[1000]; // maior valor sai, sempre  

            int top = 0; 
            int inicio = 0;
            int fim = 0;
            int pSize = 0;

            boolean isStack = true; // 
            boolean isQueue = true;
            boolean isPriority = true;

            for (int i = 0; i < n; i++) {

                int op = sc.nextInt();
                int x = sc.nextInt();

                if (op == 1) {

                    // stack
                    stack[top] = x;
                    top++;

                    // queue
                    queue[fim] = x;
                    fim++;

                    // priority
                    priority[pSize] = x;
                    pSize++;

                } else {

                    // stack
                    if (top == 0) {
                        isStack = false;
                    } else {
                        top--;
                        int val = stack[top];
                        if (val != x) isStack = false;
                    }

                    // queue
                    if (inicio == fim) {
                        isQueue = false;
                    } else {
                        int val = queue[inicio];
                        inicio++;
                        if (val != x) isQueue = false;
                    }

                    // priority queue
                    if (pSize == 0) {
                        isPriority = false;
                    } else {

                        int maxIndex = 0;

                        for (int j = 1; j < pSize; j++) {
                            if (priority[j] > priority[maxIndex]) {
                                maxIndex = j;
                            }
                        }

                        int val = priority[maxIndex];

                        for (int j = maxIndex; j < pSize - 1; j++) {
                            priority[j] = priority[j + 1];
                        }

                        pSize--;

                        if (val != x) isPriority = false;
                    }
                }
            }

            int count = 0;

            if (isStack) count++;
            if (isQueue) count++;
            if (isPriority) count++;

            if (count == 0) System.out.println("impossible");
            else if (count > 1) System.out.println("not sure");
            else if (isStack) System.out.println("stack");
            else if (isQueue) System.out.println("queue");
            else System.out.println("priority queue");
        }

        sc.close();
    }
}