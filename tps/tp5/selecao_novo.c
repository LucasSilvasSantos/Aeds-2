#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

int comparacoes = 0;
int trocas = 0;

#define TAMANHO_MAX 1000
#define TAMANHO 50

typedef struct {
    char texto[TAMANHO_MAX];
} Str;

typedef struct {
    int id;
    Str nome;
    Str dataLancamento;
    int estimativaJogadores;
    float preco;
    Str idiomas[TAMANHO];
    int qtdIdiomas; 
    int pontuacaoMetacritic;
    float avaliacaoUsuario;
    int conquistas;
    Str publicadoras[TAMANHO];
    int qtdPublicadoras;
    Str desenvolvedoras[TAMANHO];
    int qtdDesenvolvedoras;
    Str categorias[TAMANHO];
    int qtdCategorias;
    Str generos[TAMANHO];
    int qtdGeneros;
    Str tags[TAMANHO];
    int qtdTags;
} Jogo;

int processarLista(Str entrada, Str saida[], bool removerApostrofo) {
    Str buffer;
    int total = 0, pos = 0;
    for(int i = 0; i < strlen(entrada.texto); i++) {
        char c = entrada.texto[i]; 
        if(c == ',') {
            buffer.texto[pos] = '\0';
            int inicio = 0;
            while(buffer.texto[inicio] == ' ') inicio++;
            if (strlen(buffer.texto + inicio) > 0) {
                 strcpy(saida[total].texto, buffer.texto + inicio);
                 total++;
            }
            strcpy(buffer.texto, ""); 
            pos = 0;
        } else {
            if(!(c == '[' || c == ']' || (removerApostrofo && c == '\''))) {
                buffer.texto[pos] = c;
                pos++;
            }
        }
    }
    buffer.texto[pos] = '\0';
    if (strlen(buffer.texto) > 0) {
        int inicio = 0;
        while(buffer.texto[inicio] == ' ') inicio++;
        strcpy(saida[total].texto, buffer.texto + inicio);
        total++;
    }
    return total;
}

void converterData(Str entrada, Str *saida) {
    if (strlen(entrada.texto) < 8) { 
        strcpy(saida->texto, "01/01/0000");
        return;
    }
    char mesStr[4], diaStr[3], anoStr[5], mesNumerico[3];
    strncpy(mesStr, entrada.texto, 3);
    mesStr[3] = '\0';
    if (entrada.texto[5] == ',') {
        diaStr[0] = '0';
        diaStr[1] = entrada.texto[4];
        diaStr[2] = '\0';
        strcpy(anoStr, entrada.texto + 7);
    } else { 
        diaStr[0] = entrada.texto[4];
        diaStr[1] = entrada.texto[5];
        diaStr[2] = '\0';
        strcpy(anoStr, entrada.texto + 8);
    }
    if (strcmp(mesStr, "Jan") == 0) strcpy(mesNumerico, "01");
    else if (strcmp(mesStr, "Feb") == 0) strcpy(mesNumerico, "02");
    else if (strcmp(mesStr, "Mar") == 0) strcpy(mesNumerico, "03");
    else if (strcmp(mesStr, "Apr") == 0) strcpy(mesNumerico, "04");
    else if (strcmp(mesStr, "May") == 0) strcpy(mesNumerico, "05");
    else if (strcmp(mesStr, "Jun") == 0) strcpy(mesNumerico, "06");
    else if (strcmp(mesStr, "Jul") == 0) strcpy(mesNumerico, "07");
    else if (strcmp(mesStr, "Aug") == 0) strcpy(mesNumerico, "08");
    else if (strcmp(mesStr, "Sep") == 0) strcpy(mesNumerico, "09");
    else if (strcmp(mesStr, "Oct") == 0) strcpy(mesNumerico, "10");
    else if (strcmp(mesStr, "Nov") == 0) strcpy(mesNumerico, "11");
    else if (strcmp(mesStr, "Dec") == 0) strcpy(mesNumerico, "12");
    else strcpy(mesNumerico, "01");
    strcpy(saida->texto, diaStr);
    strcat(saida->texto, "/");
    strcat(saida->texto, mesNumerico);
    strcat(saida->texto, "/");
    strcat(saida->texto, anoStr);
}

