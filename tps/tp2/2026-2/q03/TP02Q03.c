#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#define MAX_TEXTO 256
#define MAX_LINHA 1024
#define MAX_VEICULOS 1000
#define MAX_FORMATADO 2048

typedef struct {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct {
    int id;
    char marca[MAX_TEXTO];
    char modelo[MAX_TEXTO];
    int ano;
    char categoria[MAX_TEXTO];
    char combustivel[8][MAX_TEXTO];
    int quantidadeCombustiveis;
    int cilindros;
    double cilindrada;
    char transmissao[MAX_TEXTO];
    char tracao[MAX_TEXTO];
    double consumoCidade;
    double consumoEstrada;
    double co2;
    bool turbo;
    Data dataRegistro;
} Veiculo;

// Conta manualmente os caracteres até o terminador nulo e devolve o tamanho da string.
int tamanho(const char *s) {
    int n = 0;
    while (s[n] != '\0') n++;
    return n;
}

// Compara as duas strings posição por posição e retorna verdadeiro somente se ambas terminarem juntas.
int iguais(const char *a, const char *b) {
    int i = 0;
    while (a[i] != '\0' && b[i] != '\0' && a[i] == b[i]) i++;
    return a[i] == '\0' && b[i] == '\0';
}

// Compara os modelos ignorando diferenças entre maiúsculas e minúsculas, como exigem os casos públicos.
int comparar(const char *a, const char *b) {
    int i = 0;
    while (a[i] != '\0' && b[i] != '\0') {
        char ca = a[i], cb = b[i];
        if (ca >= 'A' && ca <= 'Z') ca = (char)(ca + ('a' - 'A'));
        if (cb >= 'A' && cb <= 'Z') cb = (char)(cb + ('a' - 'A'));
        if (ca < cb) return -1;
        if (ca > cb) return 1;
        i++;
    }
    if (a[i] == '\0' && b[i] != '\0') return -1;
    if (a[i] != '\0' && b[i] == '\0') return 1;
    return 0;
}

// Copia cada caractere da origem para o destino e acrescenta o terminador nulo ao final.
void copiar(char *destino, const char *origem) {
    int i = 0;
    while (origem[i] != '\0') { destino[i] = origem[i]; i++; }
    destino[i] = '\0';
}

// Percorre a linha e troca a primeira quebra de linha encontrada pelo terminador nulo.
void removerQuebra(char *s) {
    int i = 0;
    while (s[i] != '\0') {
        if (s[i] == '\n' || s[i] == '\r') { s[i] = '\0'; return; }
        i++;
    }
}

// Converte texto em inteiro acumulando cada algarismo e considerando um possível sinal negativo.
int paraInteiro(const char *s) {
    int sinal = 1, i = 0, valor = 0;
    if (s[0] == '-') { sinal = -1; i = 1; }
    while (s[i] >= '0' && s[i] <= '9') {
        valor = valor * 10 + (s[i] - '0');
        i++;
    }
    return valor * sinal;
}

// Converte texto em double, separando manualmente as partes inteira e fracionária.
double paraReal(const char *s) {
    int sinal = 1, i = 0, depois = 0;
    double valor = 0.0, divisor = 1.0;
    if (s[0] == '-') { sinal = -1; i = 1; }
    while (s[i] != '\0') {
        if (s[i] == '.') depois = 1;
        else if (!depois) valor = valor * 10.0 + (s[i] - '0');
        else { divisor *= 10.0; valor += (s[i] - '0') / divisor; }
        i++;
    }
    return valor * sinal;
}

// Percorre o texto e copia cada campo para a matriz sempre que encontra o separador informado.
int separar(const char *s, char separador, char partes[][MAX_TEXTO], int maximo) {
    int quantidade = 0, coluna = 0, i = 0;
    while (quantidade < maximo) {
        if (s[i] == separador || s[i] == '\0') {
            partes[quantidade][coluna] = '\0';
            quantidade++;
            coluna = 0;
            if (s[i] == '\0') break;
        } else if (coluna < MAX_TEXTO - 1) {
            partes[quantidade][coluna++] = s[i];
        }
        i++;
    }
    return quantidade;
}

// Divide a data AAAA-MM-DD, converte as três partes e devolve a struct Data preenchida.
Data parseData(const char *s) {
    char partes[3][MAX_TEXTO];
    separar(s, '-', partes, 3);
    Data d;
    d.ano = paraInteiro(partes[0]);
    d.mes = paraInteiro(partes[1]);
    d.dia = paraInteiro(partes[2]);
    return d;
}

// Separa os quinze campos do CSV, converte cada tipo e devolve um Veiculo alocado dinamicamente.
Veiculo *parseVeiculo(const char *s) {
    char c[15][MAX_TEXTO];
    Veiculo *v = (Veiculo *)malloc(sizeof(Veiculo));
    separar(s, ',', c, 15);
    v->id = paraInteiro(c[0]);
    copiar(v->marca, c[1]);
    copiar(v->modelo, c[2]);
    v->ano = paraInteiro(c[3]);
    copiar(v->categoria, c[4]);
    v->quantidadeCombustiveis = separar(c[5], ';', v->combustivel, 8);
    v->cilindros = paraInteiro(c[6]);
    v->cilindrada = paraReal(c[7]);
    copiar(v->transmissao, c[8]);
    copiar(v->tracao, c[9]);
    v->consumoCidade = paraReal(c[10]);
    v->consumoEstrada = paraReal(c[11]);
    v->co2 = paraReal(c[12]);
    v->turbo = iguais(c[13], "true");
    v->dataRegistro = parseData(c[14]);
    return v;
}

// Acrescenta um caractere no final do buffer e mantém o terminador nulo atualizado.
void adicionarChar(char *buffer, int *n, char c) {
    buffer[*n] = c;
    (*n)++;
    buffer[*n] = '\0';
}

// Copia um texto para o final do buffer usando adicionarChar para cada caractere.
void adicionarTexto(char *buffer, int *n, const char *texto) {
    int i = 0;
    while (texto[i] != '\0') adicionarChar(buffer, n, texto[i++]);
}

// Converte o inteiro em algarismos na ordem inversa e depois os acrescenta corretamente ao buffer.
void adicionarInteiro(char *buffer, int *n, long valor) {
    char digitos[32];
    int quantidade = 0;
    if (valor == 0) { adicionarChar(buffer, n, '0'); return; }
    if (valor < 0) { adicionarChar(buffer, n, '-'); valor = -valor; }
    while (valor > 0) { digitos[quantidade++] = (char)('0' + valor % 10); valor /= 10; }
    while (quantidade > 0) adicionarChar(buffer, n, digitos[--quantidade]);
}

// Arredonda o valor, separa parte inteira e fração e escreve a quantidade fixa de casas decimais.
void adicionarDecimal(char *buffer, int *n, double valor, int casas) {
    long fator = 1;
    int i;
    for (i = 0; i < casas; i++) fator *= 10;
    long total = (long)(valor * fator + 0.500000001);
    adicionarInteiro(buffer, n, total / fator);
    adicionarChar(buffer, n, '.');
    long fracao = total % fator;
    long divisor = fator / 10;
    while (divisor > 0) {
        adicionarChar(buffer, n, (char)('0' + (fracao / divisor) % 10));
        divisor /= 10;
    }
}

// Monta a data em DD/MM/AAAA e adiciona zeros à esquerda no dia e no mês quando necessário.
void formatData(Data d, char *buffer) {
    int n = 0;
    buffer[0] = '\0';
    if (d.dia < 10) adicionarChar(buffer, &n, '0');
    adicionarInteiro(buffer, &n, d.dia);
    adicionarChar(buffer, &n, '/');
    if (d.mes < 10) adicionarChar(buffer, &n, '0');
    adicionarInteiro(buffer, &n, d.mes);
    adicionarChar(buffer, &n, '/');
    adicionarInteiro(buffer, &n, d.ano);
}

// Monta no buffer todos os campos do veículo exatamente no formato exigido pela saída.
void formatVeiculo(Veiculo v, char *buffer) {
    int n = 0, i;
    char data[32];
    buffer[0] = '\0';
    formatData(v.dataRegistro, data);
    adicionarChar(buffer, &n, '[');
    adicionarInteiro(buffer, &n, v.id);
    adicionarTexto(buffer, &n, " ## ");
    adicionarTexto(buffer, &n, v.marca);
    adicionarTexto(buffer, &n, " ## ");
    adicionarTexto(buffer, &n, v.modelo);
    adicionarTexto(buffer, &n, " ## ");
    adicionarInteiro(buffer, &n, v.ano);
    adicionarTexto(buffer, &n, " ## ");
    adicionarTexto(buffer, &n, v.categoria);
    adicionarTexto(buffer, &n, " ## [");
    for (i = 0; i < v.quantidadeCombustiveis; i++) {
        if (i > 0) adicionarChar(buffer, &n, ',');
        adicionarTexto(buffer, &n, v.combustivel[i]);
    }
    adicionarTexto(buffer, &n, "] ## ");
    adicionarInteiro(buffer, &n, v.cilindros);
    adicionarTexto(buffer, &n, " ## ");
    adicionarDecimal(buffer, &n, v.cilindrada, 1);
    adicionarTexto(buffer, &n, " ## ");
    adicionarTexto(buffer, &n, v.transmissao);
    adicionarTexto(buffer, &n, " ## ");
    adicionarTexto(buffer, &n, v.tracao);
    adicionarTexto(buffer, &n, " ## ");
    adicionarDecimal(buffer, &n, v.consumoCidade, 2);
    adicionarTexto(buffer, &n, " ## ");
    adicionarDecimal(buffer, &n, v.consumoEstrada, 2);
    adicionarTexto(buffer, &n, " ## ");
    adicionarDecimal(buffer, &n, v.co2, 1);
    adicionarTexto(buffer, &n, " ## ");
    adicionarTexto(buffer, &n, v.turbo ? "true" : "false");
    adicionarTexto(buffer, &n, " ## ");
    adicionarTexto(buffer, &n, data);
    adicionarChar(buffer, &n, ']');
}

// Lê todas as linhas do CSV, cria os veículos e informa pelo ponteiro n a quantidade carregada.
Veiculo *lerCsv(const char *caminhoArquivo, int *n) {
    FILE *arquivo = fopen(caminhoArquivo, "r");
    if (arquivo == NULL) arquivo = fopen("../veiculos.csv", "r");
    if (arquivo == NULL) arquivo = fopen("veiculos.csv", "r");
    if (arquivo == NULL) return NULL;
    Veiculo *base = (Veiculo *)malloc(MAX_VEICULOS * sizeof(Veiculo));
    char linha[MAX_LINHA];
    *n = 0;
    fgets(linha, MAX_LINHA, arquivo);
    while (fgets(linha, MAX_LINHA, arquivo) != NULL) {
        removerQuebra(linha);
        Veiculo *veiculo = parseVeiculo(linha);
        base[(*n)++] = *veiculo;
        free(veiculo);
    }
    fclose(arquivo);
    return base;
}

// Percorre o vetor sequencialmente e devolve o endereço do veículo que possui o ID informado.
Veiculo *buscarPorId(Veiculo *base, int n, int id) {
    int i;
    for (i = 0; i < n; i++) if (base[i].id == id) return &base[i];
    return NULL;
}

// Formata o veículo em um buffer e o escreve na saída padrão.
void imprimir(Veiculo v) {
    char buffer[MAX_FORMATADO];
    formatVeiculo(v, buffer);
    printf("%s\n", buffer);
}

// Seleciona os veículos informados, aplica seleção pelo modelo e imprime o vetor ordenado.
int main(void) {
    int quantidadeBase, n = 0, i, j;
    Veiculo *base = lerCsv("/tmp/veiculos.csv", &quantidadeBase);
    Veiculo escolhidos[MAX_VEICULOS], troca;
    char linha[MAX_LINHA];
    while (fgets(linha, MAX_LINHA, stdin) != NULL) {
        removerQuebra(linha);
        int id = paraInteiro(linha);
        if (id == -1) break;
        escolhidos[n++] = *buscarPorId(base, quantidadeBase, id);
    }
    for (i = 0; i < n - 1; i++) {
        int menor = i;
        for (j = i + 1; j < n; j++)
            if (comparar(escolhidos[j].modelo, escolhidos[menor].modelo) < 0) menor = j;
        troca = escolhidos[i]; escolhidos[i] = escolhidos[menor]; escolhidos[menor] = troca;
    }
    for (i = 0; i < n; i++) imprimir(escolhidos[i]);
    free(base);
    return 0;
}


