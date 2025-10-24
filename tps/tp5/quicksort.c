#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>

#define MAX_LINE_SIZE 4096
#define MAX_FIELD_SIZE 1024
#define MAX_GAMES 5000

// SUBSTITUA pela sua matrícula
#define MATRICULA "891378"

// Contadores globais
static long comparisons = 0;
static long movements = 0;

// ========== ESTRUTURA DO JOGO ==========
typedef struct {
    int id;
    char* name;
    char* releaseDate;
    int estimatedOwners;
    float price;
    char** supportedLanguages;
    int numSupportedLanguages;
    int metacriticScore;
    float userScore;
    int achievements;
    char** publishers;
    int numPublishers;
    char** developers;
    int numDevelopers;
    char** categories;
    int numCategories;
    char** genres;
    int numGenres;
    char** tags;
    int numTags;
} Game;

// ========== PROTÓTIPOS DE FUNÇÕES ==========
void freeGameContents(Game* g);
int parseInt(const char* s);
float parseFloat(const char* s);
void formatarData(char* dest, const char* data);
void formatarColchetes(char*** dest_array, int* count, const char* campo);
void splitManual(char*** dest_fields, int* num_fields, const char* linha);
void printGame(const Game* g);
int compareDates(const char* date1, const char* date2);
int compareGames(const Game* g1, const Game* g2);
void quickSort(Game* arr, int left, int right);
void swap(Game* arr, int i, int j);

// ========== FUNÇÃO DE COMPARAÇÃO DE DATAS ==========
// Compara duas datas no formato dd/MM/yyyy
// Retorna: -1 se date1 < date2, 0 se iguais, 1 se date1 > date2
int compareDates(const char* date1, const char* date2) {
    int dia1, mes1, ano1, dia2, mes2, ano2;
    
    // Parse date1 (formato: dd/MM/yyyy)
    sscanf(date1, "%d/%d/%d", &dia1, &mes1, &ano1);
    
    // Parse date2 (formato: dd/MM/yyyy)
    sscanf(date2, "%d/%d/%d", &dia2, &mes2, &ano2);
    
    // Compara ano primeiro
    if (ano1 < ano2) return -1;
    if (ano1 > ano2) return 1;
    
    // Anos iguais, compara mês
    if (mes1 < mes2) return -1;
    if (mes1 > mes2) return 1;
    
    // Meses iguais, compara dia
    if (dia1 < dia2) return -1;
    if (dia1 > dia2) return 1;
    
    // Datas iguais
    return 0;
}

// ========== FUNÇÃO DE COMPARAÇÃO DE JOGOS ==========
// Compara dois jogos: primeiro por Release_date, depois por AppID
// Retorna: -1 se g1 < g2, 0 se iguais, 1 se g1 > g2
int compareGames(const Game* g1, const Game* g2) {
    comparisons++; // Incrementa contador de comparações
    
    // Compara datas de lançamento
    int dateCmp = compareDates(g1->releaseDate, g2->releaseDate);
    
    if (dateCmp != 0) {
        return dateCmp; // Datas diferentes
    }
    
    // Datas iguais, desempata por AppID (ordem crescente)
    if (g1->id < g2->id) return -1;
    if (g1->id > g2->id) return 1;
    
    return 0; // Completamente iguais
}

// ========== FUNÇÃO DE TROCA (SWAP) ==========
// Troca dois elementos do array
void swap(Game* arr, int i, int j) {
    if (i == j) return; // Não precisa trocar se forem o mesmo
    
    Game temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
    
    movements += 3; // Conta as 3 movimentações (temp = arr[i], arr[i] = arr[j], arr[j] = temp)
}

