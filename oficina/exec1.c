#include <stdio.h>

#define MAX_EMERGENCIA 5
#define MAX_PERIODICA 5
#define MAX_BACKGROUND 30

typedef struct {
    int id;
    int prioridade;
} Tarefa;

typedef struct {
    Tarefa pilha[MAX_EMERGENCIA];
    int n;
} PilhaEmergencia;

typedef struct {
    Tarefa fila[MAX_PERIODICA];
    int primeiro;
    int ultimo;
} FilaPeriodica;

typedef struct {
    Tarefa lista[MAX_BACKGROUND];
    int n;
} ListaBackground;

Tarefa processarTarefa(PilhaEmergencia *p, FilaPeriodica *f, ListaBackground *l) {
    if (p->n > 0) return desempilharEmergencia(p);
    else if (f->primeiro != f->ultimo) return desenfileirarPeriodica(f);
    else if (l->n > 0) return removerBackground(l);
}

void promoverTarefa(PilhaEmergencia *p, ListaBackground *l, int id) {
    for (int i = 0; i < l->n; i++) {
        if (l->lista[i].id == id) {
            Tarefa t = l->lista[i];
            for (int j = i; j < l->n - 1; j++) l->lista[j] = l->lista[j+1];
            l->n--;
            empilharEmergencia(p, t);
            return;
        }
    }
}

void empilharEmergencia(PilhaEmergencia *p, Tarefa t) {
    if (p->n >= MAX_EMERGENCIA) {
        printf("Pilha cheia!\n");
        return;
    } else {
        p->pilha[p->n] = t;
        p->n++;
    }
}

Tarefa desempilharEmergencia(PilhaEmergencia *p) {
    if (p->n == 0) {
        printf("Pilha vazia!\n");
        return;
    } else return p->pilha[--p->n];
}

void enfileirarPeriodica(FilaPeriodica *f, Tarefa t) {
    if ((f->ultimo + 1) % MAX_PERIODICA == f->primeiro) {
        printf("Fila cheia!\n");
        return;
    } else {
        f->fila[(f->ultimo+1) % MAX_PERIODICA] = t;
        f->ultimo = (f->ultimo+1) % MAX_PERIODICA;
    }
}

Tarefa desenfileirarPeriodica(FilaPeriodica *f) {
    if (f->primeiro == f->ultimo) {
        printf("Fila vazia!\n");
        return;
    } else {
        Tarefa desenfileirada = f->fila[f->primeiro];
        f->primeiro = (f->primeiro + 1) % MAX_PERIODICA;
        return desenfileirada;
    }
}

void inserirBackground(ListaBackground *l, Tarefa t) {
    if (l->n >= MAX_BACKGROUND) {
        printf("Lista cheia!\n");
        return;
    }
    else {
        l->lista[l->n] = t;
        l->n++;
    }
}

Tarefa removerBackground(ListaBackground *l) {
    if (l->n == 0) {
        printf("Lista vazia!\n");
        return;
    } else {
        for (int i = 0; i < l->n - 1; i++) l->lista[i] = l->lista[i+1];
        l->n--;
        return l->lista[0];
    }
}

void imprimirEstruturas(PilhaEmergencia *p, FilaPeriodica *f, ListaBackground *l) {
    if (p->n == 0 && f->primeiro == f->ultimo && l->n == 0) {
        printf("Nenhuma tarefa pendente\n");
        return;
    }
    else if (p->n > 0) {
        printf("Pilha de Emergencia: ");
        for (int i = p->n - 1; i >= 0; i--) printf("%d ", p->pilha[i].id);
    }
    else if (f->primeiro != f->ultimo) {
        printf("Fila Periodica: ");
        for (int i = f->primeiro; i < f->ultimo; i++) printf("%d ", f->fila[i].id);
    }
    else if (l->n > 0) {
        printf("Lista Background: ");
        for (int i = 0; i < l->n; i++) printf("%d ", l->lista[i].id);
    }
}

void criarTarefa(PilhaEmergencia *p, FilaPeriodica *f, ListaBackground *l) {
    Tarefa tarefa;
    printf("ID: "); scanf("%d", &tarefa.id);
    printf("Prioridade: "); scanf("%d", &tarefa.prioridade);
    printf("1 - Inserir na Pilha\n2 - Inserir na Fila\n3 - Inserir na lista\n");
    int op; scanf("%d", &op);
    switch (op) {
        case 1: empilharEmergencia(p, tarefa); break;
        case 2: enfileirarPeriodica(f, tarefa); break;
        case 3: inserirBackground(l, tarefa); break;
        default: printf("Opcao invalida!\n"); return;
    }
}

int main() {
    PilhaEmergencia pilha;
    pilha.n = 0;
    FilaPeriodica fila;
    fila.primeiro = 0;
    fila.ultimo = 0;
    ListaBackground lista;
    lista.n = 0;

    int op;
    do {
        printf("\nMENU\n");
        printf("\n1 - Criar Tarefa");
        printf("\n2 - Processar Tarefa");
        printf("\n3 - Promover Tarefa");
        printf("\n0 - Sair\n");
        printf("Entre com a opcao: ");
        scanf("%d", &op);
        switch (op) {
            case 1: criarTarefa(&pilha, &fila, &lista); break;
            case 2:
                Tarefa processada = processarTarefa(&pilha, &fila, &lista);
                printf("Processei tarefa %d\n", processada.id);
                break;
            case 3:
                printf("Qual o ID da tarefa a ser promovida?\n");
                int id; scanf("%d", &id);
                promoverTarefa(&pilha, &lista, id);
                break;
            case 0: printf("Saindo...\n"); break;
            default: printf("Opcao invalida!\n"); break;
        }
        imprimirEstruturas(&pilha, &fila, &lista);
    } while (op != 0);
    return 0;
}