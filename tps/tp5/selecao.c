#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

int totalComparacoes = 0;
int totalTrocas = 0;

#define TAMANHO_MAXIMO 1000
#define LIMITE 50

typedef struct {
    char texto[TAMANHO_MAXIMO];
} Texto;

typedef struct {
    int id;
    Texto nome;
    Texto dataLancamento;
    int numeroJogadores;
    float valorPreco;
    Texto listaIdiomas[LIMITE];
    int quantidadeIdiomas; 
    int pontuacaoMetacritic;
    float avaliacaoUsuarios;
    int totalConquistas;
    Texto listaPublicadoras[LIMITE];
    int quantidadePublicadoras;
    Texto listaDesenvolvedoras[LIMITE];
    int quantidadeDesenvolvedoras;
    Texto listaCategorias[LIMITE];
    int quantidadeCategorias;
    Texto listaGeneros[LIMITE];
    int quantidadeGeneros;
    Texto listaTags[LIMITE];
    int quantidadeTags;
} Jogo;

int processarListaComVirgulas(Texto entrada, Texto resultado[], bool removerApostrofo) {
    int indice = 0;
    int posicaoAtual = 0;
    char buffer[TAMANHO_MAXIMO];
    int posBuffer = 0;
    
    for(int i = 0; i < strlen(entrada.texto); i++) {
        char caractere = entrada.texto[i]; 
        
        if(caractere == ',') {
            buffer[posBuffer] = '\0';
            int inicioTexto = 0;
            while(buffer[inicioTexto] == ' ') inicioTexto++;
            
            if (strlen(buffer + inicioTexto) > 0) {
                strcpy(resultado[indice].texto, buffer + inicioTexto);
                indice++;
            }
            posBuffer = 0;
        } else {
            bool ignorarCaractere = (caractere == '[' || caractere == ']' || (removerApostrofo && caractere == '\''));
            if(!ignorarCaractere) {
                buffer[posBuffer++] = caractere;
            }
        }
    }
    
    buffer[posBuffer] = '\0';
    if (strlen(buffer) > 0) {
        int inicioTexto = 0;
        while(buffer[inicioTexto] == ' ') inicioTexto++;
        strcpy(resultado[indice].texto, buffer + inicioTexto);
        indice++;
    }
    return indice;
}

void converterDataParaFormato(Texto entrada, Texto *saida) {
    if (strlen(entrada.texto) < 8) { 
        strcpy(saida->texto, "01/01/0000");
        return;
    }
    
    char mesString[4], diaString[3], anoString[5];
    int mesNumero;
    
    strncpy(mesString, entrada.texto, 3);
    mesString[3] = '\0';
    
    if (entrada.texto[5] == ',') {
        diaString[0] = '0';
        diaString[1] = entrada.texto[4];
        diaString[2] = '\0';
        strcpy(anoString, entrada.texto + 7);
    } else { 
        diaString[0] = entrada.texto[4];
        diaString[1] = entrada.texto[5];
        diaString[2] = '\0';
        strcpy(anoString, entrada.texto + 8);
    }
    
    if (strcmp(mesString, "Jan") == 0) mesNumero = 1;
    else if (strcmp(mesString, "Feb") == 0) mesNumero = 2;
    else if (strcmp(mesString, "Mar") == 0) mesNumero = 3;
    else if (strcmp(mesString, "Apr") == 0) mesNumero = 4;
    else if (strcmp(mesString, "May") == 0) mesNumero = 5;
    else if (strcmp(mesString, "Jun") == 0) mesNumero = 6;
    else if (strcmp(mesString, "Jul") == 0) mesNumero = 7;
    else if (strcmp(mesString, "Aug") == 0) mesNumero = 8;
    else if (strcmp(mesString, "Sep") == 0) mesNumero = 9;
    else if (strcmp(mesString, "Oct") == 0) mesNumero = 10;
    else if (strcmp(mesString, "Nov") == 0) mesNumero = 11;
    else if (strcmp(mesString, "Dec") == 0) mesNumero = 12;
    else mesNumero = 1;
    
    sprintf(saida->texto, "%s/%02d/%s", diaString, mesNumero, anoString);
}

