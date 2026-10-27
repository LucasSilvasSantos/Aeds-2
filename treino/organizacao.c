#include <stdio.h>
#include <stdlib.h>

typedef struct {
    float altura;
    char nome[51];
} Participante;

int main() {
    int f;
    scanf("%d", &f);

    int a;
    scanf("%d", &a);

    int quantidade = f * a;

    Participante participantes[quantidade];

    /* Leitura dos participantes */
    for (int i = 0; i < quantidade; i++) {
        scanf("%f", &participantes[i].altura);
        scanf(" %50[^\n]", participantes[i].nome);
    }

    /* Ordenação por inserção */
    for (int i = 1; i < quantidade; i++) {
        Participante temporario = participantes[i];

        int j = i - 1;

        while (j >= 0 &&
               participantes[j].altura > temporario.altura) {

            participantes[j + 1] = participantes[j];
            j--;
        }

        participantes[j + 1] = temporario;
    }

    /* Impressão do resultado */
    for (int i = 0; i < quantidade; i++) {
        int fileira = (i / a) + 1;

        printf("%d %.2f %s\n",
               fileira,
               participantes[i].altura,
               participantes[i].nome);
    }

    return 0;
}