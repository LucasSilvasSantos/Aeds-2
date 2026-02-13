#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

#define MAX_LINE_SIZE 4096
#define MAX_FIELD_SIZE 1024
#define MAX_GAMES 5000
#define MATRICULA "891378"

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

// Estrutura da Célula da Pilha
typedef struct Celula {
    Game* elemento;
    struct Celula* prox;
} Celula;

// Variável global do topo da pilha
Celula* topo = NULL;

// Funções da Pilha
Celula* newCelula(Game* g) {
    Celula* nova = (Celula*)malloc(sizeof(Celula));
    nova->elemento = g;
    nova->prox = NULL;
    return nova;
}

void inicializarPilha() {
    topo = NULL;
}

void inserir(Game* g) {
    Celula* tmp = newCelula(g);
    tmp->prox = topo;
    topo = tmp;
    tmp = NULL;
}

Game* remover() {
    if (topo == NULL) {
        fprintf(stderr, "Erro: Pilha vazia!\n");
        exit(1);
    }
    Game* resp = topo->elemento;
    Celula* tmp = topo;
    topo = topo->prox;
    tmp->prox = NULL;
    free(tmp);
    tmp = NULL;
    return resp;
}

void printGame(Game* g, int pos) {
    printf("[%d] => %d ## %s ## %s ## %d ## ", pos, g->id, g->name, 
           g->releaseDate, g->estimatedOwners);
    
    if (g->price == 0.0) 
        printf("0.0 ## ");
    else 
        printf("%g ## ", g->price);
    
    printf("[");
    for (int j = 0; j < g->numSupportedLanguages; j++)
        printf("%s%s", g->supportedLanguages[j], 
               (j == g->numSupportedLanguages - 1) ? "" : ", ");
    printf("]");
    
    printf(" ## %d ## %.1f ## %d ## ", g->metacriticScore, 
           g->userScore, g->achievements);
    
    printf("[");
    for (int j = 0; j < g->numPublishers; j++)
        printf("%s%s", g->publishers[j], 
               (j == g->numPublishers - 1) ? "" : ", ");
    printf("]");
    
    printf(" ## [");
    for (int j = 0; j < g->numDevelopers; j++)
        printf("%s%s", g->developers[j], 
               (j == g->numDevelopers - 1) ? "" : ", ");
    printf("]");
    
    printf(" ## [");
    for (int j = 0; j < g->numCategories; j++)
        printf("%s%s", g->categories[j], 
               (j == g->numCategories - 1) ? "" : ", ");
    printf("]");
    
    printf(" ## [");
    for (int j = 0; j < g->numGenres; j++)
        printf("%s%s", g->genres[j], 
               (j == g->numGenres - 1) ? "" : ", ");
    printf("]");
    
    printf(" ## [");
    for (int j = 0; j < g->numTags; j++)
        printf("%s%s", g->tags[j], 
               (j == g->numTags - 1) ? "" : ", ");
    printf("]");
    
    printf(" ##\n");
}

void mostrarPilhaRec(Celula* atual, int pos) {
    if (atual != NULL) {
        
        mostrarPilhaRec(atual->prox, pos + 1);
        printGame(atual->elemento, pos);
    }
}

void mostrarPilha() {
    mostrarPilhaRec(topo, 0);
}

// Funções auxiliares
void freeGameContents(Game* g);
int parseInt(const char* s);
float parseFloat(const char* s);
void formatarData(char* dest, const char* data);
void formatarColchetes(char*** dest_array, int* count, const char* campo);
void splitManual(char*** dest_fields, int* num_fields, const char* linha);

int parseInt(const char* s) {
    if (!s) return 0;
    int result = 0; 
    int i = 0;
    while (s[i] == ' ' || s[i] == '"') i++;
    while (s[i] >= '0' && s[i] <= '9') { 
        result = result * 10 + (s[i] - '0'); 
        i++; 
    }
    return result;
}

float parseFloat(const char* s) {
    if (!s) return 0.0f;
    char buf[MAX_FIELD_SIZE]; 
    int bi = 0;
    for (int i = 0; s[i] != '\0' && bi < MAX_FIELD_SIZE-1; i++) {
        char c = s[i]; 
        if ((c >= '0' && c <= '9') || c == '.' || c == ',') 
            buf[bi++] = c;
    }
    buf[bi] = '\0'; 
    for (int i = 0; buf[i]; i++) 
        if (buf[i] == ',') buf[i] = '.';
    return (float)atof(buf);
}

