#include<stdio.h>

#define MAX_EMERGENCIA 5
#define MAX_PERIODICA 5
#define MAX_BACKGROUND 30

typedef struct {
    int id ;
    int prioridade ; // 0 -10 (0 = maxima prioridade )
} Tarefa ;

typedef struct {
    Tarefa pilha[MAX_EMERGENCIA] ; // pilha de tarefas
    int n; // numero de tarefas na lista
} PilhaEmergencia ;

typedef struct {
    Tarefa fila[MAX_PERIODICA] ; // fila de tarefas
    int primeiro; // posicao do primeiro
    int ultimo; // posicao do ultimo 
} FilaPeriodica ;

typedef struct {
    Tarefa lista[MAX_BACKGROUND] ; // lista de tarefas
    int n; // numero de tarefas na lista
} ListaBackground ;

Tarefa processarTarefa(PilhaEmergencia* p, FilaPeriodica* f, ListaBackground* l){
    // TO DO: IMPLEMENTAR
    // desempilhar uma tarefa da pilha (se houver)
    // OU desinfileirar uma tarefa da fila (se houver)
    // OU retirar a tarefa mais prioritária da lista
}

void promoverTarefa(PilhaEmergencia* p, ListaBackground* l, int id){
    // TO DO: IMPLEMENTAR
    // passar a tarefa de identificador id da lista para a pilha
}

void empilharEmergencia(PilhaEmergencia* p, Tarefa t){
    if (p->n >= MAX_EMERGENCIA){
      printf("Pilha cheia\n");
    }
    else {
      p->pilha[p->n] = t;//empilhando a tarefa na posicao n
      p->n++; // aumentando o contador de acordo com o numero de n
    }    
    // TO DO: IMPLEMENTAR
   // empilhar uma tarefa na pilha
}

Tarefa desempilharEmergencia(PilhaEmergencia* p){
    if(p->n<=0){
 printf("Pilha vazia\n");
    }
    else if (p->n >0){
        Tarefa desempilhada;
        desempilhada = p->pilha[p->n];
      p->n--; //desinfelirei o ultimo elemento
     return p->pilha[p->n];// retornando o ultimo elemento ou seja  valor ja desempilhado


    }  
}

void enfileirarPeriodica(FilaPeriodica* f, Tarefa t){
    if ((f->ultimo+1) % MAX_PERIODICA == f->primeiro){ //confiro se todos os elementos estao prenchidos  e faco com que o ultimo volte para o inicio
      printf("Fila cheia\n");
      return;
    }
    else {  
      f->fila[(f->ultimo+1) % MAX_PERIODICA]=t; // enfilerei a tarefa depois da ultima posicao,e para nao sobrepor eu faco a divisao do max periodica
      f->ultimo = (f->ultimo+1) % MAX_PERIODICA; // atualizando a posicao do ultimo    
    }
}

Tarefa desenfileirarPeriodica(FilaPeriodica* f){
 if(f->primeiro == f->ultimo){ //se o primeiro foi igual ao ultimo, a fila esta vazia
 printf("fila vazia");
 }
 Tarefa t = f->fila[f->primeiro]; // pegando o elemento que esta na posicao do primeiro
f->primeiro =(f->primeiro +1) % MAX_PERIODICA; //atualizo a posicao do primeiro elemento


}

void inserirBackground(ListaBackground* l, Tarefa t){
    // TO DO: IMPLEMENTAR
    // inserir uma tarefa na lista
}

Tarefa removerBackground(ListaBackground* l){
    // TO DO: IMPLEMENTAR
    // remover a tarefa mais prioritaria da lista
}

void imprimirEstruturas(PilhaEmergencia* p, FilaPeriodica* f, ListaBackground* l){
    // TO DO: IMPLEMENTAR
    // printar a pilha, lista e fila
}


void criarTarefa(PilhaEmergencia* p, FilaPeriodica* f, ListaBackground* l){
    Tarefa tarefa;
    printf("ID: ");
    scanf("%d", &tarefa.id);
    printf("Prioridade: ");
    scanf("%d", &tarefa.prioridade);
    printf("1-Inserir na Pilha\n2-Inserir na Fila\n3-Inserir na lista\n");
    int local;
    printf("Onde Inserir: ");
    scanf("%d", &local);
    if(local == 1) empilharEmergencia(p, tarefa);
    else if(local == 2) enfileirarPeriodica(f, tarefa);
    else inserirBackground(l, tarefa);
}

int main(){
    PilhaEmergencia pilha;
    pilha.n = 0;
    FilaPeriodica fila;
    fila.primeiro = 0;
    fila.ultimo = 0;
    ListaBackground lista;
    lista.n = 0;

    int opcao;
    printf("########\n1-Criar Tarefa\n2-Processar Tarefa\n3-Promover Tarefa\n0-Sair\n");
    printf("Entre com a opcao: ");    
    scanf("%d", &opcao);
    while(opcao != 0){
        if(opcao == 1){
            criarTarefa(&pilha, &fila, &lista);
        }else if(opcao == 2){
            Tarefa processada = processarTarefa(&pilha, &fila, &lista);
            printf("Processei tarefa %d\n", processada.id);
        }else if(opcao == 3){
            printf("Qual o ID da tarefa a ser promovida?\n");
            int id;
            scanf("%d", &id);
            promoverTarefa(&pilha, &lista, id);
        }

        imprimirEstruturas(&pilha, &fila, &lista);

        printf("########\n1-Criar Tarefa\n2-Processar Tarefa\n3-Promover Tarefa\n0-Sair\n");
        printf("Entre com a opcao: ");    
        scanf("%d", &opcao);
    }
    return 0;
}