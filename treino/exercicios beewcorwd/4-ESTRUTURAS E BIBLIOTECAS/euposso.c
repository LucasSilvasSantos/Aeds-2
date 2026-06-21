#include <stdio.h>

#define MAX_N 1001

int st[MAX_N], stTop;
int qu[MAX_N], quHead, quTail;
int hp[MAX_N], hpSize; /* max-heap 1-indexado */

void heapPush(int x) {
    int i = ++hpSize, tmp;
    hp[i] = x;
    while (i > 1 && hp[i] > hp[i/2]) {
        tmp = hp[i]; hp[i] = hp[i/2]; hp[i/2] = tmp;
        i /= 2;
    }
}

int heapPop() {
    int top = hp[1], i = 1, maior, l, r, tmp;
    hp[1] = hp[hpSize--];
    while (1) {
        maior = i; l = 2*i; r = 2*i+1;
        if (l <= hpSize && hp[l] > hp[maior]) maior = l;
        if (r <= hpSize && hp[r] > hp[maior]) maior = r;
        if (maior == i) break;
        tmp = hp[i]; hp[i] = hp[maior]; hp[maior] = tmp;
        i = maior;
    }
    return top;
}

int main() {
    int n, op, x, stackOk, queueOk, prioOk, count, i;

    while (scanf("%d", &n) == 1) {
        stTop = 0;
        quHead = quTail = 0;
        hpSize = 0;
        stackOk = queueOk = prioOk = 1;

        for (i = 0; i < n; i++) {
            scanf("%d", &op);
            if (op == 1) {
                scanf("%d", &x);
                if (stackOk) st[stTop++] = x;
                if (queueOk) qu[quTail++] = x;
                if (prioOk)  heapPush(x);
            } else {
                scanf("%d", &x);
                if (stackOk && (stTop == 0       || st[--stTop]    != x)) stackOk = 0;
                if (queueOk && (quHead == quTail  || qu[quHead++]   != x)) queueOk = 0;
                if (prioOk  && (hpSize == 0       || heapPop()      != x)) prioOk  = 0;
            }
        }

        count = stackOk + queueOk + prioOk;
        if      (count == 0) printf("impossible\n");
        else if (count >  1) printf("not sure\n");
        else if (stackOk)    printf("stack\n");
        else if (queueOk)    printf("queue\n");
        else                 printf("priority queue\n");
    }

    return 0;
}