void atribuirId(Jogo *jogo, Str valor) {
    jogo->id = atoi(valor.texto);
}
void atribuirNome(Jogo *jogo, Str valor) {
    strcpy(jogo->nome.texto, valor.texto);
}
void atribuirData(Jogo *jogo, Str valor) {
    converterData(valor, &jogo->dataLancamento);
}
void atribuirJogadores(Jogo *jogo, Str valor) {
    Str temp;
    temp.texto[0] = '\0';
    int idx = 0;
    for (int i = 0; i < strlen(valor.texto); i++) {
        if (valor.texto[i] >= '0' && valor.texto[i] <= '9')
            temp.texto[idx++] = valor.texto[i];
    }
    temp.texto[idx] = '\0';
    jogo->estimativaJogadores = atoi(temp.texto);
}
void atribuirPreco(Jogo *jogo, Str valor) {
    if(strcmp(valor.texto, "Free to Play") == 0 || strcmp(valor.texto, "0.0") == 0) {
        jogo->preco = 0.0f;
    }
    else {
        jogo->preco = atof(valor.texto); 
    }  
}
void atribuirIdiomas(Jogo *jogo, Str valor) {
    jogo->qtdIdiomas = processarLista(valor, jogo->idiomas, true);
}
void atribuirMetacritic(Jogo *jogo, Str valor) {
    if(strlen(valor.texto) == 0) {
        jogo->pontuacaoMetacritic = 0;
    }
    else {
        jogo->pontuacaoMetacritic = atoi(valor.texto);
    }
}
void atribuirAvaliacaoUsuario(Jogo *jogo, Str valor) {
    if(strlen(valor.texto) == 0 || strcmp(valor.texto, "tbd") == 0) {
        jogo->avaliacaoUsuario = 0.0f;
    }   
    else {
        jogo->avaliacaoUsuario = atof(valor.texto);
    }   
}
void atribuirConquistas(Jogo *jogo, Str valor) {
    if(strlen(valor.texto) == 0) {
        jogo->conquistas = 0;
    }
    else {
        jogo->conquistas = atoi(valor.texto);
    }
}
void atribuirPublicadoras(Jogo *jogo, Str valor) {
    jogo->qtdPublicadoras = processarLista(valor, jogo->publicadoras, false);
}
void atribuirDesenvolvedoras(Jogo *jogo, Str valor) {
    jogo->qtdDesenvolvedoras = processarLista(valor, jogo->desenvolvedoras, false);
}
void atribuirCategorias(Jogo *jogo, Str valor) {
    jogo->qtdCategorias = processarLista(valor, jogo->categorias, false);
}
void atribuirGeneros(Jogo *jogo, Str valor) {
    jogo->qtdGeneros = processarLista(valor, jogo->generos, false);
}
void atribuirTags(Jogo *jogo, Str valor) {
    jogo->qtdTags = processarLista(valor, jogo->tags, false);
}
void preencherJogo(Jogo *jogo, Str campos[]) {
    atribuirId(jogo, campos[0]);
    atribuirNome(jogo, campos[1]);
    atribuirData(jogo, campos[2]);
    atribuirJogadores(jogo, campos[3]);
    atribuirPreco(jogo, campos[4]);
    atribuirIdiomas(jogo, campos[5]);
    atribuirMetacritic(jogo, campos[6]);
    atribuirAvaliacaoUsuario(jogo, campos[7]);
    atribuirConquistas(jogo, campos[8]);
    atribuirPublicadoras(jogo, campos[9]);
    atribuirDesenvolvedoras(jogo, campos[10]);
    atribuirCategorias(jogo, campos[11]);
    atribuirGeneros(jogo, campos[12]);
    atribuirTags(jogo, campos[13]);
}

void mostrarLista(Str lista[], int tamanho) {
    printf("[");
    for (int i = 0; i < tamanho; i++) {
        int inicio = 0;
        while(lista[i].texto[inicio] == ' ') inicio++;
        printf("%s", lista[i].texto + inicio);
        if (i < tamanho - 1) printf(", ");
    }
    printf("]");
}
void exibirJogo(Jogo *jogo) {
    printf("=> %d ## %s ## %s ## %d ## ",
        jogo->id, 
        jogo->nome.texto, 
        jogo->dataLancamento.texto,
        jogo->estimativaJogadores
    );
    if (jogo->preco == 0.0) {
        printf("0.0 ## ");
    } else {
        printf("%g ## ", jogo->preco);
    }
    mostrarLista(jogo->idiomas, jogo->qtdIdiomas);
    printf(" ## %d ## %.1f ## %d ## ", 
        jogo->pontuacaoMetacritic, 
        jogo->avaliacaoUsuario,
        jogo->conquistas
    );
    mostrarLista(jogo->publicadoras, jogo->qtdPublicadoras);
    printf(" ## ");
    mostrarLista(jogo->desenvolvedoras, jogo->qtdDesenvolvedoras);
    printf(" ## ");
    mostrarLista(jogo->categorias, jogo->qtdCategorias);
    printf(" ## ");
    mostrarLista(jogo->generos, jogo->qtdGeneros);
    printf(" ## ");
    mostrarLista(jogo->tags, jogo->qtdTags);
    printf(" ##\n");
}

