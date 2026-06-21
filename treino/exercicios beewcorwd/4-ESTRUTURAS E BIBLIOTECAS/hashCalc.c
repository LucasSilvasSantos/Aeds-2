#include <stdio.h>

#define MAX_LEN 55

int main() {
    int N, L, e, p;
    char s[MAX_LEN];
    long long hash;

    scanf("%d", &N);
    while (N--) {
        scanf("%d", &L);
        hash = 0;

        for (e = 0; e < L; e++) {
            scanf("%s", s);
            for (p = 0; s[p]; p++)
                hash += (s[p] - 'A') + e + p;
        }

        printf("%lld\n", hash);
    }

    return 0;
}
