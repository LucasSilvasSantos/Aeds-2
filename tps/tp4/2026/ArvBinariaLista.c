#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

static const char* MATRICULA = "891378";
static long long comparacoesPesquisa = 0;

typedef struct { int ano; int mes; int dia; } Data;
typedef struct { int hora; int minuto; } Hora;

typedef struct {
    int id;
    char* nome;
    char* cidade;
    int capacidade;
    double avaliacao;
    int n_tipos_cozinha;
    char** tipos_cozinha;
    int faixa_preco;
    Hora horario_abertura;
    Hora horario_fechamento;
    Data data_abertura;
    bool aberto;
} Restaurante;

typedef struct {
    int tamanho;
    Restaurante** restaurantes;
} Colecao_Restaurantes;

Data parse_data(char* s) {
    Data d; d.ano = 0; d.mes = 0; d.dia = 0;
    int parte = 0;
    for (int i = 0; s[i]; i++) {
        char c = s[i];
        if (c == '-') parte++;
        else if (c >= '0' && c <= '9') {
            int dig = c - '0';
            if (parte == 0) d.ano = d.ano * 10 + dig;
            else if (parte == 1) d.mes = d.mes * 10 + dig;
            else d.dia = d.dia * 10 + dig;
        }
    }
    return d;
}

void formatar_data(Data* data, char* buffer) {
    sprintf(buffer, "%02d/%02d/%04d", data->dia, data->mes, data->ano);
}

Hora parse_hora(char* s) {
    Hora h; h.hora = 0; h.minuto = 0;
    int parte = 0;
    for (int i = 0; s[i]; i++) {
        char c = s[i];
        if (c == ':') parte++;
        else if (c >= '0' && c <= '9') {
            int dig = c - '0';
            if (parte == 0) h.hora = h.hora * 10 + dig;
            else h.minuto = h.minuto * 10 + dig;
        }
    }
    return h;
}

void formatar_hora(Hora* hora, char* buffer) {
    sprintf(buffer, "%02d:%02d", hora->hora, hora->minuto);
}

static int parse_int(char* s) {
    int r = 0;
    for (int i = 0; s[i]; i++)
        if (s[i] >= '0' && s[i] <= '9') r = r * 10 + (s[i] - '0');
    return r;
}

static double parse_double(char* s) {
    double r = 0; bool dec = false; double div = 10.0;
    for (int i = 0; s[i]; i++) {
        char c = s[i];
        if (c >= '0' && c <= '9') {
            int dig = c - '0';
            if (!dec) r = r * 10 + dig;
            else { r += dig / div; div *= 10; }
        } else if (c == '.') dec = true;
    }
    return r;
}

static char* duplicar_string(char* s) {
    int len = 0;
    while (s[len]) len++;
    char* novo = (char*) malloc((len + 1) * sizeof(char));
    for (int i = 0; i <= len; i++) novo[i] = s[i];
    return novo;
}

Restaurante* parse_restaurante(char* s) {
    Restaurante* r = (Restaurante*) malloc(sizeof(Restaurante));
    char campos[10][512];
    int campo = 0, bi = 0;
    for (int i = 0; s[i] && s[i] != '\n' && s[i] != '\r'; i++) {
        if (s[i] == ',') { campos[campo][bi] = '\0'; campo++; bi = 0; }
        else campos[campo][bi++] = s[i];
    }
    campos[campo][bi] = '\0';
    r->id = parse_int(campos[0]);
    r->nome = duplicar_string(campos[1]);
    r->cidade = duplicar_string(campos[2]);
    r->capacidade = parse_int(campos[3]);
    r->avaliacao = parse_double(campos[4]);
    r->n_tipos_cozinha = 1;
    for (int i = 0; campos[5][i]; i++) if (campos[5][i] == ';') r->n_tipos_cozinha++;
    r->tipos_cozinha = (char**) malloc(r->n_tipos_cozinha * sizeof(char*));
    int ti = 0, ci = 0; char tipo_buf[256];
    for (int i = 0; ; i++) {
        char c = campos[5][i];
        if (c == ';' || c == '\0') {
            tipo_buf[ci] = '\0';
            r->tipos_cozinha[ti++] = duplicar_string(tipo_buf);
            ci = 0;
            if (c == '\0') break;
        } else tipo_buf[ci++] = c;
    }
    r->faixa_preco = 0;
    for (int i = 0; campos[6][i]; i++) if (campos[6][i] == '$') r->faixa_preco++;
    int dash_pos = -1;
    for (int i = 0; campos[7][i]; i++) if (campos[7][i] == '-') { dash_pos = i; break; }
    char hor_ab[6], hor_fech[6];
    for (int i = 0; i < dash_pos; i++) hor_ab[i] = campos[7][i];
    hor_ab[dash_pos] = '\0';
    int j = 0;
    for (int i = dash_pos + 1; campos[7][i]; i++) hor_fech[j++] = campos[7][i];
    hor_fech[j] = '\0';
    r->horario_abertura = parse_hora(hor_ab);
    r->horario_fechamento = parse_hora(hor_fech);
    r->data_abertura = parse_data(campos[8]);
    r->aberto = (campos[9][0] == 't');
    return r;
}