void atribuirId(Jogo *jogo, Texto valor) {
    jogo->id = atoi(valor.texto);
}
void atribuirNome(Jogo *jogo, Texto valor) {
    strcpy(jogo->nome.texto, valor.texto);
}
void atribuirData(Jogo *jogo, Texto valor) {
    converterDataParaFormato(valor, &jogo->dataLancamento);
}
void atribuirJogadores(Jogo *jogo, Texto valor) {
    Texto numeros;
    numeros.texto[0] = '\0';
    int posicao = 0;
    for (int i = 0; i < strlen(valor.texto); i++) {
        if (valor.texto[i] >= '0' && valor.texto[i] <= '9')
            numeros.texto[posicao++] = valor.texto[i];
    }
    numeros.texto[posicao] = '\0';
    jogo->numeroJogadores = atoi(numeros.texto);
}
void atribuirPreco(Jogo *jogo, Texto valor) {
    if(strcmp(valor.texto, "Free to Play") == 0 || strcmp(valor.texto, "0.0") == 0) {
        jogo->valorPreco = 0.0f;
    }
    else {
        jogo->valorPreco = atof(valor.texto); 
    }  
}
void atribuirIdiomas(Jogo *jogo, Texto valor) {
    jogo->quantidadeIdiomas = processarListaComVirgulas(valor, jogo->listaIdiomas, true);
}
void atribuirNotaMetacritic(Jogo *jogo, Texto valor) {
    if(strlen(valor.texto) == 0) {
        jogo->pontuacaoMetacritic = 0;
    }
    else {
        jogo->pontuacaoMetacritic = atoi(valor.texto);
    }
}
void atribuirAvaliacaoUsuario(Jogo *jogo, Texto valor) {
    if(strlen(valor.texto) == 0 || strcmp(valor.texto, "tbd") == 0) {
        jogo->avaliacaoUsuarios = 0.0f;
    }   
    else {
        jogo->avaliacaoUsuarios = atof(valor.texto);
    }   
}
void atribuirConquistas(Jogo *jogo, Texto valor) {
    if(strlen(valor.texto) == 0) {
        jogo->totalConquistas = 0;
    }
    else {
        jogo->totalConquistas = atoi(valor.texto);
    }
}
void atribuirPublicadoras(Jogo *jogo, Texto valor) {
    jogo->quantidadePublicadoras = processarListaComVirgulas(valor, jogo->listaPublicadoras, false);
}
void atribuirDesenvolvedoras(Jogo *jogo, Texto valor) {
    jogo->quantidadeDesenvolvedoras = processarListaComVirgulas(valor, jogo->listaDesenvolvedoras, false);
}
void atribuirCategorias(Jogo *jogo, Texto valor) {
    jogo->quantidadeCategorias = processarListaComVirgulas(valor, jogo->listaCategorias, false);
}
void atribuirGeneros(Jogo *jogo, Texto valor) {
    jogo->quantidadeGeneros = processarListaComVirgulas(valor, jogo->listaGeneros, false);
}
void atribuirTags(Jogo *jogo, Texto valor) {
    jogo->quantidadeTags = processarListaComVirgulas(valor, jogo->listaTags, false);
}
void preencherDadosJogo(Jogo *jogo, Texto campos[]) {
    atribuirId(jogo, campos[0]);
    atribuirNome(jogo, campos[1]);
    atribuirData(jogo, campos[2]);
    atribuirJogadores(jogo, campos[3]);
    atribuirPreco(jogo, campos[4]);
    atribuirIdiomas(jogo, campos[5]);
    atribuirNotaMetacritic(jogo, campos[6]);
    atribuirAvaliacaoUsuario(jogo, campos[7]);
    atribuirConquistas(jogo, campos[8]);
    atribuirPublicadoras(jogo, campos[9]);
    atribuirDesenvolvedoras(jogo, campos[10]);
    atribuirCategorias(jogo, campos[11]);
    atribuirGeneros(jogo, campos[12]);
    atribuirTags(jogo, campos[13]);
}

void mostrarArrayFormatado(Texto lista[], int tamanho) {
    printf("[");
    for (int i = 0; i < tamanho; i++) {
        int posInicial = 0;
        while(lista[i].texto[posInicial] == ' ') posInicial++;
        printf("%s", lista[i].texto + posInicial);
        if (i < tamanho - 1) printf(", ");
    }
    printf("]");
}

void exibirJogo(Jogo *jogo) {
    printf("=> %d ## %s ## %s ## %d ## ",
        jogo->id, 
        jogo->nome.texto, 
        jogo->dataLancamento.texto,
        jogo->numeroJogadores
    );
    if (jogo->valorPreco == 0.0) {
        printf("0.0 ## ");
    } else {
        printf("%g ## ", jogo->valorPreco);
    }
    mostrarArrayFormatado(jogo->listaIdiomas, jogo->quantidadeIdiomas);
    printf(" ## %d ## %.1f ## %d ## ", 
        jogo->pontuacaoMetacritic, 
        jogo->avaliacaoUsuarios,
        jogo->totalConquistas
    );
    mostrarArrayFormatado(jogo->listaPublicadoras, jogo->quantidadePublicadoras);
    printf(" ## ");
    mostrarArrayFormatado(jogo->listaDesenvolvedoras, jogo->quantidadeDesenvolvedoras);
    printf(" ## ");
    mostrarArrayFormatado(jogo->listaCategorias, jogo->quantidadeCategorias);
    printf(" ## ");
    mostrarArrayFormatado(jogo->listaGeneros, jogo->quantidadeGeneros);
    printf(" ## ");
    mostrarArrayFormatado(jogo->listaTags, jogo->quantidadeTags);
    printf(" ##\n");
}