// ========== ALGORITMO QUICKSORT ==========
// Ordena o array de jogos usando Quicksort
// Critério: Release_date (crescente), desempate por AppID (crescente)
void quickSort(Game* arr, int left, int right) {
    // Caso base: se left >= right, subarray tem 0 ou 1 elemento (já ordenado)
    if (left >= right) return;
    
    // Escolhe o pivô (elemento do meio)
    int mid = (left + right) / 2;
    Game pivot = arr[mid];
    
    // Índices de particionamento
    int i = left;
    int j = right;
    
    // Particiona o array
    while (i <= j) {
        // Encontra elemento à esquerda que deve ir para a direita
        while (compareGames(&arr[i], &pivot) < 0) {
            i++;
        }
        
        // Encontra elemento à direita que deve ir para a esquerda
        while (compareGames(&arr[j], &pivot) > 0) {
            j--;
        }
        
        // Troca os elementos se os índices não se cruzaram
        if (i <= j) {
            swap(arr, i, j);
            i++;
            j--;
        }
    }
    
    // Recursão nas duas metades
    if (left < j) quickSort(arr, left, j);   // Ordena metade esquerda
    if (i < right) quickSort(arr, i, right); // Ordena metade direita
}

// ========== FUNÇÕES AUXILIARES (PARSE E FORMATAÇÃO) ==========

// Converte string para inteiro
int parseInt(const char* s) {
    int resultado = 0;
    for (int i = 0; s[i] != '\0'; i++) {
        if (isdigit((unsigned char)s[i])) {
            resultado = resultado * 10 + (s[i] - '0');
        }
    }
    return resultado;
}

// Converte string para float
float parseFloat(const char* s) {
    float resultado = 0.0f;
    bool ponto = false;
    float divisor = 10.0f;

    for (int i = 0; s[i] != '\0'; i++) {
        char c = s[i];
        if (isdigit((unsigned char)c)) {
            int digito = c - '0';
            if (!ponto) resultado = resultado * 10 + digito;
            else {
                resultado = resultado + digito / divisor;
                divisor *= 10;
            }
        } else if (c == '.') ponto = true;
    }
    return resultado;
}

// Formata data de "MMM d, yyyy" para "dd/MM/yyyy"
void formatarData(char* dest, const char* data) {
    char temp_data[100];
    strncpy(temp_data, data, sizeof(temp_data) - 1);
    temp_data[sizeof(temp_data) - 1] = '\0';
    
    // Remove aspas se existirem
    if (temp_data[0] == '"') {
        memmove(temp_data, temp_data + 1, strlen(temp_data));
        temp_data[strlen(temp_data) - 1] = '\0';
    }

    // Parse da data
    char* mes_str = strtok(temp_data, " ,");
    char* dia_str = strtok(NULL, " ,");
    char* ano_str = strtok(NULL, " ,");

    if (!mes_str || !dia_str || !ano_str) {
        strcpy(dest, "01/01/0000");
        return;
    }
    
    // Converte mês de string para número
    char numeroMes[3];
    if (strcmp(mes_str, "Jan") == 0) strcpy(numeroMes, "01");
    else if (strcmp(mes_str, "Feb") == 0) strcpy(numeroMes, "02");
    else if (strcmp(mes_str, "Mar") == 0) strcpy(numeroMes, "03");
    else if (strcmp(mes_str, "Apr") == 0) strcpy(numeroMes, "04");
    else if (strcmp(mes_str, "May") == 0) strcpy(numeroMes, "05");
    else if (strcmp(mes_str, "Jun") == 0) strcpy(numeroMes, "06");
    else if (strcmp(mes_str, "Jul") == 0) strcpy(numeroMes, "07");
    else if (strcmp(mes_str, "Aug") == 0) strcpy(numeroMes, "08");
    else if (strcmp(mes_str, "Sep") == 0) strcpy(numeroMes, "09");
    else if (strcmp(mes_str, "Oct") == 0) strcpy(numeroMes, "10");
    else if (strcmp(mes_str, "Nov") == 0) strcpy(numeroMes, "11");
    else if (strcmp(mes_str, "Dec") == 0) strcpy(numeroMes, "12");
    else strcpy(numeroMes, "01");

    sprintf(dest, "%02d/%s/%s", atoi(dia_str), numeroMes, ano_str);
}