void formatar_restaurante(Restaurante* r, char* buffer) {
    char hor_ab[6], hor_fech[6], data_ab[11];
    formatar_hora(&r->horario_abertura, hor_ab);
    formatar_hora(&r->horario_fechamento, hor_fech);
    formatar_data(&r->data_abertura, data_ab);
    char tipos[512]; int ti = 0;
    for (int i = 0; i < r->n_tipos_cozinha; i++) {
        if (i > 0) tipos[ti++] = ',';
        for (int k = 0; r->tipos_cozinha[i][k]; k++) tipos[ti++] = r->tipos_cozinha[i][k];
    }
    tipos[ti] = '\0';
    char fp[5];
    for (int i = 0; i < r->faixa_preco; i++) fp[i] = '$';
    fp[r->faixa_preco] = '\0';
    sprintf(buffer, "[%d ## %s ## %s ## %d ## %.1f ## [%s] ## %s ## %s-%s ## %s ## %s]",
            r->id, r->nome, r->cidade, r->capacidade, r->avaliacao,
            tipos, fp, hor_ab, hor_fech, data_ab, r->aberto ? "true" : "false");
}

void ler_csv_colecao(Colecao_Restaurantes* colecao, char* path) {
    FILE* f = fopen(path, "r");
    if (!f) return;
    colecao->restaurantes = (Restaurante**) malloc(2000 * sizeof(Restaurante*));
    colecao->tamanho = 0;
    char linha[1024];
    fgets(linha, sizeof(linha), f);
    while (fgets(linha, sizeof(linha), f))
        if (linha[0] != '\n' && linha[0] != '\0')
            colecao->restaurantes[colecao->tamanho++] = parse_restaurante(linha);
    fclose(f);
}

Colecao_Restaurantes* ler_csv() {
    Colecao_Restaurantes* c = (Colecao_Restaurantes*) malloc(sizeof(Colecao_Restaurantes));
    ler_csv_colecao(c, "/tmp/restaurantes.csv");
    return c;
}

/* ---------- Arvore Binaria de Lista (estilo Agenda) ---------- */
/* Nivel 1: BST com chave = primeiro caractere do nome          */
/* Nivel 2: Lista simples encadeada ordenada pelo nome          */

typedef struct Celula {
    Restaurante* r;
    struct Celula* prox;
} Celula;

typedef struct No {
    char chave;         /* primeiro caractere do nome */
    Celula* lista;      /* lista ordenada de restaurantes */
    struct No* esq;
    struct No* dir;
} No;

/* Insere restaurante na lista ordenada pelo nome */
void inserir_lista(No* no, Restaurante* r) {
    Celula* nova = (Celula*) malloc(sizeof(Celula));
    nova->r = r;
    nova->prox = NULL;
    if (no->lista == NULL || strcmp(r->nome, no->lista->r->nome) < 0) {
        nova->prox = no->lista;
        no->lista = nova;
        return;
    }
    Celula* atual = no->lista;
    while (atual->prox != NULL && strcmp(r->nome, atual->prox->r->nome) > 0)
        atual = atual->prox;
    nova->prox = atual->prox;
    atual->prox = nova;
}

