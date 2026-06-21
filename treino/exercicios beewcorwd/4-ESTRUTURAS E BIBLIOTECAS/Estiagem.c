#include <stdio.h>

#define MAX_N 10001

int moradores[MAX_N];
int consumo[MAX_N];

int lerInt() {
    int c;
    while ((c = getchar()) != EOF && (c < '0' || c > '9'));
    if (c == EOF) return 0;
    int num = c - '0';
    while ((c = getchar()) >= '0' && c <= '9')
        num = num * 10 + (c - '0');
    return num;
}

void insertionSort(int n) {
    int i, j, km, kc;
    for (i = 1; i < n; i++) {
        km = moradores[i];
        kc = consumo[i];
        j = i - 1;
        while (j >= 0 && consumo[j] > kc) {
            moradores[j+1] = moradores[j];
            consumo[j+1]   = consumo[j];
            j--;
        }
        moradores[j+1] = km;
        consumo[j+1]   = kc;
    }
}

int main() {
    int n, i, cidade = 0, primeiro = 1;
    long long totalMoradores, totalConsumo;

    while ((n = lerInt()) != 0) {
        cidade++;
        totalMoradores = 0;
        totalConsumo   = 0;

        for (i = 0; i < n; i++) {
            int x = lerInt();
            int y = lerInt();
            moradores[i] = x;
            consumo[i]   = y / x;
            totalMoradores += x;
            totalConsumo   += y;
        }

        insertionSort(n);

        if (!primeiro) printf("\n");
        primeiro = 0;

        printf("Cidade# %d:\n", cidade);
        for (i = 0; i < n; i++) {
            if (i > 0) printf(" ");
            printf("%d-%d", moradores[i], consumo[i]);
        }
        printf("\n");

        long long mediaX100 = (totalConsumo * 100LL) / totalMoradores;
        printf("Consumo medio: %lld.%02lld m3.\n",
               mediaX100 / 100, mediaX100 % 100);
    }

    return 0;
}