void trocar(Jogo *jogos, int i, int j) {
    Jogo temporario = jogos[i];
    jogos[i] = jogos[j];
    jogos[j] = temporario;
    trocas += 3;
}

void ordenarPorNome(Jogo *jogos, int quantidade) {
    for(int i = 0; i < quantidade - 1; i++) {
        int indiceMenor = i;
        for(int j = i + 1; j < quantidade; j++) {
            comparacoes++;
            if(strcmp(jogos[indiceMenor].nome.texto, jogos[j].nome.texto) > 0) {
                indiceMenor = j;
            }
        }
        if(i != indiceMenor) trocar(jogos, indiceMenor, i);
    }
}

int main() {
    FILE *arquivo = NULL;
    const char *caminhos[5] = {
        "pubs/games.csv",
        "./pubs/games.csv",
        "/tmp/games.csv",
        "games.csv",
        NULL
    };
    for (int i = 0; i < 5; i++) {
        if (caminhos[i] == NULL) continue;
        arquivo = fopen(caminhos[i], "r");
        if (arquivo != NULL) break;
    }
    if (!arquivo) {
        printf("Erro ao abrir o arquivo\n");
        return 1;
    }
    Jogo *todosJogos = (Jogo*)malloc(4000 * sizeof(Jogo));
    Str linha, header;
    int totalJogos = 0;
    fscanf(arquivo, " %[^\n]", header.texto);
    while (fscanf(arquivo, " %[^\n]", linha.texto) != EOF) {
        linha.texto[strcspn(linha.texto, "\r\n")] = '\0';
        Str campos[14];
        Str buffer;
        int numCampos = 0, posBuffer = 0;
        bool dentroAspas = false;
        for (int i = 0; i < strlen(linha.texto); i++) {
            char c = linha.texto[i];
            if (c == '"') {
                dentroAspas = !dentroAspas;
            } else if (c == ',' && !dentroAspas) {
                buffer.texto[posBuffer] = '\0';
                strcpy(campos[numCampos++].texto, buffer.texto);
                posBuffer = 0;
            } else {
                buffer.texto[posBuffer++] = c;
            }
        }
        buffer.texto[posBuffer] = '\0';
        strcpy(campos[numCampos].texto, buffer.texto);
        preencherJogo(&todosJogos[totalJogos], campos);
        totalJogos++;
    }
    fclose(arquivo);
    Jogo *jogosSelecionados = (Jogo*)malloc(200 * sizeof(Jogo)); 
    int qtdSelecionados = 0;
    Str idBuscado;
    scanf("%s", idBuscado.texto);
    while (strcmp(idBuscado.texto, "FIM") != 0) {
        int idProcurado = atoi(idBuscado.texto);
        for (int i = 0; i < totalJogos; i++) {
            if (idProcurado == todosJogos[i].id) {
                jogosSelecionados[qtdSelecionados++] = todosJogos[i];
                i = totalJogos;
            }
        }
        scanf("%s", idBuscado.texto);
    }
    clock_t tempoInicio = clock();
    if(qtdSelecionados > 0) {
        ordenarPorNome(jogosSelecionados, qtdSelecionados);
    }
    clock_t tempoFim = clock();
    for(int i = 0; i < qtdSelecionados; i++) {
        exibirJogo(&jogosSelecionados[i]);
    }
    free(jogosSelecionados);
    free(todosJogos);
    double duracao = ((double)(tempoFim - tempoInicio)) / CLOCKS_PER_SEC;
    FILE *arquivoLog = fopen("891378_selecao.txt", "w");
    if (arquivoLog != NULL) {
        fprintf(arquivoLog, "891378\t%dcomparacoes\t%dmovimentacoes\t%.3fms\n", comparacoes, trocas, duracao * 1000);
        fclose(arquivoLog);
    } else {
        printf("Erro ao criar arquivo de log.\n");
    }
    return 0;
}
