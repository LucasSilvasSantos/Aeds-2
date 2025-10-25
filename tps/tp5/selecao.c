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
int compareGamesByName(const Game* g1, const Game* g2);
void selectionSort(Game* arr, int n);
void swap(Game* arr, int i, int j);

int compareGamesByName(const Game* g1, const Game* g2) {
    comparisons++;
    if (g1->name == NULL && g2->name == NULL) return 0;
    if (g1->name == NULL) return -1;
    if (g2->name == NULL) return 1;
    return strcmp(g1->name, g2->name);
}

void swap(Game* arr, int i, int j) {
    if (i == j) return;
    Game temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
    movements += 3;
}

void selectionSort(Game* arr, int n) {
    for (int i = 0; i < n - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < n; j++) {
            if (compareGamesByName(&arr[j], &arr[minIndex]) < 0) {
                minIndex = j;
            }
        }
        swap(arr, i, minIndex);
    }
}

int parseInt(const char* s) {
    if (!s || strlen(s) == 0) return 0;
    char temp[MAX_FIELD_SIZE];
    strncpy(temp, s, MAX_FIELD_SIZE - 1);
    temp[MAX_FIELD_SIZE - 1] = '\0';
    if (strlen(temp) >= 2 && temp[0] == '"' && temp[strlen(temp) - 1] == '"') {
        temp[strlen(temp) - 1] = '\0';
        memmove(temp, temp + 1, strlen(temp));
    }
    char* endptr;
    long val = strtol(temp, &endptr, 10);
    if (*endptr != '\0' && !isspace((unsigned char)*endptr)) {
         char *check_start = temp;
         while(isspace((unsigned char)*check_start)) check_start++;
         if (check_start == endptr) return 0;
    }
    return (int)val;
}

float parseFloat(const char* s) {
    if (!s || strlen(s) == 0) return 0.0f;
    if (strcmp(s, "\"Free to Play\"") == 0 || strcmp(s, "Free to Play") == 0 || strcmp(s, "0.0") == 0) {
        return 0.0f;
    }
    if (strcmp(s, "\"tbd\"") == 0 || strcmp(s, "tbd") == 0) {
        return 0.0f;
    }
    char temp[MAX_FIELD_SIZE];
    strncpy(temp, s, MAX_FIELD_SIZE - 1);
    temp[MAX_FIELD_SIZE - 1] = '\0';
    if (strlen(temp) >= 2 && temp[0] == '"' && temp[strlen(temp) - 1] == '"') {
        temp[strlen(temp) - 1] = '\0';
        memmove(temp, temp + 1, strlen(temp));
    }
    char* endptr;
    float val = strtof(temp, &endptr);
    if (*endptr != '\0' && !isspace((unsigned char)*endptr)) {
         char *check_start = temp;
         while(isspace((unsigned char)*check_start)) check_start++;
         if (check_start == endptr) return 0.0f;
    }
    return val;
}

void formatarData(char* dest, const char* data) {
    if (!data || strlen(data) == 0) {
        strcpy(dest, "01/01/0000");
        return;
    }
    char temp[MAX_FIELD_SIZE];
    strncpy(temp, data, MAX_FIELD_SIZE - 1);
    temp[MAX_FIELD_SIZE - 1] = '\0';
    char* p = temp;
    if (strlen(p) >= 2 && p[0] == '"' && p[strlen(p) - 1] == '"') {
        p[strlen(p) - 1] = '\0';
        p++;
    }
    int d = 1, m = 1, y = 0;
    char mon_str[32];
    if (sscanf(p, "%31s %d, %d", mon_str, &d, &y) == 3 || sscanf(p, "%d %31s %d", &d, mon_str, &y) == 3) {
        char mlow[4] = {'\0', '\0', '\0', '\0'};
        for (int i = 0; i < 3 && mon_str[i]; i++) mlow[i] = (char)tolower((unsigned char)mon_str[i]);
        if (strcmp(mlow, "jan") == 0) m = 1;
        else if (strcmp(mlow, "feb") == 0) m = 2;
        else if (strcmp(mlow, "mar") == 0) m = 3;
        else if (strcmp(mlow, "apr") == 0) m = 4;
        else if (strcmp(mlow, "may") == 0) m = 5;
        else if (strcmp(mlow, "jun") == 0) m = 6;
        else if (strcmp(mlow, "jul") == 0) m = 7;
        else if (strcmp(mlow, "aug") == 0) m = 8;
        else if (strcmp(mlow, "sep") == 0) m = 9;
        else if (strcmp(mlow, "oct") == 0) m = 10;
        else if (strcmp(mlow, "nov") == 0) m = 11;
        else if (strcmp(mlow, "dec") == 0) m = 12;
        else m = 1;
    } else if (sscanf(p, "%d/%d/%d", &d, &m, &y) == 3) {
    } else if (sscanf(p, "%d-%d-%d", &d, &m, &y) == 3) {
    } else if (sscanf(p, "%d-%d-%d", &y, &m, &d) == 3) {
         sscanf(p, "%d-%d-%d", &y, &m, &d);
    } else {
         bool only_year = true;
         for(int k=0; p[k]; ++k) if (!isdigit((unsigned char)p[k])) only_year = false;
         if(only_year && strlen(p) == 4) {
             y = atoi(p);
             d = 1;
             m = 1;
         } else {
            strcpy(dest, "01/01/0000");
            return;
         }
    }
    if (d < 1 || d > 31) d = 1;
    if (m < 1 || m > 12) m = 1;
    if (y < 0) y = 0;
    sprintf(dest, "%02d/%02d/%04d", d, m, y);
}