// Formata listas entre colchetes (ex: [item1, item2, item3])
void formatarColchetes(char*** dest_array, int* count, const char* campo) {
    char temp_campo[MAX_FIELD_SIZE];
    strncpy(temp_campo, campo, MAX_FIELD_SIZE - 1);
    temp_campo[MAX_FIELD_SIZE - 1] = '\0';
    
    char* start = temp_campo;

    // Remove aspas externas
    if (strlen(start) > 1 && start[0] == '"' && start[strlen(start) - 1] == '"') {
        start[strlen(start) - 1] = '\0';
        start++;
    }
    // Remove colchetes externos
    if (strlen(start) > 1 && start[0] == '[' && start[strlen(start) - 1] == ']') {
        start[strlen(start) - 1] = '\0';
        start++;
    }

    // Conta elementos
    *count = 0;
    if (strlen(start) > 0) {
        *count = 1;
        for (int i = 0; start[i] != '\0'; i++) {
            if (start[i] == ',') (*count)++;
        }
    }

    if (*count == 0) {
        *dest_array = NULL;
        return;
    }

    *dest_array = (char**)malloc(*count * sizeof(char*));
    if (!*dest_array) return;

    // Separa elementos
    char* token = strtok(start, ",");
    int index = 0;
    while (token != NULL && index < *count) {
        // Remove espaços e aspas simples
        while (isspace((unsigned char)*token)) token++;
        if (*token == '\'') token++;

        char* end = token + strlen(token) - 1;
        while (end >= token && (isspace((unsigned char)*end) || *end == '\'')) {
            *end = '\0';
            end--;
        }

        (*dest_array)[index++] = strdup(token);
        token = strtok(NULL, ",");
    }
}

// Faz parse manual de linha CSV (trata aspas e colchetes)
void splitManual(char*** dest_fields, int* num_fields, const char* linha) {
    *num_fields = 0;
    bool dentroAspas = false, dentroColchete = false;

    // Conta campos
    for (int i = 0; linha[i] != '\0'; i++) {
        if (linha[i] == '"') dentroAspas = !dentroAspas;
        else if (linha[i] == '[') dentroColchete = true;
        else if (linha[i] == ']') dentroColchete = false;
        else if (linha[i] == ',' && !dentroAspas && !dentroColchete) (*num_fields)++;
    }
    (*num_fields)++;

    *dest_fields = (char**)malloc(*num_fields * sizeof(char*));

    // Separa campos
    char temp_field[MAX_FIELD_SIZE];
    int field_index = 0, char_index = 0;
    dentroAspas = false;
    dentroColchete = false;

    for (int i = 0; linha[i] != '\0'; i++) {
        char c = linha[i];
        if (c == '\n' || c == '\r') continue;

        if (c == '"') dentroAspas = !dentroAspas;
        else if (c == '[') dentroColchete = true;
        else if (c == ']') dentroColchete = false;

        if (c == ',' && !dentroAspas && !dentroColchete) {
            temp_field[char_index] = '\0';
            (*dest_fields)[field_index++] = strdup(temp_field);
            char_index = 0;
        } else {
            temp_field[char_index++] = c;
        }
    }
    temp_field[char_index] = '\0';
    (*dest_fields)[field_index] = strdup(temp_field);
}

// Libera memória alocada para um jogo
void freeGameContents(Game* g) {
    if (!g) return;

    free(g->name);
    free(g->releaseDate);

    for (int i = 0; i < g->numSupportedLanguages; i++) free(g->supportedLanguages[i]);
    free(g->supportedLanguages);

    for (int i = 0; i < g->numPublishers; i++) free(g->publishers[i]);
    free(g->publishers);

    for (int i = 0; i < g->numDevelopers; i++) free(g->developers[i]);
    free(g->developers);

    for (int i = 0; i < g->numCategories; i++) free(g->categories[i]);
    free(g->categories);

    for (int i = 0; i < g->numGenres; i++) free(g->genres[i]);
    free(g->genres);

    for (int i = 0; i < g->numTags; i++) free(g->tags[i]);
    free(g->tags);
}

