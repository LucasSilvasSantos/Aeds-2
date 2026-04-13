#include <stdio.h>

int comprimento(char *s) {
    int len = 0;
    while (s[len] != '\0') len++;
    return len;
}

char paraMinusculo(char c) {
    if (c >= 'A' && c <= 'Z') {
        c = (char)(c + 32);
    }
    return c;
}

void stringParaMinusculo(char *s, int len) {
    for (int i = 0; i < len; i++) {
        s[i] = paraMinusculo(s[i]);
    }
}

// divide a linha em duas partes pelo hifen, retorna 1 se encontrou
int dividirPorHifen(char *linha, int len, char *palavra1, char *palavra2) {
    int indiceHifen = -1;

    for (int i = 0; i < len; i++) {
        if (linha[i] == '-') {
            indiceHifen = i;
            break;
        }
    }

    if (indiceHifen == -1) return 0;

    // copia a parte antes do hifen (sem espacos do trim)
    int inicio = 0;
    int fim = indiceHifen - 1;
    while (inicio <= fim && linha[inicio] == ' ') inicio++;
    while (fim >= inicio && linha[fim] == ' ') fim--;
    int j = 0;
    for (int i = inicio; i <= fim; i++) palavra1[j++] = linha[i];
    palavra1[j] = '\0';

    // copia a parte depois do hifen (sem espacos do trim)
    inicio = indiceHifen + 1;
    fim = len - 1;
    while (inicio <= fim && linha[inicio] == ' ') inicio++;
    while (fim >= inicio && linha[fim] == ' ') fim--;
    j = 0;
    for (int i = inicio; i <= fim; i++) palavra2[j++] = linha[i];
    palavra2[j] = '\0';

    return 1;
}

int contarOcorrencias(char *s, int len, char c) {
    int count = 0;
    for (int i = 0; i < len; i++) {
        if (s[i] == c) count++;
    }
    return count;
}

int saoAnagramas(char *palavra1, char *palavra2) {
    int len1 = comprimento(palavra1);
    int len2 = comprimento(palavra2);

    if (len1 != len2) return 0;

    for (int i = 0; i < len1; i++) {
        if (contarOcorrencias(palavra1, len1, palavra1[i]) !=
            contarOcorrencias(palavra2, len2, palavra1[i])) {
            return 0;
        }
    }

    return 1;
}

int main() {
    char linha[1000];

    while (fgets(linha, sizeof(linha), stdin)) {
        int len = comprimento(linha);

        if (len > 0 && linha[len - 1] == '\n') {
            linha[--len] = '\0';
        }

        if (linha[0] == 'F' && linha[1] == 'I' && linha[2] == 'M' && len == 3) {
            break;
        }

        char palavra1[500], palavra2[500];

        if (!dividirPorHifen(linha, len, palavra1, palavra2)) {
            printf("NAO\n");
            continue;
        }

        stringParaMinusculo(palavra1, comprimento(palavra1));
        stringParaMinusculo(palavra2, comprimento(palavra2));

        printf("%s\n", saoAnagramas(palavra1, palavra2) ? "SIM" : "NAO");
    }

    return 0;
}