void formatarColchetes(char*** dest_array, int* count, const char* campo) {
    *dest_array = NULL;
    *count = 0;
    if (!campo || strlen(campo) == 0) return;
    char temp_campo[MAX_FIELD_SIZE * 2];
    strncpy(temp_campo, campo, sizeof(temp_campo) - 1);
    temp_campo[sizeof(temp_campo) - 1] = '\0';
    char* start = temp_campo;
    if (strlen(start) >= 2 && start[0] == '"' && start[strlen(start) - 1] == '"') {
        start[strlen(start) - 1] = '\0';
        start++;
    }
    if (strlen(start) >= 2 && start[0] == '[' && start[strlen(start) - 1] == ']') {
        start[strlen(start) - 1] = '\0';
        start++;
    }
    if (strlen(start) == 0) return;
    int temp_count = 1;
    for (int i = 0; start[i]; i++) {
        if (start[i] == ',') temp_count++;
    }
    *dest_array = (char**)malloc(temp_count * sizeof(char*));
    if (!*dest_array) return;
    char* current_element = start;
    int actual_count = 0;
    for (int i = 0; ; i++) {
        if (start[i] == ',' || start[i] == '\0') {
             char saved_char = start[i];
             start[i] = '\0';
             char* element = current_element;
             while (isspace((unsigned char)*element)) element++;
             if (*element == '\'' || *element == '"') element++;
             char* end = element + strlen(element) - 1;
             while (end >= element && (isspace((unsigned char)*end) || *end == '\'' || *end == '"')) {
                 *end = '\0';
                 end--;
             }
             if (strlen(element) > 0) {
                 if (actual_count < temp_count) {
                    (*dest_array)[actual_count++] = strdup(element);
                 }
             }
             start[i] = saved_char;
             current_element = start + i + 1;
             if (saved_char == '\0') break;
        }
    }
    *count = actual_count;
    if (actual_count > 0 && actual_count < temp_count) {
         char** resized_array = (char**)realloc(*dest_array, actual_count * sizeof(char*));
         if (resized_array) {
             *dest_array = resized_array;
         }
     } else if (actual_count == 0) {
         free(*dest_array);
         *dest_array = NULL;
     }
}

