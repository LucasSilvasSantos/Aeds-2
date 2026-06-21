#include <stdio.h>

#define MAX_M     105
#define MAX_NODES 205

/* lista ligada representada por arrays paralelos (pool de nós) */
int head[MAX_M];
int node_key[MAX_NODES];
int node_next[MAX_NODES];
int node_count;

void init(int M) {
    int i;
    for (i = 0; i < M; i++) head[i] = -1;
    node_count = 0;
}

void insert(int x, int M) {
    int h = x % M;
    int cur;

    node_key[node_count]  = x;
    node_next[node_count] = -1;

    if (head[h] == -1) {
        head[h] = node_count;
    } else {
        cur = head[h];
        while (node_next[cur] != -1) cur = node_next[cur];
        node_next[cur] = node_count;
    }
    node_count++;
}

int main() {
    int N, M, C, i, j, x, cur;

    scanf("%d", &N);

    for (i = 0; i < N; i++) {
        scanf("%d %d", &M, &C);
        init(M);

        for (j = 0; j < C; j++) {
            scanf("%d", &x);
            insert(x, M);
        }

        if (i > 0) printf("\n");

        for (j = 0; j < M; j++) {
            printf("%d -> ", j);
            cur = head[j];
            while (cur != -1) {
                printf("%d -> ", node_key[cur]);
                cur = node_next[cur];
            }
            printf("\\\n");
        }
    }

    return 0;
}