void formatarData(char* dest, const char* data) {
    if (!data || data[0] == '\0') { 
        strcpy(dest, "01/01/0000"); 
        return; 
    }
    char s[256]; 
    int si = 0, i = 0; 
    while (data[i] == ' ' || data[i] == '"') i++; 
    while (data[i] && data[i] != '"' && si < 255) 
        s[si++] = data[i++]; 
    s[si] = '\0';
    
    int d, m, y;
    if (sscanf(s, "%d/%d/%d", &d, &m, &y) == 3) { 
        sprintf(dest, "%02d/%02d/%04d", d, m, y); 
        return; 
    }
    if (sscanf(s, "%d-%d-%d", &y, &m, &d) == 3) { 
        sprintf(dest, "%02d/%02d/%04d", d, m, y); 
        return; 
    }
    
    char mon[32];
    if (sscanf(s, "%31s %d, %d", mon, &d, &y) == 3 || sscanf(s, "%d %31s %d", &d, mon, &y) == 3) {
        char mlow[4] = {0}; 
        for (i = 0; i < 3 && mon[i]; i++) 
            mlow[i] = tolower((unsigned char)mon[i]);
        int mm = 1; 
        if (strcmp(mlow, "jan") == 0) mm = 1; 
        else if (strcmp(mlow, "feb") == 0) mm = 2; 
        else if (strcmp(mlow, "mar") == 0) mm = 3; 
        else if (strcmp(mlow, "apr") == 0) mm = 4; 
        else if (strcmp(mlow, "may") == 0) mm = 5; 
        else if (strcmp(mlow, "jun") == 0) mm = 6; 
        else if (strcmp(mlow, "jul") == 0) mm = 7; 
        else if (strcmp(mlow, "aug") == 0) mm = 8; 
        else if (strcmp(mlow, "sep") == 0) mm = 9; 
        else if (strcmp(mlow, "oct") == 0) mm = 10; 
        else if (strcmp(mlow, "nov") == 0) mm = 11; 
        else if (strcmp(mlow, "dec") == 0) mm = 12;
        sprintf(dest, "%02d/%02d/%04d", d, mm, y); 
        return; 
    }
    strcpy(dest, "01/01/0000");
}

void formatarColchetes(char*** dest_array, int* count, const char* campo) {
    char temp[MAX_FIELD_SIZE]; 
    int ti = 0, i = 0; 
    while (campo[i] && ti < MAX_FIELD_SIZE-1) 
        temp[ti++] = campo[i++]; 
    temp[ti] = '\0'; 
    char* start = temp; 
    int len = strlen(start);
    
    if (len > 1 && start[0] == '"' && start[len-1] == '"') { 
        start[len-1] = '\0'; 
        start++; 
        len -= 2; 
    }
    if (len > 1 && start[0] == '[' && start[len-1] == ']') { 
        start[len-1] = '\0'; 
        start++; 
    }
    
    *count = 0; 
    if (strlen(start) > 0) { 
        *count = 1; 
        for (i = 0; start[i]; i++) 
            if (start[i] == ',') 
                (*count)++; 
    }
    
    if (*count == 0) { 
        *dest_array = NULL; 
        return; 
    }
    
    *dest_array = (char**)malloc(*count * sizeof(char*)); 
    if (!*dest_array) return; 
    
    char* token = strtok(start, ","); 
    int index = 0; 
    while (token && index < *count) { 
        while (*token == ' ' || *token == '\'') token++; 
        char* end = token + strlen(token) - 1; 
        while (end >= token && (*end == ' ' || *end == '\'')) 
            *end-- = '\0'; 
        (*dest_array)[index++] = strdup(token); 
        token = strtok(NULL, ","); 
    }
}