No* inserir(No* raiz, Restaurante* r) {
    char chave = r->nome[0];
    if (raiz == NULL) {
        No* novo = (No*) malloc(sizeof(No));
        novo->chave = chave; novo->lista = NULL;
        novo->esq = novo->dir = NULL;
        inserir_lista(novo, r);
        return novo;
    }
    if (chave < raiz->chave) raiz->esq = inserir(raiz->esq, r);
    else if (chave > raiz->chave) raiz->dir = inserir(raiz->dir, r);
    else inserir_lista(raiz, r); /* mesmo primeiro char: insere na lista */
    return raiz;
}

/* Pesquisa por nome: navega BST com nome[0], percorre lista imprimindo nomes */
void pesquisar(No* raiz, char* nome) {
    char caminho[8192];
    int ci = 0;
    /* inicia caminho com "RAIZ" */
    caminho[ci++] = 'R'; caminho[ci++] = 'A'; caminho[ci++] = 'I'; caminho[ci++] = 'Z';

    char chave = nome[0];
    No* atual = raiz;
    while (atual != NULL) {
        if (chave == atual->chave) break;
        else if (chave < atual->chave) {
            caminho[ci++] = ' '; caminho[ci++] = 'E'; caminho[ci++] = 'S'; caminho[ci++] = 'Q';
            atual = atual->esq;
        } else {
            caminho[ci++] = ' '; caminho[ci++] = 'D'; caminho[ci++] = 'I'; caminho[ci++] = 'R';
            atual = atual->dir;
        }
    }

    if (atual == NULL) {
        /* chave nao existe na arvore */
        caminho[ci++] = ' '; caminho[ci++] = 'N'; caminho[ci++] = 'A'; caminho[ci++] = 'O';
        caminho[ci] = '\0'; printf("%s\n", caminho); return;
    }

    /* percorre lista ordenada imprimindo nomes visitados */
    Celula* cel = atual->lista;
    while (cel != NULL) {
        comparacoesPesquisa++;
        /* imprime nome do restaurante percorrido */
        caminho[ci++] = ' ';
        for (int k = 0; cel->r->nome[k]; k++) caminho[ci++] = cel->r->nome[k];

        int cmp = strcmp(nome, cel->r->nome);
        if (cmp == 0) {
            /* encontrado */
            char buf_rest[2048];
            formatar_restaurante(cel->r, buf_rest);
            caminho[ci] = '\0';
            printf("%s SIM %s\n", caminho, buf_rest); return;
        } else if (cmp < 0) {
            /* lista ordenada: elemento viria antes — nao esta na lista */
            break;
        }
        cel = cel->prox;
    }

    caminho[ci++] = ' '; caminho[ci++] = 'N'; caminho[ci++] = 'A'; caminho[ci++] = 'O';
    caminho[ci] = '\0'; printf("%s\n", caminho);
}

void em_ordem(No* raiz) {
    if (!raiz) return;
    em_ordem(raiz->esq);
    Celula* cel = raiz->lista;
    while (cel != NULL) {
        char buffer[2048];
        formatar_restaurante(cel->r, buffer);
        printf("%s\n", buffer);
        cel = cel->prox;
    }
    em_ordem(raiz->dir);
}

int main() {
    Colecao_Restaurantes* colecao = ler_csv();
    No* raiz = NULL;

    int id;
    while (scanf("%d", &id) == 1 && id != -1) {
        for (int i = 0; i < colecao->tamanho; i++) {
            if (colecao->restaurantes[i]->id == id) {
                raiz = inserir(raiz, colecao->restaurantes[i]); break;
            }
        }
    }

    { int c; while ((c = getchar()) != '\n' && c != EOF); }

    clock_t t0 = clock();
    char nome_buf[512];
    while (fgets(nome_buf, sizeof(nome_buf), stdin)) {
        int len = 0;
        while (nome_buf[len] && nome_buf[len] != '\n' && nome_buf[len] != '\r') len++;
        nome_buf[len] = '\0';
        if (len == 0) continue;
        if (strcmp(nome_buf, "FIM") == 0) break;
        pesquisar(raiz, nome_buf);
    }
    clock_t t1 = clock();
    double tempo = ((double)(t1 - t0)) / CLOCKS_PER_SEC * 1000.0;

    em_ordem(raiz);

    FILE* log = fopen("891378_hibrida_arvore_lista.txt", "w");
    fprintf(log, "%s\t%lld\t%.2f\n", MATRICULA, comparacoesPesquisa, tempo);
    fclose(log);

    return 0;
}