void trocarPosicoes(Jogo *jogos, int posA, int posB) {
    Jogo temporario = jogos[posA];
    jogos[posA] = jogos[posB];
    jogos[posB] = temporario;
    totalTrocas += 3;
}

void ordenarPorNomeSelecao(Jogo *jogos, int quantidade) {
    for(int i = 0; i < quantidade - 1; i++) {
        int indiceMenor = i;
        for(int j = i + 1; j < quantidade; j++) {
            totalComparacoes++;
            if(strcmp(jogos[indiceMenor].nome.texto, jogos[j].nome.texto) > 0) {
                indiceMenor = j;
            }
        }
        if(i != indiceMenor) trocarPosicoes(jogos, indiceMenor, i);
    }
}

int main() {
    FILE *arquivo = NULL;
    const char *caminhosPossiveis[5] = {
        "pubs/games.csv",
        "./pubs/games.csv",
        "/tmp/games.csv",
        "games.csv",
        NULL
    };
    for (int i = 0; i < 5; i++) {
        if (caminhosPossiveis[i] == NULL) continue;
        arquivo = fopen(caminhosPossiveis[i], "r");
        if (arquivo != NULL) break;
    }
    if (!arquivo) {
        printf("Erro ao abrir o arquivo\n");
        return 1;
    }
    Jogo *todosJogos = (Jogo*)malloc(4000 * sizeof(Jogo));
    Texto linhaLida, primeiraLinha;
    int totalJogos = 0;
    fscanf(arquivo, " %[^\n]", primeiraLinha.texto);
    while (fscanf(arquivo, " %[^\n]", linhaLida.texto) != EOF) {
        linhaLida.texto[strcspn(linhaLida.texto, "\r\n")] = '\0';
        Texto campos[14];
        Texto buffer;
        int indiceCampo = 0, indiceBuf = 0;
        bool dentroAspas = false;
        for (int i = 0; i < strlen(linhaLida.texto); i++) {
            char c = linhaLida.texto[i];
            if (c == '"') {
                dentroAspas = !dentroAspas;
            } else if (c == ',' && !dentroAspas) {
                buffer.texto[indiceBuf] = '\0';
                strcpy(campos[indiceCampo++].texto, buffer.texto);
                indiceBuf = 0;
            } else {
                buffer.texto[indiceBuf++] = c;
            }
        }
        buffer.texto[indiceBuf] = '\0';
        strcpy(campos[indiceCampo].texto, buffer.texto);
        preencherDadosJogo(&todosJogos[totalJogos], campos);
        totalJogos++;
    }
    fclose(arquivo);
    Jogo *jogosSelecionados = (Jogo*)malloc(200 * sizeof(Jogo)); 
    int quantidadeSelecionados = 0;
    Texto idBuscado;
    scanf("%s", idBuscado.texto);
    while (strcmp(idBuscado.texto, "FIM") != 0) {
        int valorId = atoi(idBuscado.texto);
        for (int i = 0; i < totalJogos; i++) {
            if (valorId == todosJogos[i].id) {
                jogosSelecionados[quantidadeSelecionados++] = todosJogos[i];
                i = totalJogos;
            }
        }
        scanf("%s", idBuscado.texto);
    }
    clock_t tempoInicio = clock();
    if(quantidadeSelecionados > 0) {
        ordenarPorNomeSelecao(jogosSelecionados, quantidadeSelecionados);
    }
    clock_t tempoFim = clock();
    for(int i = 0; i < quantidadeSelecionados; i++) {
        exibirJogo(&jogosSelecionados[i]);
    }
    free(jogosSelecionados);
    free(todosJogos);
    double duracaoExecucao = ((double)(tempoFim - tempoInicio)) / CLOCKS_PER_SEC;
    FILE *arquivoLog = fopen("891378_selecao.txt", "w");
    if (arquivoLog != NULL) {
        fprintf(arquivoLog, "891378\t%dcomparacoes\t%dmovimentacoes\t%.3fms\n", totalComparacoes, totalTrocas, duracaoExecucao * 1000);
        fclose(arquivoLog);
    } else {
        printf("Erro ao criar arquivo de log.\n");
    }
    return 0;
}
