 /*
 * PilhaCompleta.c
 * Versão em C do PilhaCompleta.java
 * Implementa uma pilha ligada (lista encadeada) com operações básicas:
 * inserir (push), remover (pop), soma dos elementos e mostrar.
 */

#include <stdio.h>
#include <stdlib.h>

typedef struct Celula {
    int elemento;
    struct Celula* prox;
} Celula;

typedef struct Pilha {
    Celula* topo;
} Pilha;

Pilha* criarPilha(void) {
    Pilha* p = (Pilha*) malloc(sizeof(Pilha));
    if (!p) {
        fprintf(stderr, "Erro de alocacao\n");
        exit(EXIT_FAILURE);
    }
    p->topo = NULL;
    return p;
}

void inserir(Pilha* p, int x) {
    Celula* tmp = (Celula*) malloc(sizeof(Celula));
    if (!tmp) {
        fprintf(stderr, "Erro de alocacao\n");
        exit(EXIT_FAILURE);
    }
    tmp->elemento = x;
    tmp->prox = p->topo;
    p->topo = tmp;
}

int remover(Pilha* p) {
    if (p->topo == NULL) {
        return -1; // mesmo comportamento do Java
    }
    Celula* tmp = p->topo;
    int valor = tmp->elemento;
    p->topo = tmp->prox;
    free(tmp);
    return valor;
}

int soma(Pilha* p) {
    int s = 0;
    for (Celula* i = p->topo; i != NULL; i = i->prox) {
        s += i->elemento;
    }
    return s;
}

void mostrar(Pilha* p) {
    printf("[");
    for (Celula* i = p->topo; i != NULL; i = i->prox) {
        printf("%d", i->elemento);
        if (i->prox != NULL) printf(" ");
    }
    printf("]\n");
}

void liberarPilha(Pilha* p) {
    Celula* cur = p->topo;
    while (cur != NULL) {
        Celula* nxt = cur->prox;
        free(cur);
        cur = nxt;
    }
    free(p);
}

int main(void) {
    Pilha* pilha = criarPilha();
    int numero;

    printf("digite o numero que vc quer inserir na pilha : (para sair digite -1)\n");
    if (scanf("%d", &numero) != 1) {
        fprintf(stderr, "Entrada invalida\n");
        liberarPilha(pilha);
        return EXIT_FAILURE;
    }

    while (numero != -1) {
        inserir(pilha, numero);
        if (scanf("%d", &numero) != 1) break;
    }

    printf("elementos da pilha : \n");
    mostrar(pilha);
    printf("soma dos elementos da pilha : %d\n", soma(pilha));

    liberarPilha(pilha);
    return 0;
}