void splitManual(char*** dest_fields, int* num_fields, const char* linha) {
    *num_fields = 0; 
    bool aspas = false, colchete = false; 
    
    for (int i = 0; linha[i]; i++) { 
        if (linha[i] == '"') aspas = !aspas; 
        else if (linha[i] == '[') colchete = true; 
        else if (linha[i] == ']') colchete = false; 
        else if (linha[i] == ',' && !aspas && !colchete) 
            (*num_fields)++; 
    } 
    (*num_fields)++;
    
    *dest_fields = (char**)malloc(*num_fields * sizeof(char*)); 
    char temp[MAX_FIELD_SIZE]; 
    int field_idx = 0, char_idx = 0; 
    aspas = colchete = false; 
    
    for (int i = 0; linha[i]; i++) { 
        char c = linha[i]; 
        if (c == '\n' || c == '\r') continue; 
        if (c == '"') aspas = !aspas; 
        else if (c == '[') colchete = true; 
        else if (c == ']') colchete = false; 
        
        if (c == ',' && !aspas && !colchete) { 
            temp[char_idx] = '\0'; 
            (*dest_fields)[field_idx++] = strdup(temp); 
            char_idx = 0; 
        } else { 
            temp[char_idx++] = c; 
        } 
    } 
    temp[char_idx] = '\0'; 
    (*dest_fields)[field_idx] = strdup(temp);
}

void freeGameContents(Game* g) {
    if (!g) return; 
    free(g->name); 
    free(g->releaseDate); 
    
    for (int i = 0; i < g->numSupportedLanguages; i++) 
        free(g->supportedLanguages[i]); 
    free(g->supportedLanguages); 
    
    for (int i = 0; i < g->numPublishers; i++) 
        free(g->publishers[i]); 
    free(g->publishers); 
    
    for (int i = 0; i < g->numDevelopers; i++) 
        free(g->developers[i]); 
    free(g->developers); 
    
    for (int i = 0; i < g->numCategories; i++) 
        free(g->categories[i]); 
    free(g->categories); 
    
    for (int i = 0; i < g->numGenres; i++) 
        free(g->genres[i]); 
    free(g->genres); 
    
    for (int i = 0; i < g->numTags; i++) 
        free(g->tags[i]); 
    free(g->tags);
}

int main() {
    FILE *arq = NULL; 
    const char *env_path = getenv("CSV_PATH"); 
    const char *candidates[5] = {"pubs/games.csv","./pubs/games.csv","/tmp/games.csv","games.csv",env_path}; 
    
    for (int i = 0; i < 5; i++) { 
        if (candidates[i] == NULL) continue; 
        arq = fopen(candidates[i], "r"); 
        if (arq != NULL) break; 
    } 
    
    if (!arq) { 
        fprintf(stderr, "Erro ao abrir o arquivo CSV.\n"); 
        return 1; 
    }
    
    char linha[MAX_LINE_SIZE]; 
    fgets(linha, sizeof(linha), arq);
    
    Game* allGames = (Game*)malloc(MAX_GAMES * sizeof(Game)); 
    int totalGames = 0;
    
    while (fgets(linha, sizeof(linha), arq) != NULL && totalGames < MAX_GAMES) {
        char** campos = NULL; 
        int num_campos = 0; 
        splitManual(&campos, &num_campos, linha); 
        
        if (num_campos < 14) { 
            for (int i = 0; i < num_campos; i++) free(campos[i]); 
            free(campos); 
            continue; 
        }
        
        Game* g = &allGames[totalGames]; 
        memset(g, 0, sizeof(Game)); 
        g->id = parseInt(campos[0]); 
        g->name = strdup(campos[1]); 
        
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
        totalGames++; 
    }
    fclose(arq);

    // Inicializar pilha
    inicializarPilha();
    
    // Ler IDs e empilhar
    char inputId[50]; 
    scanf("%49s", inputId);
    while (strcmp(inputId, "FIM") != 0) { 
        int idBusca = atoi(inputId); 
        for (int i = 0; i < totalGames; i++) { 
            if (allGames[i].id == idBusca) { 
                inserir(&allGames[i]); 
                break; 
            } 
        } 
        scanf("%49s", inputId); 
    }
    
    // Processar comandos
    int n;
    scanf("%d", &n);
    
    for (int i = 0; i < n; i++) {
        char comando[10];
        scanf("%s", comando);
        
        if (strcmp(comando, "I") == 0) {
            // Inserir (empilhar)
            int id;
            scanf("%d", &id);
            for (int j = 0; j < totalGames; j++) {
                if (allGames[j].id == id) {
                    inserir(&allGames[j]);
                    break;
                }
            }
        } else if (strcmp(comando, "R") == 0) {
            // Remover (desempilhar)
            Game* removido = remover();
            printf("(R) %s\n", removido->name);
        }
    }
    
    // Mostrar pilha final
    mostrarPilha();

    for (int i = 0; i < totalGames; i++) 
        freeGameContents(&allGames[i]); 
    free(allGames);
    
    return 0;
}