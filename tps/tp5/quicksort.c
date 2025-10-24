#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include <time.h>

#define MAX_LINE_SIZE 4096
#define MAX_FIELD_SIZE 1024
#define MAX_GAMES 5000
#define MAX_SELECTED 200

#define MATRICULA "891378"


static long comparisons = 0;
static long movements = 0;

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


int compareDates(const char* date1, const char* date2) {
    int dia1, mes1, ano1, dia2, mes2, ano2;
    
 
    sscanf(date1, "%d/%d/%d", &dia1, &mes1, &ano1);
    

    sscanf(date2, "%d/%d/%d", &dia2, &mes2, &ano2);
    
 
    if (ano1 < ano2) return -1;
    if (ano1 > ano2) return 1;
    
    if (mes1 < mes2) return -1;
    if (mes1 > mes2) return 1;
    
   
    if (dia1 < dia2) return -1;
    if (dia1 > dia2) return 1;
    
    
    return 0;
}


int compareGames(const Game* g1, const Game* g2) {
    comparisons++; 
    int dateCmp = compareDates(g1->releaseDate, g2->releaseDate);
    
    if (dateCmp != 0) {
        return dateCmp; 
    }
    
   
    if (g1->id < g2->id) return -1;
    if (g1->id > g2->id) return 1;
    
    return 0; 
}


void swap(Game* arr, int i, int j) {
    if (i == j) return; 
    
    Game temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
    
    movements += 3; 
}


void quickSort(Game* arr, int left, int right) {

    if (left >= right) return;
    
    
    int mid = (left + right) / 2;
    Game pivot = arr[mid];
    
  
    int i = left;
    int j = right;
    

    while (i <= j) {
       
        while (compareGames(&arr[i], &pivot) < 0) {
            i++;
        }
        
       
        while (compareGames(&arr[j], &pivot) > 0) {
            j--;
        }
        
        
        if (i <= j) {
            swap(arr, i, j);
            i++;
            j--;
        }
    }
    
  
    if (left < j) quickSort(arr, left, j);
    if (i < right) quickSort(arr, i, right); 
}

int parseInt(const char* s) {
    if (!s) return 0;
    int resultado = 0;
    int i = 0;
    while (s[i] == ' ' || s[i] == '"') i++;
    while (s[i] >= '0' && s[i] <= '9') {
        resultado = resultado * 10 + (s[i] - '0');
        i++;
    }
    return resultado;
}

float parseFloat(const char* s) {
    if (!s) return 0.0f;
    char buf[MAX_FIELD_SIZE];
    int bi = 0;
    for (int i = 0; s[i] != '\0' && bi < MAX_FIELD_SIZE-1; i++) {
        char c = s[i];
        if ((c >= '0' && c <= '9') || c == '.' || c == ',') buf[bi++] = c;
    }
    buf[bi] = '\0';
    for (int i = 0; buf[i] != '\0'; i++) if (buf[i] == ',') buf[i] = '.';
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
    while (data[i] && data[i] != '"' && si < 255) s[si++] = data[i++];
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
        for (i = 0; i < 3 && mon[i]; i++) mlow[i] = tolower(mon[i]);
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
    while (campo[i] && ti < MAX_FIELD_SIZE-1) temp[ti++] = campo[i++];
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
        for (i = 0; start[i]; i++) if (start[i] == ',') (*count)++;
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
        while (end >= token && (*end == ' ' || *end == '\'')) *end-- = '\0';
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
        else if (linha[i] == ',' && !aspas && !colchete) (*num_fields)++;
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


void printGame(const Game* g) {
    printf("=> %d ## %s ## %s ## %d ## ", g->id, g->name, g->releaseDate, g->estimatedOwners);
    
    if (g->price == 0.0) {
        printf("0.0 ## ");
    } else {
        printf("%g ## ", g->price);
    }
    
    printf("[");
    for (int i = 0; i < g->numSupportedLanguages; i++)
        printf("%s%s", g->supportedLanguages[i], (i == g->numSupportedLanguages - 1) ? "" : ", ");
    printf("]");

    printf(" ## %d ## %.1f ## %d ## ", g->metacriticScore, g->userScore, g->achievements);
    
    printf("[");
    for (int i = 0; i < g->numPublishers; i++)
        printf("%s%s", g->publishers[i], (i == g->numPublishers - 1) ? "" : ", ");
    printf("]");

    printf(" ## ");
    printf("[");
    for (int i = 0; i < g->numDevelopers; i++)
        printf("%s%s", g->developers[i], (i == g->numDevelopers - 1) ? "" : ", ");
    printf("]");

    printf(" ## ");
    printf("[");
    for (int i = 0; i < g->numCategories; i++)
        printf("%s%s", g->categories[i], (i == g->numCategories - 1) ? "" : ", ");
    printf("]");

    printf(" ## ");
    printf("[");
    for (int i = 0; i < g->numGenres; i++)
        printf("%s%s", g->genres[i], (i == g->numGenres - 1) ? "" : ", ");
    printf("]");

    printf(" ## ");
    printf("[");
    for (int i = 0; i < g->numTags; i++)
        printf("%s%s", g->tags[i], (i == g->numTags - 1) ? "" : ", ");
    printf("]");
    
    printf(" ##\n");
}


int main() {
    
    FILE *arq = NULL;
    const char *env_path = getenv("CSV_PATH");
    const char *candidates[5] = {
        "pubs/games.csv",
        "./pubs/games.csv",
        "/tmp/games.csv",
        "games.csv",
        env_path
    };
    for (int i = 0; i < 5; i++) {
        if (candidates[i] == NULL) continue;
        arq = fopen(candidates[i], "r");
        if (arq != NULL) break;
    }
    if (!arq) {
        fprintf(stderr, "Erro ao abrir o arquivo CSV. Verifique se o arquivo existe em pubs/, /tmp/ ou defina CSV_PATH.\n");
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

   
    Game* selectedGames = (Game*)malloc(MAX_SELECTED * sizeof(Game));
    int numSelected = 0;
    
    char inputId[50];
    scanf("%s", inputId);
    
    while (strcmp(inputId, "FIM") != 0) {
        int idBusca = atoi(inputId);
        
       
        for (int i = 0; i < totalGames; i++) {
            if (allGames[i].id == idBusca) {
              
                selectedGames[numSelected++] = allGames[i];
                break; 
            }
        }
        
        scanf("%s", inputId);
    }

 
    clock_t inicio = clock();
    if (numSelected > 0) {
        quickSort(selectedGames, 0, numSelected - 1);
    }
    clock_t fim = clock();
    
    double tempoMs = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000.0;

   
    for (int i = 0; i < numSelected; i++) {
        printGame(&selectedGames[i]);
    }

   
    char logFilename[100];
    sprintf(logFilename, "%s_quicksort.txt", MATRICULA);
    FILE* logFile = fopen(logFilename, "w");
    if (logFile) {
        fprintf(logFile, "%s\t%ld\t%ld\t%.2fms\n", MATRICULA, comparisons, movements, tempoMs);
        fclose(logFile);
    }

   
    for (int i = 0; i < totalGames; i++) {
        freeGameContents(&allGames[i]);
    }
    free(allGames);
    free(selectedGames);

    return 0;
}
