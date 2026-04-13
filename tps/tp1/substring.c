#include <stdio.h>

int longestSubstring(char *input) {
    int inicio = 0;
    int maxTamanho = 0;
    int len = 0;

    while (input[len] != '\0') len++;

    for (int fim = 0; fim < len; fim++) {
        char atual = input[fim];

        for (int j = inicio; j < fim; j++) {
            if (input[j] == atual) {
                inicio = j + 1;
                break;
            }
        }

        int tamanhoAtual = fim - inicio + 1;
        if (tamanhoAtual > maxTamanho) {
            maxTamanho = tamanhoAtual;
        }
    }

    return maxTamanho;
}

int main() {
    char linha[10001];

    while (fgets(linha, sizeof(linha), stdin)) {
        int i = 0;
        while (linha[i] != '\0' && linha[i] != '\n') i++;
        linha[i] = '\0';

        if (linha[0] == 'F' && linha[1] == 'I' && linha[2] == 'M' && linha[3] == '\0') break;

        printf("%d\n", longestSubstring(linha));
    }

    return 0;
}
