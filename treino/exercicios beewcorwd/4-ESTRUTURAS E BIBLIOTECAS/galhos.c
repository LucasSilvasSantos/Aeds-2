#include <stdio.h>

#define MAX_N 10001

int centerC[MAX_N], cclC[MAX_N];
int centerD[MAX_N], cclD[MAX_N];
int path[MAX_N];

void computeCcl(int* center, int* ccl, int n) {
    int start, v, pathLen, i, base;
    for (start = 1; start <= n; start++) {
        if (ccl[start] != -1) continue;
        pathLen = 0;
        v = start;
        while (v != 0 && ccl[v] == -1) {
            path[pathLen++] = v;
            v = center[v];
        }
        base = (v == 0) ? 0 : ccl[v];
        for (i = pathLen - 1; i >= 0; i--)
            ccl[path[i]] = ++base;
    }
}

int main() {
    int N, M, i, id, esq, dir;

    while (scanf("%d", &N) == 1) {
        for (i = 1; i <= N; i++) cclC[i] = -1;
        for (i = 0; i < N; i++) {
            /* canhota: id esquerdo central (sem direito) */
            scanf("%d %d %d", &id, &esq, &centerC[id]);
        }

        scanf("%d", &M);
        for (i = 1; i <= M; i++) cclD[i] = -1;
        for (i = 0; i < M; i++) {
            /* destra: id central direito (sem esquerdo) */
            scanf("%d %d %d", &id, &centerD[id], &dir);
        }

        computeCcl(centerC, cclC, N);
        computeCcl(centerD, cclD, M);

        int maxCclC = 0, maxCclD = 0;
        for (i = 1; i <= N; i++) if (cclC[i] > maxCclC) maxCclC = cclC[i];
        for (i = 1; i <= M; i++) if (cclD[i] > maxCclD) maxCclD = cclD[i];

        int cclC1 = cclC[1];
        int cclD1 = cclD[1];

        int overlap1 = maxCclC < cclD1 ? maxCclC : cclD1; /* raiz de S = raiz de C */
        int overlap2 = cclC1 < maxCclD ? cclC1 : maxCclD; /* raiz de S = raiz de D */
        int maxOverlap = overlap1 > overlap2 ? overlap1 : overlap2;

        printf("%d\n", N + M - maxOverlap);
    }
    return 0;
}
