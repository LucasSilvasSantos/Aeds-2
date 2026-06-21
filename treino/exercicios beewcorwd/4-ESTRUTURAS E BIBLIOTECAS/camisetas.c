#include <stdio.h>

#define MAX_NODES 10001
#define MAX_LEN   201

/* BST representada por arrays paralelos (pool de nós sem malloc) */
char pNome[MAX_NODES][MAX_LEN];
char pCor [MAX_NODES][MAX_LEN];
char pTam [MAX_NODES];
int  pEsq [MAX_NODES];
int  pDir [MAX_NODES];
int  poolCount;

int compareStr(char* a, char* b) {
    int i = 0;
    while (a[i] && b[i]) {
        if (a[i] != b[i]) return (unsigned char)a[i] - (unsigned char)b[i];
        i++;
    }
    if (!a[i] && !b[i]) return 0;
    return a[i] ? 1 : -1;
}

/* cor ASC, tamanho DESC (P>M>G = menor char ganha), nome ASC */
int compare(char* corA, char tamA, char* nomeA,
            char* corB, char tamB, char* nomeB) {
    int c = compareStr(corA, corB);
    if (c != 0) return c;
    c = tamB - tamA; /* invertido: B - A = descendente */
    if (c != 0) return c;
    return compareStr(nomeA, nomeB);
}

void copyStr(char* dst, char* src) {
    int i = 0;
    while (src[i]) { dst[i] = src[i]; i++; }
    dst[i] = '\0';
}

int inserir(int raiz, char* nome, char* cor, char tamanho) {
    if (raiz == -1) {
        int idx = poolCount++;
        copyStr(pNome[idx], nome);
        copyStr(pCor[idx],  cor);
        pTam[idx] = tamanho;
        pEsq[idx] = -1;
        pDir[idx] = -1;
        return idx;
    }
    int cmp = compare(cor, tamanho, nome,
                      pCor[raiz], pTam[raiz], pNome[raiz]);
    if (cmp <= 0) pEsq[raiz] = inserir(pEsq[raiz], nome, cor, tamanho);
    else          pDir[raiz] = inserir(pDir[raiz], nome, cor, tamanho);
    return raiz;
}

void emOrdem(int raiz) {
    if (raiz == -1) return;
    emOrdem(pEsq[raiz]);
    printf("%s %c %s\n", pCor[raiz], pTam[raiz], pNome[raiz]);
    emOrdem(pDir[raiz]);
}

int main() {
    int n, i, raiz, primeiro = 1;
    char nome[MAX_LEN], cor[MAX_LEN], tam[4];
    int c, ch;

    while (scanf("%d", &n) == 1 && n != 0) {
        poolCount = 0;
        raiz = -1;

        /* consome o resto da linha do n */
        while ((ch = getchar()) != '\n' && ch != EOF);

        for (i = 0; i < n; i++) {
            /* lê o nome — pode conter espaços */
            int j = 0;
            while ((c = getchar()) != '\n' && c != EOF)
                if (c != '\r') nome[j++] = c;
            nome[j] = '\0';

            /* lê cor e tamanho */
            scanf("%s %s", cor, tam);

            /* consome o resto da linha do tamanho */
            while ((ch = getchar()) != '\n' && ch != EOF);

            raiz = inserir(raiz, nome, cor, tam[0]);
        }

        if (!primeiro) printf("\n");
        primeiro = 0;

        emOrdem(raiz);
    }

    return 0;
}
