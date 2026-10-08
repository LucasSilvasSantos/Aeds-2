#include <stdio.h>
#include <stdlib.h>

// Estrutura da célula
typedef struct Celula {
    int elemento;
    struct Celula* prox;
} Celula;


// Estrutura da fila
typedef struct Fila {
    Celula* primeiro;
    Celula* ultimo;
} Fila;


// Inicializa a fila
void iniciarFila(Fila* fila) {

    // cria a célula sentinela
    fila->primeiro = (Celula*) malloc(sizeof(Celula));

    fila->primeiro->elemento = 0;
    fila->primeiro->prox = NULL;

    fila->ultimo = fila->primeiro;
}


// INSERIR NO FIM
void inserir(Fila* fila, int elemento) {

    // cria uma nova célula
    Celula* nova = (Celula*) malloc(sizeof(Celula));

    nova->elemento = elemento;
    nova->prox = NULL;

    // o último aponta para a nova célula
    fila->ultimo->prox = nova;

    // atualiza o último
    fila->ultimo = nova;
}


// REMOVER DO INÍCIO
int remover(Fila* fila) {

    // fila vazia
    if (fila->primeiro == fila->ultimo) {

        printf("Erro: fila vazia!\n");

        return -1;
    }

    // guarda a célula sentinela antiga
    Celula* tmp = fila->primeiro;

    // primeiro avança
    fila->primeiro = fila->primeiro->prox;

    // pega o elemento removido
    int resp = fila->primeiro->elemento;

    // libera a célula antiga
    free(tmp);

    return resp;
}


// MOSTRAR A FILA
void mostrar(Fila* fila) {

    Celula* i = fila->primeiro->prox;

    printf("[ ");

    while (i != NULL) {

        printf("%d ", i->elemento);

        i = i->prox;
    }

    printf("]\n");
}


// MAIN
int main() {

    Fila fila;

    iniciarFila(&fila);

    inserir(&fila, 10);
    inserir(&fila, 20);
    inserir(&fila, 30);

    printf("Fila inicial:\n");
    mostrar(&fila);

    printf("\nRemovido: %d\n", remover(&fila));

    printf("\nFila depois da remocao:\n");
    mostrar(&fila);

    return 0;
}