#include <stdio.h>

#define MAX_N 100001

long long tree[4*MAX_N+4];
long long lazy[4*MAX_N+4];

void propagar(int no, int ini, int fim) {
    if (lazy[no] != 0) {
        int mid = (ini + fim) / 2;
        tree[2*no]   += lazy[no] * (mid - ini + 1);
        tree[2*no+1] += lazy[no] * (fim - mid);
        lazy[2*no]   += lazy[no];
        lazy[2*no+1] += lazy[no];
        lazy[no] = 0;
    }
}

void atualizar(int no, int ini, int fim, int l, int r, long long v) {
    if (r < ini || fim < l) return;
    if (l <= ini && fim <= r) {
        tree[no] += v * (fim - ini + 1);
        lazy[no] += v;
        return;
    }
    propagar(no, ini, fim);
    int mid = (ini + fim) / 2;
    atualizar(2*no,   ini,   mid, l, r, v);
    atualizar(2*no+1, mid+1, fim, l, r, v);
    tree[no] = tree[2*no] + tree[2*no+1];
}

long long consultar(int no, int ini, int fim, int l, int r) {
    if (r < ini || fim < l) return 0;
    if (l <= ini && fim <= r) return tree[no];
    propagar(no, ini, fim);
    int mid = (ini + fim) / 2;
    return consultar(2*no,   ini,   mid, l, r)
         + consultar(2*no+1, mid+1, fim, l, r);
}

int main() {
    int T, N, C, cmd, p, q, i;
    long long v;

    scanf("%d", &T);
    while (T--) {
        scanf("%d %d", &N, &C);

        /* zera apenas os nós que serão usados */
        for (i = 0; i < 4*N+4; i++) { tree[i] = 0; lazy[i] = 0; }

        while (C--) {
            scanf("%d", &cmd);
            if (cmd == 0) {
                scanf("%d %d %lld", &p, &q, &v);
                atualizar(1, 1, N, p, q, v);
            } else {
                scanf("%d %d", &p, &q);
                printf("%lld\n", consultar(1, 1, N, p, q));
            }
        }
    }
    return 0;
}