void splitManual(char*** dest_fields, int* num_fields, const char* linha) {
    *num_fields = 0;
    int capacity = 20;
    *dest_fields = (char**)malloc(capacity * sizeof(char*));
    char temp_field[MAX_FIELD_SIZE * 2];
    int char_index = 0;
    bool dentroAspas = false;
    for (int i = 0; linha[i] != '\0'; i++) {
        char c = linha[i];
        if (c == '\n' || c == '\r') continue;
        if (c == '"') {
             if (dentroAspas && linha[i+1] == '"') {
                 temp_field[char_index++] = '"';
                 i++;
             } else {
                 dentroAspas = !dentroAspas;
             }
        } else if (c == ',' && !dentroAspas) {
            temp_field[char_index] = '\0';
            if (*num_fields >= capacity) {
                capacity *= 2;
                *dest_fields = (char**)realloc(*dest_fields, capacity * sizeof(char*));
            }
            (*dest_fields)[(*num_fields)++] = strdup(temp_field);
            char_index = 0;
        } else {
            if (char_index < sizeof(temp_field) - 1) {
                temp_field[char_index++] = c;
            }
        }
    }
    temp_field[char_index] = '\0';
    if (*num_fields >= capacity) {
        capacity++;
        *dest_fields = (char**)realloc(*dest_fields, capacity * sizeof(char*));
    }
    (*dest_fields)[(*num_fields)++] = strdup(temp_field);
    if (*num_fields > 0) {
        *dest_fields = (char**)realloc(*dest_fields, (*num_fields) * sizeof(char*));
    } else {
         free(*dest_fields);
         *dest_fields = NULL;
    }
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
    g->name = NULL;
    g->releaseDate = NULL;
    g->supportedLanguages = NULL; g->numSupportedLanguages = 0;
    g->publishers = NULL; g->numPublishers = 0;
    g->developers = NULL; g->numDevelopers = 0;
    g->categories = NULL; g->numCategories = 0;
    g->genres = NULL; g->numGenres = 0;
    g->tags = NULL; g->numTags = 0;
}

void printGame(const Game* g) {
    printf("=> %d ## %s ## %s ## %d ## ",
           g->id,
           g->name ? g->name : "",
           g->releaseDate ? g->releaseDate : "01/01/0000",
           g->estimatedOwners);
    if (g->price == 0.0) {
        printf("0.0 ## ");
    } else {
        printf("%g ## ", g->price);
    }
    printf("[");
    for (int i = 0; i < g->numSupportedLanguages; i++)
        printf("%s%s", g->supportedLanguages[i], (i == g->numSupportedLanguages - 1) ? "" : ", ");
    printf("]");
    printf(" ## %d ## %.1f ## %d ## ",
           g->metacriticScore == -1 ? 0 : g->metacriticScore,
           g->userScore == -1.0f ? 0.0f : g->userScore,
           g->achievements);
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
    if (!allGames) { fclose(arq); return 1; }
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
        g->price = parseFloat(campos[4]);
        formatarColchetes(&g->supportedLanguages, &g->numSupportedLanguages, campos[5]);
        g->metacriticScore = strlen(campos[6]) == 0 ? 0 : parseInt(campos[6]);
        g->userScore = (strlen(campos[7]) == 0 || strcmp(campos[7], "tbd") == 0 || strcmp(campos[7], "\"tbd\"") == 0) ? 0.0f : parseFloat(campos[7]);
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
    if (!selectedGames) {
         for (int i = 0; i < totalGames; i++) freeGameContents(&allGames[i]);
         free(allGames);
         return 1;
    }
    int numSelected = 0;
    char inputId[50];
    scanf("%49s", inputId);
    while (strcmp(inputId, "FIM") != 0 && numSelected < MAX_SELECTED) {
        int idBusca = atoi(inputId);
        for (int i = 0; i < totalGames; i++) {
            if (allGames[i].id == idBusca) {
                selectedGames[numSelected++] = allGames[i];
                break;
            }
        }
        scanf("%49s", inputId);
    }
    clock_t inicio = clock();
    if (numSelected > 0) {
        selectionSort(selectedGames, numSelected);
    }
    clock_t fim = clock();
    double tempoMs = ((double)(fim - inicio)) * 1000.0 / CLOCKS_PER_SEC;
    for (int i = 0; i < numSelected; i++) {
        printGame(&selectedGames[i]);
    }
    char logFilename[100];
    sprintf(logFilename, "%s_selecao.txt", MATRICULA);
    FILE* logFile = fopen(logFilename, "w");
    if (logFile) {
        fprintf(logFile, "%s\t%ld\t%ld\t%.4fms\n", MATRICULA, comparisons, movements, tempoMs);
        fclose(logFile);
    } else {
        fprintf(stderr, "Erro ao criar arquivo de log %s\n", logFilename);
    }
    for (int i = 0; i < totalGames; i++) {
        freeGameContents(&allGames[i]);
    }
    free(allGames);
    free(selectedGames);
    return 0;
}