#include <stdio.h>
#include <stdbool.h>

// Função auxiliar para calcular o comprimento da string 
int comprimento(char s[]) {
    int len = 0;
    while (s[len] != '\0') {
        len++;
    }
    return len;
}

// Função auxiliar para remover o '\n' do final 
void removerNovaLinha(char s[]) {
    int len = comprimento(s);
    if (len > 0 && s[len - 1] == '\n') {
        s[len - 1] = '\0';
    }
}

// Função recursiva que verifica se uma string é palíndromo
bool ehPalindromo(char s[], int esquerda, int direita) {
    if (esquerda >= direita) {
        return true; // Caso base: cruzaram ou se encontraram no meio
    }
    if (s[esquerda] != s[direita]) {
        return false; // Encontrou diferença, não é palíndromo
    }
    // Chamada recursiva para os próximos caracteres
    return ehPalindromo(s, esquerda + 1, direita - 1);
}

int main() {
    char linha[1000]; // Buffer para armazenar cada linha

    // Lê até EOF 
    while (fgets(linha, sizeof(linha), stdin)) {
        // Remove o '\n' do final
        removerNovaLinha(linha);

        // Calcula o comprimento
        int len = comprimento(linha);

        // Condição de parada
        

        // Verifica palíndromo recursivamente
        if (ehPalindromo(linha, 0, len - 1)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
    }

    return 0;
}