// Imprime um jogo formatado
void printGame(const Game* g) {
    printf("=> %d ## %s ## %s ## %d ## %.2f ## [", g->id, g->name, g->releaseDate, g->estimatedOwners, g->price);
    
    for (int i = 0; i < g->numSupportedLanguages; i++)
        printf("%s%s", g->supportedLanguages[i], (i == g->numSupportedLanguages - 1) ? "" : ", ");

    printf("] ## %d ## %.1f ## %d ## [", g->metacriticScore, g->userScore, g->achievements);
    
    for (int i = 0; i < g->numPublishers; i++)
        printf("%s%s", g->publishers[i], (i == g->numPublishers - 1) ? "" : ", ");

    printf("] ## [");
    for (int i = 0; i < g->numDevelopers; i++)
        printf("%s%s", g->developers[i], (i == g->numDevelopers - 1) ? "" : ", ");

    printf("] ## [");
    for (int i = 0; i < g->numCategories; i++)
        printf("%s%s", g->categories[i], (i == g->numCategories - 1) ? "" : ", ");

    printf("] ## [");
    for (int i = 0; i < g->numGenres; i++)
        printf("%s%s", g->genres[i], (i == g->numGenres - 1) ? "" : ", ");

    printf("] ## [");
    for (int i = 0; i < g->numTags; i++)
        printf("%s%s", g->tags[i], (i == g->numTags - 1) ? "" : ", ");
    
    printf("] ##\n");
}

// ========== FUNÇÃO PRINCIPAL ==========
int main() {
    // Abre arquivo CSV
    FILE* file = fopen("/tmp/games.csv", "r");
    if (!file) {
        perror("Erro ao abrir /tmp/games.csv");
        return 1;
    }

    // Pula cabeçalho
    char linha[MAX_LINE_SIZE];
    fgets(linha, sizeof(linha), file);

    // Lê todos os jogos do CSV
    Game* games = (Game*)malloc(MAX_GAMES * sizeof(Game));
    int numGames = 0;

    while (fgets(linha, sizeof(linha), file) != NULL && numGames < MAX_GAMES) {
        char** campos = NULL;
        int num_campos = 0;
        splitManual(&campos, &num_campos, linha);
        
        if (num_campos < 14) {
            for (int i = 0; i < num_campos; i++) free(campos[i]);
            free(campos);
            continue;
        }

        // Preenche estrutura do jogo
        Game* g = &games[numGames];
        memset(g, 0, sizeof(Game));
        
        g->id = parseInt(campos[0]);
        g->name = strdup(campos[1]);
        
        // Formata data para dd/MM/yyyy
        char dataFormatada[12];
        formatarData(dataFormatada, campos[2]);
        g->releaseDate = strdup(dataFormatada);
        
        g->estimatedOwners = parseInt(campos[3]);
        g->price = strcmp(campos[4], "Free to Play") == 0 ? 0.0f : parseFloat(campos[4]);
        
        formatarColchetes(&g->supportedLanguages, &g->numSupportedLanguages, campos[5]);
        
        g->metacriticScore = strlen(campos[6]) == 0 ? -1 : parseInt(campos[6]);
        g->userScore = (strlen(campos[7]) == 0 || strcmp(campos[7], "tbd") == 0) ? -1.0f : parseFloat(campos[7]);
        g->achievements = strlen(campos[8]) == 0 ? 0 : parseInt(campos[8]);
        
        formatarColchetes(&g->publishers, &g->numPublishers, campos[9]);
        formatarColchetes(&g->developers, &g->numDevelopers, campos[10]);
        formatarColchetes(&g->categories, &g->numCategories, campos[11]);
        formatarColchetes(&g->genres, &g->numGenres, campos[12]);
        formatarColchetes(&g->tags, &g->numTags, campos[13]);

        for (int i = 0; i < num_campos; i++) free(campos[i]);
        free(campos);
        
        numGames++;
    }
    fclose(file);

    // Mede tempo de execução do Quicksort
    clock_t inicio = clock();
    quickSort(games, 0, numGames - 1);
    clock_t fim = clock();
    
    double tempoExecucao = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000.0; // em milissegundos

    // Imprime jogos ordenados
    for (int i = 0; i < numGames; i++) {
        printGame(&games[i]);
    }

    // Grava arquivo de log
    char logFilename[100];
    sprintf(logFilename, "%s_quicksort.txt", MATRICULA);
    FILE* logFile = fopen(logFilename, "w");
    if (logFile) {
        fprintf(logFile, "%s\t%ld\t%ld\t%.2fms\n", MATRICULA, comparisons, movements, tempoExecucao);
        fclose(logFile);
    }

    // Libera memória
    for (int i = 0; i < numGames; i++) {
        freeGameContents(&games[i]);
    }
    free(games);

    return 0;
}
