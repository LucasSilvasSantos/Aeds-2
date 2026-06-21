#include <stdio.h>

#define MAX_N    200001
#define NEG_INF  (-1073741824)
#define POS_INF  (1073741824)

int maxTree[4*MAX_N+4];
int minTree[4*MAX_N+4];
int precos[MAX_N];
int N;

void build(int no, int ini, int fim) {
    if (ini == fim) {
        maxTree[no] = minTree[no] = precos[ini];
        return;
    }
    int mid = (ini + fim) / 2;
    build(2*no,   ini,   mid);
    build(2*no+1, mid+1, fim);
    maxTree[no] = maxTree[2*no] > maxTree[2*no+1] ? maxTree[2*no] : maxTree[2*no+1];
    minTree[no] = minTree[2*no] < minTree[2*no+1] ? minTree[2*no] : minTree[2*no+1];
}

void atualizar(int no, int ini, int fim, int idx, int val) {
    if (ini == fim) {
        maxTree[no] = minTree[no] = val;
        return;
    }
    int mid = (ini + fim) / 2;
    if (idx <= mid) atualizar(2*no,   ini,   mid, idx, val);
    else            atualizar(2*no+1, mid+1, fim, idx, val);
    maxTree[no] = maxTree[2*no] > maxTree[2*no+1] ? maxTree[2*no] : maxTree[2*no+1];
    minTree[no] = minTree[2*no] < minTree[2*no+1] ? minTree[2*no] : minTree[2*no+1];
}

int consultaMax(int no, int ini, int fim, int l, int r) {
    if (r < ini || fim < l) return NEG_INF;
    if (l <= ini && fim <= r) return maxTree[no];
    int mid = (ini + fim) / 2;
    int esq = consultaMax(2*no,   ini,   mid, l, r);
    int dir = consultaMax(2*no+1, mid+1, fim, l, r);
    return esq > dir ? esq : dir;
}

int consultaMin(int no, int ini, int fim, int l, int r) {
    if (r < ini || fim < l) return POS_INF;
    if (l <= ini && fim <= r) return minTree[no];
    int mid = (ini + fim) / 2;
    int esq = consultaMin(2*no,   ini,   mid, l, r);
    int dir = consultaMin(2*no+1, mid+1, fim, l, r);
    return esq < dir ? esq : dir;
}

int main() {
    int Q, op, i, j, p;
    while (scanf("%d", &N) == 1) {
        for (i = 1; i <= N; i++) scanf("%d", &precos[i]);
        build(1, 1, N);

        scanf("%d", &Q);
        while (Q--) {
            scanf("%d", &op);
            if (op == 1) {
                scanf("%d %d", &i, &p);
                atualizar(1, 1, N, i, p);
            } else {
                scanf("%d %d", &i, &j);
                printf("%d\n",
                    consultaMax(1, 1, N, i, j) - consultaMin(1, 1, N, i, j));
            }
        }
    }
    return 0;
}
