#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h> // Para isspace()

#define MAX_LINE_SIZE 4096
#define MAX_FIELD_SIZE 1024
#define MAX_ID_SIZE 10

// Estrutura do jogo
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

// Funções auxiliares
void freeGameContents(Game* g);
int parseInt(const char* s);
float parseFloat(const char* s);
void formatarData(char* dest, const char* data);
void formatarColchetes(char*** dest_array, int* count, const char* campo);
void splitManual(char*** dest_fields, int* num_fields, const char* linha);
void printGame(const Game* g);


void formatarColchetes(char*** dest_array, int* count, const char* campo) {
    char temp_campo[MAX_FIELD_SIZE];
    strncpy(temp_campo, campo, MAX_FIELD_SIZE - 1);
    temp_campo[MAX_FIELD_SIZE - 1] = '\0';
    
    char* start = temp_campo;

   
    if (strlen(start) > 1 && start[0] == '"' && start[strlen(start) - 1] == '"') {
        start[strlen(start) - 1] = '\0';
        start++;
    }
    if (strlen(start) > 1 && start[0] == '[' && start[strlen(start) - 1] == ']') {
        start[strlen(start) - 1] = '\0';
        start++;
    }

    
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

    char* token = strtok(start, ",");
    int index = 0;
    while (token != NULL && index < *count) {
       
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


void printGame(const Game* g) {
    char dataFormatada[12];
    formatarData(dataFormatada, g->releaseDate);

    printf("=> %d ## %s ## %s ## %d ## %.2f ## [", g->id, g->name, dataFormatada, g->estimatedOwners, g->price);
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

int parseInt(const char* s) {
    int resultado = 0;
    for (int i = 0; s[i] != '\0'; i++) {
        if (isdigit((unsigned char)s[i])) {
            resultado = resultado * 10 + (s[i] - '0');
        }
    }
    return resultado;
}

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

void formatarData(char* dest, const char* data) {
    char temp_data[100];
    strncpy(temp_data, data, sizeof(temp_data) - 1);
    temp_data[sizeof(temp_data) - 1] = '\0';
    
    if (temp_data[0] == '"') {
        memmove(temp_data, temp_data + 1, strlen(temp_data));
        temp_data[strlen(temp_data) - 1] = '\0';
    }

    char* mes_str = strtok(temp_data, " ,");
    char* dia_str = strtok(NULL, " ,");
    char* ano_str = strtok(NULL, " ,");

    if (!mes_str || !dia_str || !ano_str) {
        strcpy(dest, "00/00/0000");
        return;
    }
    
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
    else strcpy(numeroMes, "00");

    sprintf(dest, "%02d/%s/%s", atoi(dia_str), numeroMes, ano_str);
}


void splitManual(char*** dest_fields, int* num_fields, const char* linha) {
    *num_fields = 0;
    bool dentroAspas = false, dentroColchete = false;

    for (int i = 0; linha[i] != '\0'; i++) {
        if (linha[i] == '"') dentroAspas = !dentroAspas;
        else if (linha[i] == '[') dentroColchete = true;
        else if (linha[i] == ']') dentroColchete = false;
        else if (linha[i] == ',' && !dentroAspas && !dentroColchete) (*num_fields)++;
    }
    (*num_fields)++;

    *dest_fields = (char**)malloc(*num_fields * sizeof(char*));

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


int main() {
    Game g = {0};
    char procurarId[MAX_ID_SIZE];

    while (fgets(procurarId, sizeof(procurarId), stdin) != NULL && strncmp(procurarId, "FIM", 3) != 0) {
        procurarId[strcspn(procurarId, "\n")] = 0;
        
        FILE* file = fopen("/tmp/games.csv", "r");
        if (!file) {
            perror("Erro ao abrir /tmp/games.csv");
            return 1;
        }

        char linha[MAX_LINE_SIZE];
        fgets(linha, sizeof(linha), file);

        bool encontrado = false;
        while (fgets(linha, sizeof(linha), file) != NULL) {
            char** campos = NULL;
            int num_campos = 0;
            splitManual(&campos, &num_campos, linha);
            
            if (num_campos < 14) {
                for (int i = 0; i < num_campos; i++) free(campos[i]);
                free(campos);
                continue;
            }

            if (parseInt(campos[0]) == parseInt(procurarId)) {
                encontrado = true;
                freeGameContents(&g); 
                
                g.id = parseInt(campos[0]);
                g.name = strdup(campos[1]);
                g.releaseDate = strdup(campos[2]);
                g.estimatedOwners = parseInt(campos[3]);
                g.price = strcmp(campos[4], "Free to Play") == 0 ? 0.0f : parseFloat(campos[4]);
                
                formatarColchetes(&g.supportedLanguages, &g.numSupportedLanguages, campos[5]);

                g.metacriticScore = strlen(campos[6]) == 0 ? -1 : parseInt(campos[6]);
                g.userScore = (strlen(campos[7]) == 0 || strcmp(campos[7], "tbd") == 0) ? -1.0f : parseFloat(campos[7]);
                g.achievements = strlen(campos[8]) == 0 ? 0 : parseInt(campos[8]);

                formatarColchetes(&g.publishers, &g.numPublishers, campos[9]);
                formatarColchetes(&g.developers, &g.numDevelopers, campos[10]);
                formatarColchetes(&g.categories, &g.numCategories, campos[11]);
                formatarColchetes(&g.genres, &g.numGenres, campos[12]);
                formatarColchetes(&g.tags, &g.numTags, campos[13]);

                printGame(&g);
            }

            for (int i = 0; i < num_campos; i++) free(campos[i]);
            free(campos);
        }

        if (!encontrado) {
            printf("Jogo com ID %s não encontrado!\n", procurarId);
        }
        fclose(file);
    }

    freeGameContents(&g);
    return 0;
}
