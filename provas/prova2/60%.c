#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <string.h>

/*
 * Versão corrigida do arquivo 60%.c
 * Comandos esperados no input:
 * - PUSH x  -> insere x na pilha
 * - POP     -> remove topo
 * - MIN     -> imprime o menor elemento da pilha
 */

typedef struct Celula {
  int elemento;
  struct Celula* prox;
} Celula;

typedef struct pilha {
  Celula *topo;
} Pilha;

Celula* criarCelula(int x) {
  Celula* nova = (Celula*) malloc(sizeof(Celula));
  if (!nova) {
    fprintf(stderr, "Erro de alocacao\n");
    exit(EXIT_FAILURE);
  }
  nova->elemento = x;
  nova->prox = NULL;
  return nova;
}

Pilha* criarPilha(void) {
  Pilha* nova = (Pilha*) malloc(sizeof(Pilha));
  if (!nova) {
    fprintf(stderr, "Erro de alocacao\n");
    exit(EXIT_FAILURE);
  }
  nova->topo = NULL;
  return nova;
}

void inserir(int x, Pilha* pilha) {
  Celula *tmp = criarCelula(x);
  tmp->prox = pilha->topo;
  pilha->topo = tmp;
}

int remover(Pilha* pilha) {
  if (pilha == NULL || pilha->topo == NULL) return -1; // pilha vazia
  Celula* tmp = pilha->topo;
  int elm = tmp->elemento;
  pilha->topo = tmp->prox;
  tmp->prox = NULL;
  free(tmp);
  return elm;
}

int menor(Pilha* pilha) {
  if (pilha == NULL || pilha->topo == NULL) return INT_MAX;
  int min = INT_MAX;
  for (Celula* i = pilha->topo; i != NULL; i = i->prox) {
    if (i->elemento < min) min = i->elemento;
  }
  return min;
}

int main(void) {
  Pilha* pilha = criarPilha();
  int operacoes;
  int grau;
  char cmd[32];

  if (scanf("%d", &operacoes) != 1) return 0;

  for (int i = 0; i < operacoes; i++) {
    if (scanf("%31s", cmd) != 1) break;

    if (strcmp(cmd, "PUSH") == 0) {
      if (scanf("%d", &grau) != 1) break;
      inserir(grau, pilha);
    } else if (strcmp(cmd, "MIN") == 0) {
      int m = menor(pilha);
      if (m == INT_MAX) printf("EMPTY\n");
      else printf("%d\n", m);
    } else if (strcmp(cmd, "POP") == 0) {
      remover(pilha);
    } else {
      // comando desconhecido: ignorar ou tratar conforme necessário
    }
  }

  // liberar pilha
  Celula* cur = pilha->topo;
  while (cur) {
    Celula* nxt = cur->prox;
    free(cur);
    cur = nxt;
  }
  free(pilha);
  return 0;
}


